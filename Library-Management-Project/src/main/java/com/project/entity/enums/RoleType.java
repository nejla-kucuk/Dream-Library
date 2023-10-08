package com.project.entity.enums;

public enum RoleType {

    MEMBER("Member"),
    EMPLOYEE("Employee"),
    ADMIN("Admin");

    public final String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
