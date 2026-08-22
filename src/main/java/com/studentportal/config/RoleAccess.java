package com.studentportal.config;
public final class RoleAccess {
    private RoleAccess() {}
    public static final String STUDENT = "hasRole('STUDENT')";
    public static final String FACULTY = "hasRole('FACULTY')";
    public static final String ADMIN = "hasRole('ADMIN')";
}
