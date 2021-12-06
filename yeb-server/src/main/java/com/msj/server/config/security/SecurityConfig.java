package com.msj.server.config.security;

import com.msj.server.pojo.Admin;
import com.msj.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * security配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IAdminService adminService;

    // 我们重写了UserDetails，重写下面的方法可以让使用security登录的时候走我们是实现的UserDetails
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 这样的话，登录的时候userDetails和passwordEncoder就会走我们自己实现的
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    // spring-security的完整配置


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 我们使用的是jwt，所以csrf()不用，可以关了
        http.csrf().disable()
                // 基于token，不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 使用add继续配置
                .and()
                .authorizeRequests()
                // 允许登录访问
                .antMatchers("/login", "/logout")
                .permitAll()
                // 除了上面的，其他的需要拦截(拦截认证）
                .anyRequest()
                .authenticated()
                .and()
                // 缓存也用不到
                .headers()
                .cacheControl();
        // 添加jwt登录授权拦截器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // 添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler()
                .authenticationEntryPoint();
    }

    // 重写UserDetails
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = adminService.getAdminByUserName(username);
            if(null != admin) {
                return admin;
            }
            return null;
        };
    }

    // 注册passwordEncoder的bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 这也是spring-security中默认使用的实现
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }
}
