package com.msj.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long code; // 返回的状态码
    private String message; // 提示信息
    private Object obj;  // 返回的对象

    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static RespBean success(String message, Object obj) {
        return new RespBean(20, message, obj);
    }

    /**
     * 失败返回的结果
     * @param message
     * @return
     */
    public static RespBean error(String message) {
        return new RespBean(500, message, null);
    }

    /**
     * 失败返回结果
     * @param message
     * @param obj
     * @return
     */
    public static RespBean error(String message, Object obj) {
        return new RespBean(500, message, obj);
    }
}
