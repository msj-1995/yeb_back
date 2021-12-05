package com.msj.server.controller;

import com.msj.server.pojo.AdminLoginParam;
import com.msj.server.pojo.RespBean;
import com.msj.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录实体
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(AdminLoginParam adminLoginParam, HttpRequest request) {
        return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(), request);
    }
}
