package com.example.filikov_advanced_server.error;

public enum ErrorCodes {
    UNKNOWN(0, "unknown"),
    USERNAME_SIZE_NOT_VALID(1, ValidationConstants.USERNAME_SIZE_NOT_VALID),
    EMAIL_SIZE_NOT_VALID(3, ValidationConstants.EMAIL_SIZE_NOT_VALID),
    MUST_NOT_BE_NULL(4, "must not be null"),
    USER_NOT_FOUND(5,ValidationConstants.USER_NOT_FOUND),
    UNAUTHORIZED(7, ValidationConstants.UNAUTHORIZED),
    USER_EMAIL_NOT_NULL(8, ValidationConstants.USER_EMAIL_NOT_NULL),
    USER_ROLE_NOT_NULL(10, ValidationConstants.USER_ROLE_NOT_NULL),
    NEWS_DESCRIPTION_SIZE(11, ValidationConstants.NEWS_DESCRIPTION_SIZE_NOT_VALID),
    NEWS_DESCRIPTION_NOT_NULL(12, ValidationConstants.NEWS_DESCRIPTION_HAS_TO_BE_PRESENT),
    PARAM_PAGE_NOT_NULL(15, "Required Integer parameter 'page' is not present"),
    PARAM_PER_PAGE_NOT_NULL(16, "Required Integer parameter 'perPage' is not present"),
    USER_EMAIL_NOT_VALID(17, ValidationConstants.USER_EMAIL_NOT_VALID),
    PAGE_SIZE_NOT_VALID(18, "news page must be greater or equal 1"),
    PER_PAGE_MIN_NOT_VALID(19, "perPage must be greater or equal 1"),
    PER_PAGE_MAX_NOT_VALID(19, "perPage must be less or equal 100"),
    CODE_NOT_NULL(20, "Required String parameter 'code' is not present"),
    EXCEPTION_HANDLER_NOT_PROVIDED(21, ValidationConstants.EXCEPTION_HANDLER_NOT_PROVIDED),
    MAX_UPLOAD_SIZE_EXCEEDED(23, ValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED),
    USER_AVATAR_NOT_NULL(24, ValidationConstants.USER_AVATAR_NOT_NULL),
    PASSWORD_NOT_VALID(25, ValidationConstants.PASSWORD_NOT_VALID),
    NEWS_NOT_FOUND(27, ValidationConstants.NEWS_NOT_FOUND),
    ID_MUST_BE_POSITIVE(29, ValidationConstants.ID_MUST_BE_POSITIVE),
    USER_ALREADY_EXISTS(30, ValidationConstants.USER_ALREADY_EXISTS),
    TODO_TEXT_NOT_NULL(31, ValidationConstants.TODO_TEXT_NOT_NULL),
    TODO_TEXT_SIZE_NOT_VALID(32, ValidationConstants.TODO_TEXT_SIZE_NOT_VALID),
    TODO_STATUS_NOT_NULL(33, ValidationConstants.TODO_STATUS_NOT_NULL),
    TASK_NOT_FOUND(34, ValidationConstants.TASK_NOT_FOUND),
    TASK_PATCH_UPDATED_NOT_CORRECT_COUNT(35, ValidationConstants.TASK_PATCH_UPDATED_NOT_CORRECT_COUNT),
    TASKS_PAGE_GREATER_OR_EQUAL_1(37, ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1),
    TASKS_PER_PAGE_GREATER_OR_EQUAL_1(38, ValidationConstants.TASKS_PER_PAGE_GREATER_OR_EQUAL_1),
    TASKS_PER_PAGE_LESS_OR_EQUAL_100(39, ValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100),
    REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT(40, ValidationConstants.REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT),
    REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT(41, ValidationConstants.REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT),
    USER_NAME_HAS_TO_BE_PRESENT(43, ValidationConstants.USERNAME_HAS_TO_BE_PRESENT),
    TAGS_NOT_VALID(44, ValidationConstants.TAGS_NOT_VALID),
    NEWS_IMAGE_HAS_TO_BE_PRESENT(45, ValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT),
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION(47, ValidationConstants.HTTP_MESSAGE_NOT_READABLE_EXCEPTION);

    private final int code;
    private final String errorText;

    ErrorCodes(int code, String errorText) {
        this.code = code;
        this.errorText = errorText;
    }

    public static Integer getError(String errorText) {
        for (ErrorCodes error : ErrorCodes.values()) {
            if (error.getErrorText().equals(errorText)) {
                return error.getErrorCode();
            }
        }
        return 0;
    }

    public int getErrorCode() {
        return code;
    }

    public String getErrorText() {
        return errorText;
    }
}
