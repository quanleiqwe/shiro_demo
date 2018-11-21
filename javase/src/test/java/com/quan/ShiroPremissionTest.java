package com.quan;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.Arrays;

public class ShiroPremissionTest {

    @Test
    public void testPermission1() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:permission.ini");
        SecurityManager manager = factory.getInstance();
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("name", "quan");
        System.out.println(session.getAttribute("name"));
        AuthenticationToken token = new UsernamePasswordToken("zhangsan", "666", true);
        subject.login(token);
        System.out.println(subject.isAuthenticated());
        //授权判断


        //角色判断
        //1 . 编程方式实现
//        System.out.println(subject.hasRole("role1"));
//        System.out.println(subject.hasRoles(Arrays.asList("role1","role2")));
        //使用断言判断
//        subject.checkRole("role3");

        //权限判断
        System.out.println(subject.isPermitted("user:create"));
        subject.checkPermission("user:select");
    }

    @Test
    public void testPermission2() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:permission1.ini");
        SecurityManager manager = factory.getInstance();
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("name", "quan");
        System.out.println(session.getAttribute("name"));
        AuthenticationToken token = new UsernamePasswordToken("zhangsan", "666", true);
        subject.login(token);
        System.out.println(subject.isAuthenticated());
        //授权判断


        //角色判断
        //1 . 编程方式实现
        System.out.println(subject.hasRole("role1"));
        System.out.println(subject.hasRoles(Arrays.asList("role1","role2")));
        //使用断言判断
//        subject.checkRole("role3");

        //权限判断
        System.out.println(subject.isPermitted("user:create"));
        subject.checkPermission("user:select");
    }

}
