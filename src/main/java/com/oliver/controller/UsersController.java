package com.oliver.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oliver.entity.Users;
import com.oliver.mapper.UsersMapper;
import com.oliver.service.UsersService;
import com.oliver.utils.Jwtutils;
import com.oliver.utils.Result;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oliver
 * @since 2023-08-29
 */
@RestController
@RequestMapping("/users")
@CrossOrigin
@Slf4j
public class UsersController {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersService usersService;


    //查询所有用户资料
    @GetMapping("/user")
    public String query()  {

        List<Users> list= usersMapper.findUsers();

        return "查询结果";

    }
    //用户登录
    @PostMapping("/login")
        public Result login(@RequestBody Users users){
        Map<String,Object> date1=usersService.login(users);
        if(date1 != null){
            Result result2= Result.successByCodeMessage(200,"登陆成功");
            result2.setData(date1);
            return result2;
        }
           return   Result.errorByCodeMessage(401,"登录失败");


    }
    //jwt跨域认证
    @GetMapping("/info")
    public Result info(@RequestParam("token") String token){

        Map<String,Object> date1=usersService.getUserInfo(token);
        if(date1 != null){
            return Result.ok();
        }
        return   Result.error();
    }
    //用户注册列表

    @PostMapping("/register")
    public Result register(@RequestBody Users users) {
        if(users == null){
            return Result.errorByCodeMessage(401,"传输的数据为null");
        }
        if(users.getUsername() == null||users.getPassword()==null||users.getPhone()==null){
            return Result.errorByCodeMessage(401,"传输的必要数据有null");
        }
        QueryWrapper<Users> qw = new QueryWrapper<>();
        qw.eq("Username", users.getUsername());
        Users existingUser = usersService.getBaseMapper().selectOne(qw);

        if (existingUser != null) {
            // 用户名已存在，返回错误响应。
            return Result.errorByCodeMessage(401,"用户名已存在");
        } else
            // 将新用户保存到数据库。
            usersService.saveUsers(users);
            return Result.successByCodeMessage(200,"Successfully");

    }
    // unsubscribe 注销账户
    @PostMapping("/unsubscribe")
    public  Result unsubscribe(@RequestBody Users users){
        if(users==null){
            return Result.error();
        }else{
            usersMapper.deleteById(users);
        }
        return Result.ok();
    }


    //更新用户信息(更改密码)
    @PostMapping("/updatemypassword")
    public Result updatemypassword(@RequestBody JSONObject requestBody) {
        JSONObject usersJson = requestBody.getJSONObject("users");
        String newpassword = requestBody.getString("newpassword");

        if (usersJson == null ||  newpassword == null) {
            return Result.errorByCodeMessage(400, "缺少账号或者密码");
        } else {
            String userName = usersJson.getString("Username");
            String password = usersJson.getString("Password");
            Users users = new Users();
            users.setUsername(userName);
            users.setPassword(password);
            // 在这里执行更新密码的逻辑
            Result result1= usersService.updatemypassword(users,newpassword);
            return result1;

        }

    }

    //分页查询
    @PostMapping("/selectAllUsers")
    public IPage selectAllUser(){
      IPage iPage=  usersService.selectAllUseers();
      return iPage;
    }













    //无用测试类
    @GetMapping("/test")
    public List<Users> test(){
        List<Users> date2=usersService.getUsers();
        return date2;
    }
    @GetMapping("/test2")
    public Result test2(){
        Users users=usersMapper.selectById(8989);
        return Result.successByKeyValue("users",users);
    }


    @GetMapping("/test3")
    public ResponseEntity<String> secureResource(@RequestBody String token) {
        Claims claims = Jwtutils.getClaimsByToken(token);
        String Username = claims.getSubject();
        return ResponseEntity.ok("Welcome, " + Username + "! This is a secure resource.");
//        try {
//            if (!Jwtutils.checkToken(token)) {
//                // 从令牌中获取用户信息，进行后续操作
//
//
//                // 根据用户名执行相应的逻辑
//                return ResponseEntity.ok("Welcome, " + Username + "! This is a secure resource.");
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid or expired token.");
//            }
//        } catch (ExpiredJwtException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Token expired.");
//        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token.");
//        }
    }

    @GetMapping("/test4")
    public ResponseEntity<String> secureResource2(@RequestHeader("Authorization") String token) {
        // 在请求头中获取令牌
        Claims claims = Jwtutils.getClaimsByToken(token);
        String username = claims.getSubject();
        return ResponseEntity.ok("Welcome, " + username + "! This is a secure resource.");
    }

}
