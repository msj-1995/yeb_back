package com.msj.server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * 验证码接口:用户去登录的时候就要获取验证码，所以security中也要放行/kaptcha
 */

@RestController
public class KaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

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

        // ---------------------生成验证码 begin------------------------
        // 获取验证码文本内容
        String text = defaultKaptcha.createText();
        System.out.println("验证码内容：" + text);
        // 把文本内容放到session中:前端从session中拿到这个验证码就可以和输入做对比了
        httpServletRequest.getSession().setAttribute("kaptcha", text);
        // 根据文本验证码内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = httpServletResponse.getOutputStream();
            // 输出流输出图片，格式为jpg
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != outputStream) {
                try {
                    outputStream.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
