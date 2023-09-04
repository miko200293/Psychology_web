package com.oliver.service;

import com.oliver.entity.Users;
import com.oliver.entity.Wechatusers;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oliver
 * @since 2023-08-29
 */
public interface WechatusersService extends IService<Wechatusers> {
    List<Wechatusers> getWechatusersSerivce();
}
