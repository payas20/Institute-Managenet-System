package com.lucentlearn.payas.app.StudentCRUD.model.response;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required field, Please check documentation for required field"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("Internal server Error"),
    NO_RECORD_FOUND("No record found with given id"),
    AUTHENTICATION_FAILED("Authentication Failed"),
    PERMISSION_NOT_GRANTED("Access Denied"),
    COULD_NOT_UPDATE_RECORD("Could not update record"),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified")
    ;

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
