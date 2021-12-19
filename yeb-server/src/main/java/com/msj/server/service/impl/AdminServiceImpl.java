package com.msj.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msj.server.config.security.JwtTokenUtil;
import com.msj.server.mapper.AdminMapper;
import com.msj.server.pojo.Admin;
import com.msj.server.pojo.Menu;
import com.msj.server.pojo.RespBean;
import com.msj.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder; // security的密码加密工具
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    // 拿到token的头部信息
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        // 验证码判断
        String kaptcha = (String)request.getSession().getAttribute("kaptcha");
        if(StringUtils.isEmpty(code) || !kaptcha.equalsIgnoreCase(code)) {
            return RespBean.error("验证码输入错误，请重新输入");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名名或密码不正确");
        }
        if(!userDetails.isEnabled()) {
            return RespBean.error("账号被禁用，请联系管理员");
        }
        // 更新登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
        // 把更新的对象放到spring-security全局中
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 登录成功，可以根据userDetails拿到jwt令牌-->生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        // 把token的头部信息也返回回去
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功", tokenMap);
    }


    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username).eq("enabled", true));
    }

    /**
     * 根据用户id查询菜单列表
     */
    @Override
    public List<Menu> getMenuByAdminId() {
        // 这里我们通过security的全局上下文获取用户id
        // 这里的getPrincipal是一个Principal,本质就是一个Admin,所以我们可以根据Admin中的getId()方法获得用户的id
        return adminMapper.getMenuByAdminId(((Admin)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }
}
