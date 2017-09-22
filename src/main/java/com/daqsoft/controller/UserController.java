package com.daqsoft.controller;

import com.daqsoft.commons.responseEntity.BaseResponse;
import com.daqsoft.commons.responseEntity.ResponseBuilder;
import com.daqsoft.entity.SysUser;
import com.daqsoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lxx
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public BaseResponse findByUser(String username, String password){
        SysUser user = userService.findByUsernameAndPassword(username, password);
        return ResponseBuilder.custom().success().data(user).build();
    }

    @RequestMapping(value = "/findByName")
    public BaseResponse findByName(String username){
        SysUser user = userService.findByUsername(username);
        return ResponseBuilder.custom().success().data(user).build();
    }

    @RequestMapping(value = "/hello")
    public BaseResponse hello(){
        Map<String, Object> resultMap = new HashMap();
        resultMap.put("hello", "lxx");
        return ResponseBuilder.custom().success().data(resultMap).build();
    }

    @RequestMapping(value = "/world")
    public BaseResponse world(){
        Map<String, Object> resultMap = new HashMap();
        resultMap.put("world", "china");
        return ResponseBuilder.custom().success().data(resultMap).build();
    }

    @RequestMapping(value = "/findAll")
    public BaseResponse findAll() {
        List<SysUser> users = userService.findAll();
        return ResponseBuilder.custom().success().data(users).build();
    }
}
