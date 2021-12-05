package com.msj.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msj.server.pojo.Admin;
import com.msj.server.pojo.RespBean;
import org.springframework.http.HttpRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author msj
 * @since 2021-12-04
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean login(String username, String password, HttpRequest request);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);
}
