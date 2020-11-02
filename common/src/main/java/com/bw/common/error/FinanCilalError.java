package com.bw.common.error;

public class FinanCilalError {


    public static final String JSCON_ERROR_CODE = "10000";
    public static final String JSON_ERROR_MESSAGE = "服务端返回数据解析错误";

    public static final String HTTP_ERROR_CODE = "20000";
    public static final String HTTP_ERROR_MESSAGE = "网络错误";

    public static final String SECURITY_ERROR_CODE = "30000";
    public static final String SECURITY_ERROR_MESSAGE = "权限错误";

    public static final String USER_NOT_REGISTER_ERROR = "1001";
    public static final String USER_NOT_REGISTER_MESSAGE = "用户没有注册";


    public static final String USER_ALRE_REGISTER_ERROR = "1002";
    public static final String USER_ALRE_REGISTER_MESSAGE = "该用户已存在";

    public static final String SOCKET_TIMEOUT_ERROR_CODE = "40000";
    public static final String SOCKET_TIMEOUT_ERROR_MESSAGE = "连接超时错误";
}
