package com.oliver.service;

import com.oliver.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oliver
 * @since 2023-08-29
 */
public interface UsersService extends IService<Users> {

    Map<String, Object> login(Users user);

    Map<String, Object> getUserInfo(String token);

    List<Users> getUsers();

    void saveUsers(Users users);


}
