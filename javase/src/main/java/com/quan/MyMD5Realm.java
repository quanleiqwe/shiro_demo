package com.quan;

import jdk.nashorn.internal.ir.ReturnNode;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class MyMD5Realm extends AuthorizingRealm {

    @Override
    public String getName() {
        return "myRealm";
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        if ("zhangsan".equals(userName)) {
            //实际项目中prinipal为User对象,模拟加密密文，设置加密solt
            return new SimpleAuthenticationInfo("zhangsan", "cd757bae8bd31da92c6b14c235668091", ByteSource.Util.bytes("zhangsan"),getName());
        }
        return null;
    }
}
