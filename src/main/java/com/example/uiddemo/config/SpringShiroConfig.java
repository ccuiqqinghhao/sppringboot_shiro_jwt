package com.example.uiddemo.config;

import com.example.uiddemo.utils.JWTFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ：ccui
 * @date ：Created in 2020/10/18 下午11:28
 * @description：
 * @modified By：
 * @version: $
 */
@Configuration
public class SpringShiroConfig {

    /*
     * a. 告诉shiro不要使用默认的DefaultSubject创建对象，因为不能创建Session
     * */
    @Bean
    public SubjectFactory subjectFactory() {
        return new JWTDefaultSubjectFactory();
    }


    @Bean
    public Realm realm() {
        return new JWTRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        // 关闭 ShiroDAO 功能
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        //禁止Subject的getSession方法
        securityManager.setSubjectFactory(subjectFactory());

        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilter  = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());
        shiroFilter.setLoginUrl("/user/login");
        // todo url后期需要改
        shiroFilter.setUnauthorizedUrl("/unauthorized");
        /*
         * c. 添加jwt过滤器，并在下面注册
         * 也就是将jwtFilter注册到shiro的Filter中
         * 指定除了login和logout之外的请求都先经过jwtFilter
         * */
        Map<String, Filter> filterMap = new HashMap<>();
        //这个地方其实另外两个filter可以不设置，默认就是
        filterMap.put("anon", new AnonymousFilter());
        filterMap.put("jwt", new JWTFilter());
        filterMap.put("logout", new LogoutFilter());
        shiroFilter.setFilters(filterMap);

        // 拦截器

        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        filterRuleMap.put("/**/login", "anon");
        filterRuleMap.put("/**/logout","logout");
        filterRuleMap.put("/**","jwt");
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);

        return shiroFilter;
    }

    /*@Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
        // 哪些请求可以匿名访问
        chain.addPathDefinition("/login", "anon");      // 登录接口
        chain.addPathDefinition("/notLogin", "anon");   // 未登录错误提示接口
        chain.addPathDefinition("/403", "anon");    // 权限不足错误提示接口
        // 除了以上的请求外，其它请求都需要登录
        chain.addPathDefinition("/**", "authc");
        return chain;
    }*/



    // Shiro 和 Spring AOP 整合时的特殊设置
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}
