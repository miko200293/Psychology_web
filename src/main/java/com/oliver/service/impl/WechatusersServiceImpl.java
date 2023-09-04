package com.oliver.service.impl;

import com.oliver.entity.Wechatusers;
import com.oliver.mapper.WechatusersMapper;
import com.oliver.service.WechatusersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oliver
 * @since 2023-08-29
 */
@Service
public class WechatusersServiceImpl extends ServiceImpl<WechatusersMapper, Wechatusers> implements WechatusersService {
@Autowired
private WechatusersMapper mapper;
    @Override
    public List<Wechatusers> getWechatusersSerivce() {
        List<Wechatusers> wechatusers=mapper.selectList(null);
        return wechatusers;
    }
}
