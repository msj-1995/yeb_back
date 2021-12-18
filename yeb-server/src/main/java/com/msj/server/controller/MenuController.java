package com.msj.server.controller;


import com.msj.server.pojo.Menu;
import com.msj.server.service.IAdminService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author msj
 * @since 2021-12-04
 */
@RestController
// /system/cfg/**属于系统管理菜单，该路径也保存在了后台数据库中
@RequestMapping("/system/cfg")
public class MenuController {
    // 注入Menu的Service
    @Autowired
    private IAdminService adminService;

    @ApiModelProperty(value = "通过id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenuByAdminId() {

    }
}
