package io.github.premsh.orgmanager.config;

import io.github.premsh.orgmanager.models.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

@Component
public class OrganizationMiddleware implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        System.out.println("//////////////");
        System.out.println("handler : "+ handler);
        System.out.println("authorities : "+token.getAuthorities());
        System.out.println("getMethod : "+request.getMethod());
        System.out.println("pathVariables : "+pathVariables.get("orgId"));
        System.out.println("//////////////");

        return true;
    }
}
