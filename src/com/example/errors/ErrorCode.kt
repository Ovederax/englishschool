package com.example.errors

enum class ErrorCode(field: String, message: String) {
    BAD_FIRST_NAME  ("firstname",   "Your firsname is not correct"),
    BAD_LAST_NAME   ("lastname",    "Your lastname is not correct"),
    BAD_LOGIN       ("login",       "Your login is bad"),
    BAD_PASSWORD    ("password",    "Your password is bad"),
    BAD_POSITION    ("position",    "Position is not correct"),
    VALIDATE_ERROR(""),

    //DATABASE ERROR
    LOGIN_ALREADY_EXISTS("login",   "This login is used"),
    LOGIN_NOT_FOUND_DB  ("login",   "This login is not found"),
    USER_IS_ACTIVE      ("token",   "User is active now, no need login account"),
    UUID_NOT_FOUND              ("token",    "Your uuid is not found"),
    YOU_NO_HAVE_THIS_PRIVILEGES ("token",    "You no have this privileges"),

    CATEGORY_NO_EXISTS("id",   "This category is no exist"),

    //-----DATABASE COMMON_ERROR---------------

    NOT_FOUND("","Page not found"),
    INTERNAL_SERVER_ERROR("Internal server error"),

    //--------MY_SQL_EXCEPTION------------------

    //-----------REQUEST ERROR-------------
    NULL_REQUEST("No data in Request"),
    COOKIE_MISSING("Cookie is missing"),

    //MySQLIntegrityConstraintViolationException
    CANT_ADD_PRODUCT_WITH_NO_UNIQUE_NAME("name", "Cant add product with no unique name"),
    CANT_ADD_CATEGORY_WITH_NO_UNIQUE_NAME("name", "Cant add category with no unique name"),

    // EXEPTION IN DaoImpl
    CANT_ADD_PRODUCT_TO_BASKET("Cant add product to basket"),
    CANT_ADD_CATEGORY("Cant add new category"),
    CANT_GET_CATEGORY("Cant get category"),
    CANT_UPDATE_CATEGORY("Cant update category"),
    CANT_DELETE_CATEGORY("Cant delete category"),
    CANT_CLEAR_DATABASE("Cant clear database"),
    CANT_ADD_PRODUCT("Cant add product"),
    CANT_GET_PRODUCT("Cant get product"),
    CANT_UPDATE_PRODUCT("Cant update product"),
    CANT_DELETE_PRODUCT("Cant delete product"),
    CANT_BUY_PRODUCT("Cant buy product"),
    CANT_GET_PRODUCT_LIST("Cant get product list"),
    CANT_GET_CLIENT("Cant get client"),
    CANT_UPDATE_MONEY_DEPOSIT("Cant update money deposit"),
    CANT_USER_LOGOUT("Cant user logout"),
    CANT_GET_CLIENT_INFO("Cant get client info"),
    CANT_GET_TUTOR("Cant get tutor"),
    CANT_UPDATE_TUTOR("Cant update tutor"),
    CANT_UPDATE_CLIENT("Cant update client"),
    CANT_ADD_TUTOR("Cant add tutor"),
    CANT_ADD_CLIENT("Cant add client"),
    CANT_GET_PURCHASES_LIST("Cant get purchases list");

    constructor(message: String) : this("", message)
}