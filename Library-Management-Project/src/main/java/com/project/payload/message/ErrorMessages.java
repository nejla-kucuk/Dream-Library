package com.project.payload.message;

public class ErrorMessages {

    private ErrorMessages(){}

    public static final String NOT_PERMITTED_METHOD_MESSAGE = "You do not have any permission to do this operation!";
    public static final String PASSWORD_NOT_MATCHED  = "Your password are not matched!";


    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error: user with email %s is already register!";
    public static final String ALREADY_REGISTER_MESSAGE_PHONE = "Error: user with phone number %s is already register!";

     public static final String ROLE_NOT_FOUND = "There is no role like that, check the database!";
    public static final String NOT_FOUND_USER_MESSAGE = "Error: User not found with ID %s! ";

    public static final String NOT_PERMITTED_USER_MESSAGE = "Error: You do not have the authority!" ;

    public static final String NOT_ROLE_TYPE_VALID_MESSAGE = "Error: The role type is not valid. E.G: Admin - Employee - Member";


    public static final String NOT_FOUND_BOOKS_MESSAGE = "Error: Book not found with ID %s! " ;

    public static final String NOT_FOUND_PUBLISHER_MESSAGE = "Error: Publisher not found with ID %s! ";

    public static final String NOT_DELETED_PUBLISHER_BY_ID = "Error: Publisher has related books. Deletion not allowed!" ;

}
