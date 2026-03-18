package org.studentapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.studentapi.interceptor.AuditInterceptor;
import org.studentapi.interceptor.LoggingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuditInterceptor()).addPathPatterns("/v1/api/**");
        registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/v1/api/**");
    }
}
