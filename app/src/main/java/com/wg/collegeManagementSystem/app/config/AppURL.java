package com.wg.collegeManagementSystem.app.config;

/**
 * Created by Jerry on 15-06-2017.
 */

public class AppURL {
    public static String BASE_URL = "http://192.168.43.103:8090/CollegeManagementSystemWebApp/api";
    public static String PUBLIC_URL = "http://192.168.43.103:8090/CollegeManagementSystemWebApp/public";
    public static String URL_LOGIN = "http://192.168.43.103:8090/CollegeManagementSystemWebApp/api/login";
    /**
     * Admin URLs
     */
    public static String ADMIN_BASE_URL = "http://192.168.43.103:8090/CollegeManagementSystemWebApp/api/admin";
    public static String ADMIN_ROLE_URL = "http://192.168.43.103:8090/CollegeManagementSystemWebApp/api/admin/roles";
    public static String ADMIN_USER_URL = "http://192.168.43.103:8090/CollegeManagementSystemWebApp/api/admin/users";
    public static String ADMIN_COLLEGE_BRANCH = "http://192.168.43.103:8090/CollegeManagementSystemWebApp/api/admin/branches";
    public static String ADMIN_SUBJECT = "http://192.168.43.103:8090/CollegeManagementSystemWebApp/api/admin/subjects";
    public static String ADMIN_FACULTY_MEMBER = "http://192.168.43.103:8090/CollegeManagementSystemWebApp/api/admin/facultyMembers";
    /**
     * HOD URLs
     */
    public static String HOD_BASE_URL = "http://192.168.43.103:8090/CollegeManagementSystemWebApp/api/hod";
    public static String HOD_FACULTY_MEMBER = "http://192.168.43.103:8090/CollegeManagementSystemWebApp/api/hod/facultyMembers";
}
