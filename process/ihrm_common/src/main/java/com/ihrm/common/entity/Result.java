package com.ihrm.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据响应对象
 *    {
 *      success ：是否成功
 *      code    ：返回码
 *      message ：返回信息
 *      //返回数据
 *      data：  ：{
 *
 *      }
 *    }
 */
@Data
@NoArgsConstructor
public class Result {
    private Integer code;// 返回码
    private String message;//返回信息
    private Object data;// 返回数据
    private Integer total;//数据条数
    private String token;//token

    public Result(ResultCode code) {

        this.code = code.code;
        this.message = code.message;
    }

    public Result(ResultCode code,Object data) {

        this.code = code.code;
        this.message = code.message;
        this.data = data;
    }

    public Result(ResultCode code,Object data,Integer total) {
        this.code = code.code;
        this.message = code.message;
        this.data = data;
        this.total = total;
    }
    public Result(ResultCode code,Object data,String token) {
        this.code = code.code;
        this.message = code.message;
        this.data = data;
        this.token = token;
    }
    public Result(Integer code,String message) {
        this.code = code;
        this.message = message;
    }

    public static Result SUCCESS(){
        return new Result(ResultCode.SUCCESS);
    }

    public static Result ERROR(){
        return new Result(ResultCode.SERVER_ERROR);
    }

    public static Result FAIL(){
        return new Result(ResultCode.FAIL);
    }
}
