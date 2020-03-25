package com.ihrm.common.entity;

/**
 * 公共的返回码
 *    返回码code：
 *      成功：10000
 *      失败：10001
 *      未登录：10002
 *      未授权：10003
 *      抛出异常：99999
 */
public enum ResultCode {

    SUCCESS(200,"操作成功！"),
    //---系统错误返回码-----
    FAIL(500,"操作失败"),
    UNAUTHENTICATED(10002,"您还未登录"),
    UNAUTHORISE(10003,"权限不足"),
    SERVER_ERROR(99999,"抱歉，系统繁忙，请稍后重试！"),

    //---用户操作返回码  2xxxx----
    MOBILEORPASSWORDERROR(20001,"用户名或密码错误");

    //---企业操作返回码  3xxxx----
    //---权限操作返回码----
    //---其他操作返回码----
    //操作代码
    int code;
    //提示信息
    String message;

    ResultCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

}
