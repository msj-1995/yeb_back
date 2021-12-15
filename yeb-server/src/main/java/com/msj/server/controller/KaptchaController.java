package com.msj.server.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码接口
 */

@RestController
public class KaptchaController {
    @ApiOperation(value="验证码")
    @GetMapping(value = "/kaptcha")
    public void kaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        // 定义response输出类型为image/jpeg类型
        httpServletResponse.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers
        httpServletResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers(use addHeader)
        httpServletResponse.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache-header
        httpServletResponse.setHeader("Pragma", "no-cache");
        // return a jpeg
        httpServletResponse.setContentType("image/jpeg");
    }
}
