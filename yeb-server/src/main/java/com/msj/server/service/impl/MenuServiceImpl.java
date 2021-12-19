package com.msj.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msj.server.mapper.MenuMapper;
import com.msj.server.pojo.Admin;
import com.msj.server.pojo.Menu;
import com.msj.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author msj
 * @since 2021-12-04
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 根据用户id查询菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenuByAdminId() {
        return menuMapper.getMenuByAdminId(((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }
}
