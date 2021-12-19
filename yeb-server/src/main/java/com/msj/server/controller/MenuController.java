package com.msj.server.controller;


import com.msj.server.pojo.Menu;
import com.msj.server.service.IAdminService;
import com.msj.server.service.IMenuService;
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
    private IMenuService menuService;

    @ApiModelProperty(value = "通过id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenuByAdminId() {
        /**
         * 这里的通过用户id查寻菜单列表中的id我们选择从后台获取，如果从前端传过来的话，存在篡改的可能
         * 我们使用了Security,每个登录的对象都有一个全局对象，我们可以从这个全局对象中获取用户的id
         */
        return menuService.getMenuByAdminId();
    }
}
