package com.msj.server.config.security;

import com.msj.server.pojo.Admin;
import com.msj.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    // 我们重写了UserDetails，重写下面的方法可以让使用security登录的时候走我们是实现的UserDetails
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 这样的话，登录的时候userDetails和passwordEncoder就会走我们自己实现的
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    // 放行一些路径，这些路径是不走拦截链的
    @Override
    public void configure(WebSecurity web) throws Exception {
        /**
         * doc.html是swagger的路径,只放行这个是不够的，还需要放行/swagger-resources/**,还有v2/api-docs/**也是
         */
        web.ignoring().antMatchers(
                "/login",
                "/css/**",
                "/js/**",
                "/index.html",
                "/favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/kaptcha"
        );
        super.configure(web);
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
                // 允许登录访问:上面的config已经配置了，所以这里可以删了
                //.antMatchers("/login", "/logout")
                //.permitAll()
                // 除了上面的，其他的需要拦截(拦截认证）
                .anyRequest()
                .authenticated()
                .and()
                // 缓存也用不到:禁用缓存
                .headers()
                .cacheControl();
        // 添加jwt登录授权拦截器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // 添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
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
