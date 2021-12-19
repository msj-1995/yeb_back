package com.msj.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msj.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author msj
 * @since 2021-12-04
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 根据用户id查询菜单列表
     * @return
     */
    List<Menu> getMenuByAdminId();
}
