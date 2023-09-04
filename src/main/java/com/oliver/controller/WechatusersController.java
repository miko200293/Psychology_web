package com.oliver.controller;

import com.oliver.entity.Wechatusers;
import com.oliver.service.WechatusersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oliver
 * @since 2023-08-29
 */
@RestController
@RequestMapping("/wechatusers")
public class WechatusersController {
    @Autowired
    private WechatusersService service;
    @GetMapping("/test")
    public List<Wechatusers> getWechatusers() {
        List<Wechatusers> result = service.getWechatusersSerivce();
        return result;
    }

}
