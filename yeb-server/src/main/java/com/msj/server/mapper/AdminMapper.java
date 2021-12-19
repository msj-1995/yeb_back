package com.msj.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msj.server.pojo.Admin;
import com.msj.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author msj
 * @since 2021-12-04
 */
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 通过用户id查询菜单列表
     * 这里为了减少数据库的查询语句，我们把当前用户id对应的菜单和子菜单一次性查出来返回给前端
     * 这里的用户和菜单列表是自关联关系
     * 这里的用户和菜单列表是自关联关系
     */
    List<Menu> getMenuByAdminId(Integer id);
}
