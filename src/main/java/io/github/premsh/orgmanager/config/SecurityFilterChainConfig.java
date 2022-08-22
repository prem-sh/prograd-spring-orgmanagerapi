package io.github.premsh.orgmanager.config;

import io.github.premsh.orgmanager.constants.Permissions;
import io.github.premsh.orgmanager.constants.RoleConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityFilterChainConfig {
    @Bean
    public SecurityFilterChain filterChainConfig(HttpSecurity http) throws Exception{
        http
                .httpBasic().and()
                .csrf().disable()
                .authorizeRequests()

//                  ProfileController
                    .antMatchers(HttpMethod.POST,"/profile").authenticated()
                    .antMatchers(HttpMethod.GET,"/profile").authenticated()

//                  OrganizationController
                    .antMatchers(HttpMethod.POST, "/org").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/org").authenticated()
                    .antMatchers(HttpMethod.PUT,  "/org").authenticated()
                    .antMatchers(HttpMethod.GET,"/org/*").authenticated()

//                  UserController
                    .antMatchers(HttpMethod.POST,"/admin/user").hasAnyAuthority(Permissions.USER_CREATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.DELETE, "/admin/user/**").hasAnyAuthority(Permissions.USER_DELETE.toArray(String[]::new))
                    .antMatchers(HttpMethod.PUT, "/admin/user/**").hasAnyAuthority(Permissions.USER_UPDATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.GET,"/admin/user/**").hasAnyAuthority(Permissions.USER_READ.toArray(String[]::new))

//                  MembersController
                    .antMatchers(HttpMethod.POST,"/org/*/members").hasAnyAuthority(Permissions.MEMBER_CREATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.DELETE,"/org/*/members/**").hasAnyAuthority(Permissions.MEMBER_DELETE.toArray(String[]::new))
                    .antMatchers(HttpMethod.PUT,"/org/*/members/**").hasAnyAuthority(Permissions.MEMBER_UPDATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.GET,"/org/*/members/**").hasAnyAuthority(Permissions.MEMBER_READ.toArray(String[]::new))

//                  RolesController
                    .antMatchers(HttpMethod.GET, "/org/*/role/**").hasAnyAuthority(Permissions.ROLE_READ.toArray(String[]::new))

//                  EmployeesController
                    .antMatchers(HttpMethod.POST,"/org/*/employee").hasAnyAuthority(Permissions.EMPLOYEE_CREATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.DELETE,"/org/*/employee/**").hasAnyAuthority(Permissions.EMPLOYEE_DELETE.toArray(String[]::new))
                    .antMatchers(HttpMethod.PUT,"/org/*/employee/**").hasAnyAuthority(Permissions.EMPLOYEE_UPDATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.GET,"/org/*/employee/**").hasAnyAuthority(Permissions.EMPLOYEE_READ.toArray(String[]::new))
//
//                  DepartmentController
                    .antMatchers(HttpMethod.POST,"/org/*/department").hasAnyAuthority(Permissions.DEPARTMENT_CREATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.DELETE,"/org/*/department/**").hasAnyAuthority(Permissions.DEPARTMENT_DELETE.toArray(String[]::new))
                    .antMatchers(HttpMethod.PUT,"/org/*/department/**").hasAnyAuthority(Permissions.DEPARTMENT_UPDATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.GET,"/org/*/department/**").hasAnyAuthority(Permissions.DEPARTMENT_READ.toArray(String[]::new))
//
//                  DesignationController
                    .antMatchers(HttpMethod.POST,"/org/*/designation").hasAnyAuthority(Permissions.DESIGNATION_CREATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.DELETE,"/org/*/designation/**").hasAnyAuthority(Permissions.DESIGNATION_DELETE.toArray(String[]::new))
                    .antMatchers(HttpMethod.PUT,"/org/*/designation/**").hasAnyAuthority(Permissions.DESIGNATION_UPDATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.GET,"/org/*/designation/**").hasAnyAuthority(Permissions.DESIGNATION_READ.toArray(String[]::new))
//
//                  PayrollController
                    .antMatchers(HttpMethod.POST,"/org/*/payroll").hasAnyAuthority(Permissions.PAYROLL_CREATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.DELETE,"/org/*/payroll/**").hasAnyAuthority(Permissions.PAYROLL_DELETE.toArray(String[]::new))
                    .antMatchers(HttpMethod.PUT,"/org/*/payroll/**").hasAnyAuthority(Permissions.PAYROLL_UPDATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.GET,"/org/*/payroll/**").hasAnyAuthority(Permissions.PAYROLL_READ.toArray(String[]::new))
//
//                  AssetController
                    .antMatchers(HttpMethod.POST,"/org/*/asset").hasAnyAuthority(Permissions.ASSET_CREATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.DELETE,"/org/*/asset/**").hasAnyAuthority(Permissions.ASSET_DELETE.toArray(String[]::new))
                    .antMatchers(HttpMethod.PUT,"/org/*/asset/**").hasAnyAuthority(Permissions.ASSET_UPDATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.GET,"/org/*/asset/**").hasAnyAuthority(Permissions.ASSET_READ.toArray(String[]::new))
//
//                  TagsController
                    .antMatchers(HttpMethod.POST,"/org/*/tag").hasAnyAuthority(Permissions.TAG_CREATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.DELETE,"/org/*/tag/**").hasAnyAuthority(Permissions.TAG_DELETE.toArray(String[]::new))
                    .antMatchers(HttpMethod.PUT,"/org/*/tag/**").hasAnyAuthority(Permissions.TAG_UPDATE.toArray(String[]::new))
                    .antMatchers(HttpMethod.GET,"/org/*/tag/**").hasAnyAuthority(Permissions.TAG_READ.toArray(String[]::new))
//
                    .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
