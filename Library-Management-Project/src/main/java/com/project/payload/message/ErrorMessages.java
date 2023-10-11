package com.project.payload.message;

public class ErrorMessages {

    private ErrorMessages(){}

    public static final String NOT_PERMITTED_METHOD_MESSAGE = "You do not have any permission to do this operation!";
    public static final String PASSWORD_NOT_MATCHED  = "Your password are not matched!";


    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error: user with email %s is already register!";
    public static final String ALREADY_REGISTER_MESSAGE_PHONE = "Error: user with phone number %s is already register!";

     public static final String ROLE_NOT_FOUND = "There is no role like that, check the database!";
    public static final String NOT_FOUND_USER_MESSAGE = "Error: User not found with ID %s! ";
}
