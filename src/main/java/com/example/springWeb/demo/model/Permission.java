package com.example.springWeb.demo.model;

public enum Permission {
    UNREGISTERED("unregistered"),
    USER("user"),
    ADMIN("admin");

    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
