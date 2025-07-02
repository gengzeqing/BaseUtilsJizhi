package org.base;

import org.springframework.http.HttpStatus;

/**
 * 定义返回给前端的状态码，为了避免业务异常膨胀，业务异常详细信息不在这里定义
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(HttpStatus.OK.value(), "成功！"),
    /**
     * 失败
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "请求失败！"),
    /**
     * 未登录
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "未登录！"),
    /**
     * 未授权
     */
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "无访问权限！"),
    /**
     * 未授权
     */
    @SuppressWarnings("deprecation")
	METHOD_ERROR(HttpStatus.METHOD_FAILURE.value(), "无访问权限！"),
    /**
     * 系统异常
     */
    SYS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常，请稍后重试或联系管理员！"),
    /**
     * 数据版本异常
     */
    PARAM_ERROR(1000, "参数错误");
    /**
     *
     */
    private final Integer code;
    /**
     *
     */
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

}
