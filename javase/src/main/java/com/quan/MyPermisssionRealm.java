package com.quan;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Arrays;
import java.util.List;

public class  MyPermisssionRealm extends AuthorizingRealm {
    @Override
    public String getName() {
        return "MyPermisssionRealm";
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();
        if(primaryPrincipal instanceof User){
            User user = (User) primaryPrincipal;
            System.out.println(user);
        }
        List<String> roles = Arrays.asList("role1","role2");
        List<String> permissions = Arrays.asList("user:update");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }

    //授权
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        if ("zhangsan".equals(userName)) {
            User user = new User();
            user.name = userName;

            //实际项目中prinipal为User对象
            return new SimpleAuthenticationInfo(user, "666", getName());
        }
        return null;
    }

    private static class User{
        private String name;
    }
}
