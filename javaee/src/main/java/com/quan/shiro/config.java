package com.quan.shiro;

import javafx.scene.paint.Material;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.lang.invoke.SerializedLambda;
import java.net.SecureCacheResponse;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class config {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
        //必须设置
        factory.setSecurityManager(securityManager);

        // 拦截器.
        //rest：比如/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user：method] ,其中method为post，get，delete等。
        //port：比如/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal：//serverName：8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
        //perms：比如/admins/user/**=perms[user：add：*],perms参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，比如/admins/user/**=perms["user：add：*,user：modify：*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
        //roles：比如/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，比如/admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。//要实现or的效果看http://zgzty.blog.163.com/blog/static/83831226201302983358670/
        //anon：比如/admins/**=anon 没有参数，表示可以匿名使用。
        //authc：比如/admins/user/**=authc表示需要认证才能使用，没有参数
        //authcBasic：比如/admins/user/**=authcBasic没有参数表示httpBasic认证
        //ssl：比如/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
        //user：比如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查

        Map<String,String> filterMap = new HashMap();
        filterMap.put("/static/**","anon");
        //登出url
        filterMap.put("/logout","logout");
        filterMap.put("/actuator/**","roles[admin]");
        filterMap.put("/**","authc");
        factory.setFilterChainDefinitionMap(filterMap);

        //验证码过滤器
//        Map<String, Filter> filtersMap = factory.getFilters();
//        KaptchaFilter kaptchaFilter = new KaptchaFilter();
//        filtersMap.put("kaptchaFilter", kaptchaFilter);

        factory.setLoginUrl("/user/login");
        factory.setSuccessUrl("/user/index");
        //授权失败跳转的页面，在springmvc中即使加上也没有作用，异常信息会默认交给SpringMVC处理
        factory.setUnauthorizedUrl("/static/403.html");
        return factory;
    }

    @Bean
    public Realm shiroRealm(CredentialsMatcher matcher){
        ShiroRealm realm =  new ShiroRealm();
        //设置密码校验器
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    @Bean
    public SecurityManager securityManager(Realm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(realm);

        //注入缓存管理器;进行授权以及用户信息就不需要每次使用Realm获取了
        //注意:开发时请先关闭，如不关闭热启动会报错
        securityManager.setCacheManager(cacheManager());
        //注入记住我管理器，这样就可以将信息保存到cookie中
        securityManager.setRememberMeManager(rememberMeManager());

        return securityManager;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");   //散列算法
        matcher.setHashIterations(3);   //散列次数
        return matcher;
    }

    @Bean
    public CacheManager cacheManager(){
        return new MemoryConstrainedCacheManager();
    }
    /**
     * 加入注解的支持，可以使用@RequireRole,@RequirePermission,@RequireUser
     * @param manager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager manager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

    @Bean
    public RememberMeManager rememberMeManager(){
        CookieRememberMeManager manager =  new CookieRememberMeManager();
        return manager;
    }
}
