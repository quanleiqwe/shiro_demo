package com.quan.shiro;

import com.quan.dao.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class ShiroRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRole("admin");
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String name = (String) token.getPrincipal();
        if ("zhangsan".equals(name)) {
            User user = new User();
            user.setName(name);
            return new SimpleAuthenticationInfo(user, "8b51b90d65deccb3a7eaf96d6632082e", ByteSource.Util.bytes(name), getName());
        }
        return null;
    }

    /**
     * 清除缓存
     * @param principals
     */
    @Override
    protected void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
}
