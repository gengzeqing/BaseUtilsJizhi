package org.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 返回给调用者的封装结果
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    private Integer code;
    private String message;
    private String requestId;
    private Object data;

    /**
     * 构造成功结果
     */
    public static Result success() {
        return result(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message(), null);
    }

    /**
     * 构造成功结果
     * @param data
     * @return
     */
    public static Result success(Object data) {
        return result(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message(), data);
    }

    /**
     * 成功返回结果
     * @param message 提示信息
     * @param data    获取的数据
     */
    public static Result success(String message, Object data) {
        return result(ResultCode.SUCCESS.code(), message, data);
    }

    public static Result failed(String message) {
        return result(ResultCode.PARAM_ERROR.code(), message, null);
    }

    public static Result failed(int errorCode, String message) {
        return result(errorCode, message, null);
    }

    /**
     * 直接构造返回结果
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static Result result(Integer code, String message, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setRequestId(RequestNoContext.get());
        result.setData(data);
        return result;
    }
}
