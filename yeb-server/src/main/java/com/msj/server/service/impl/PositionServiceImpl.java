package com.msj.server.service.impl;

import com.msj.pojo.Position;
import com.msj.mapper.PositionMapper;
import com.msj.service.IPositionService;
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
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

}
