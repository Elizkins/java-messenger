package com.semenova.javamessenger.presentation.users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.semenova.javamessenger.R;
import com.semenova.javamessenger.domain.User;
import com.semenova.javamessenger.presentation.login.LoginActivity;
import com.semenova.javamessenger.presentation.register.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    private static final String EXTRA_USER = "user";
    private RecyclerView recyclerViewUsers;
    private UsersViewModel viewModel;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();

        userAdapter = new UserAdapter();
        recyclerViewUsers.setAdapter(userAdapter);

        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Intent intent = LoginActivity.newIntent(UsersActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(UsersActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users != null) {
                    userAdapter.setUsers(users);
                }
            }
        });
    }

    private void initViews() {
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
    }

    public static Intent newIntent(Context context, FirebaseUser user) {
        Intent intent = new Intent(context, UsersActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.users_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemLogout) {
            viewModel.logout();
        }
        return super.onOptionsItemSelected(item);
    }
}