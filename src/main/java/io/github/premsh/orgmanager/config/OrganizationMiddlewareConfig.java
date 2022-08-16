package io.github.premsh.orgmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OrganizationMiddlewareConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OrganizationMiddleware()).addPathPatterns("/org/**").excludePathPatterns("/org/all"); ;
    }
}
