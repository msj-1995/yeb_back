package com.msj.service.impl;

import com.msj.pojo.Role;
import com.msj.mapper.RoleMapper;
import com.msj.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author msj
 * @since 2021-12-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
