package com.example.uiddemo.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * @author ：ccui
 * @date ：Created in 2020/10/25 上午10:34
 * @description：
 * @modified By：
 * @version: $
 */
public class JWTDefaultSubjectFactory extends DefaultWebSubjectFactory {
    @Override
    public Subject createSubject(SubjectContext context) {
        /* 不创建session */
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
