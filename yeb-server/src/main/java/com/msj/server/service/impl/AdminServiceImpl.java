package com.msj.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msj.server.mapper.AdminMapper;
import com.msj.server.pojo.Admin;
import com.msj.server.service.IAdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
