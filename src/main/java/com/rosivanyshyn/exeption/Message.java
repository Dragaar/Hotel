package com.rosivanyshyn.exeption;

public class Message {
    public static final String INSERT_ERROR = "Cannot create row";
    public static final String SELECT_ERROR = "Cannot find row";
    public static final String SELECT_ALL_ERROR = "Cannot find all rows";

    public static final String SELECT_FEW_ERROR = "Cannot find few rows";
    public static final String SELECT_BY_FIELD_ERROR = "Cannot find row by given field and value";
    public static final String SELECT_DYNAMIC_ERROR = "Cannot find row by given query or fields";

    public static final String UPDATE_ERROR = "Cannot update row by given object";
    public static final String DELETE_ERROR = "Cannot delete row by given id";

    public static final String COUNT_ROWS_ERROR = "Cannot count rows of the last query";
    public static final String MANY_TO_MANY_ERROR = "Cannot operate with ManyToMany";

}
