package com.quan.controller;

import com.quan.dao.User;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController{
    //登录页(shiro配置需要两个/login 接口,一个是get用来获取登陆页面,一个用post用于登录,这是一个坑)
    @GetMapping("/login")
    public String login() {

        //登陆失败跳转页面
        return "login";
    }

    // 登录提交地址和applicationontext-shiro.xml配置的loginurl一致。 (配置文件方式的说法)，登陆的地址

    @PostMapping("/login")
    public String login(HttpServletRequest request) {
       // 登录失败从request中获取shiro处理的异常信息。shiroLoginFailure:就是shiro异常类的全类名.
                Object exception = request.getAttribute("shiroLoginFailure");
        String msg = "";
        if (exception != null){
            if (UnknownAccountException.class.isInstance(exception)) {
                msg = "提示->账号不存在";
            } else if (IncorrectCredentialsException.class.isInstance(exception)) {
                msg = "提示->密码不正确";
            } else {
                msg = "提示->未知错误";
            }
            System.out.println(msg);
            return "login";
        }
        //如果已经登录，直接跳转主页面
        return "index";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    //需要admin权限才能访问
    @RequiresRoles("admin")
    @GetMapping("/hello")
    public String htllo(){
        return "hello";
    }

    @RequiresRoles("manager")
    @GetMapping("/order/list")
    public String orderList(){
        return "list";
    }

    @ResponseBody
    @GetMapping("/list")
    public List<User> userList(){
        User user = new User();
        user.setName("quan");
        return Arrays.asList(user);
    }
}
