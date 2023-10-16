package com.oliver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oliver.entity.Users;
import com.oliver.mapper.UsersMapper;
import com.oliver.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oliver.utils.Jwtutils;
import com.oliver.utils.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oliver
 * @since 2023-08-29
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Jwtutils jwtutils;
    @Override
    public Map<String, Object>login(Users user) {
        QueryWrapper<Users>qw=new QueryWrapper<Users>();
        if(user.getUsername()!=null){
            qw.eq("Username", user.getUsername());

        }
        if(user.getPassword()!=null){
            qw.eq("Password", user.getPassword());
        }
        Users users2 = this.getBaseMapper().selectOne(qw);
        if (users2 !=null){
            Map<String, Object>map =new HashMap<>();
            String token =Jwtutils.generateToken(users2.getUsername());
            map.put("token", token);
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        Users user = jwtutils.getTClaimFromToken(token,Users.class);
        Map<String, Object>data =new HashMap<>();
        if(user !=null){
            data.put("role",new Array[0]);
            data.put("intoroduct","hello");
            data.put("avatar","ddsw");
            data.put("name","miko");
            data.put("email",new Array[0]);
            return data;

        }
        return null;
    }

    @Override
    public List<Users> getUsers() {
        List<Users> users=usersMapper.selectList(null);
        return users;
    }

    @Override
    public void saveUsers(Users users) {
        usersMapper.insertUsers(users);

    }

    @Override
    public Result updatemypassword(Users users,String newpassword) {
        String userPass = usersMapper.findUsersPassNameByUserName(users);

        if(userPass ==null){
            return Result.errorByCodeMessage(400,"用户不存在");
        }
        if (userPass.equals(users.getPassword()) ) {
            usersMapper.updateNewPassword(users, newpassword);
            return Result.successByCodeMessage(200, "修改成功");
        } else {

            return Result.errorByCodeMessage(400, "修改失败,密码错误");
        }

    }
}
