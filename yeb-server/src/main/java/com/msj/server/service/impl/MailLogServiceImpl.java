package com.msj.server.service.impl;

import com.msj.pojo.MailLog;
import com.msj.mapper.MailLogMapper;
import com.msj.service.IMailLogService;
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
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
