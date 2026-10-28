package com.semenova.javamessenger.domain;

public class User {

    private String id;
    private String name;
    private String lastname;
    private int age;
    private boolean isActive;

    public User(String id, String name, String lastname, int age, boolean isActive) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.isActive = isActive;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }


    public int getAge() {
        return age;
    }

    public boolean isActive() {
        return isActive;
    }

}
