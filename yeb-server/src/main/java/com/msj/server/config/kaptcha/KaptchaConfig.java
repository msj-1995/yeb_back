package com.msj.server.config.kaptcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 谷歌验证码功能
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha defaultKaptcha() {
        // 验证码生成器
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        // 配置
        Properties properties = new Properties();
        // 是否有边框
        properties.setProperty("kaptcha.border", "yes");
        // 设置边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 边框粗细，默认为1
        // properties.setProperty("kaptcha.border.thickness", "1");
        // 验证码
        properties.setProperty("kaptcha.session.key", "code");
        // 验证码文本字符颜色，默认为黑色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        // 设置字体样式
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        // 字体大小默认40
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // 验证码文本字符范围：默认为a-z,0-9(默认abcd2345678gfynmnpwx)
        // properties.setProperty("kaptcha.textproducer.char.string", "");
        // 字符长度，默认为5
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 字符边距，默认2
    }
}
