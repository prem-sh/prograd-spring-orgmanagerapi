package io.github.premsh.orgmanager.config;

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

                    .antMatchers(HttpMethod.POST,"/profile").authenticated()
                    .antMatchers(HttpMethod.GET,"/profile").authenticated()

//                  UserController
                    .antMatchers(HttpMethod.POST,"/admin/user").hasAnyAuthority(RoleConstants.SUPERADMIN)
                    .antMatchers(HttpMethod.DELETE, "/admin/user/**").hasAnyAuthority(RoleConstants.SUPERADMIN)
                    .antMatchers(HttpMethod.PUT, "/admin/user/**").hasAnyAuthority(RoleConstants.SUPERADMIN)
                    .antMatchers(HttpMethod.GET,"/admin/user/**").hasAnyAuthority(RoleConstants.SUPERADMIN,RoleConstants.SUPPORT)

//                  OrganizationController
                    .antMatchers(HttpMethod.POST, "/org").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/org").authenticated()
                    .antMatchers(HttpMethod.PUT,  "/org").authenticated()
                    .antMatchers(HttpMethod.GET,"/org/*").authenticated()

//                  MembersController
                    .antMatchers(HttpMethod.POST,"/org/*/members").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN )
                    .antMatchers(HttpMethod.DELETE,"/org/*/members/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN )
                    .antMatchers(HttpMethod.PUT,"/org/*/members/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN )
                    .antMatchers(HttpMethod.GET,"/org/*/members/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN )

//                  RolesController
                    .antMatchers(HttpMethod.GET, "/org/*/role/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN )

//                  EmployeesController
                    .antMatchers(HttpMethod.POST,"/org/*/employee").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN,RoleConstants.EMPLOYEE_MANAGER)
                    .antMatchers(HttpMethod.DELETE,"/org/*/employee/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN)
                    .antMatchers(HttpMethod.PUT,"/org/*/employee/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN,RoleConstants.EMPLOYEE_MANAGER )
                    .antMatchers(HttpMethod.GET,"/org/*/employee/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.EMPLOYEE_MANAGER, RoleConstants.EMPLOYEE_ATTENDANCE_MANAGER,RoleConstants.EMPLOYEE_PAYROLL_MANAGER)
//
//                  DepartmentController
                    .antMatchers(HttpMethod.POST,"/org/*/department").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN)
                    .antMatchers(HttpMethod.DELETE,"/org/*/department/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN)
                    .antMatchers(HttpMethod.PUT,"/org/*/department/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN)
                    .antMatchers(HttpMethod.GET,"/org/*/department/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.EMPLOYEE_MANAGER, RoleConstants.EMPLOYEE_ATTENDANCE_MANAGER,RoleConstants.EMPLOYEE_PAYROLL_MANAGER)
//
//                  DesignationController
                    .antMatchers(HttpMethod.POST,"/org/*/designation").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN)
                    .antMatchers(HttpMethod.DELETE,"/org/*/designation/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN)
                    .antMatchers(HttpMethod.PUT,"/org/*/designation/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN)
                    .antMatchers(HttpMethod.GET,"/org/*/designation/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.EMPLOYEE_MANAGER, RoleConstants.EMPLOYEE_ATTENDANCE_MANAGER, RoleConstants.EMPLOYEE_PAYROLL_MANAGER)
//

//                  PayrollController
                    .antMatchers(HttpMethod.POST,"/org/*/payroll").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN,RoleConstants.EMPLOYEE_MANAGER, RoleConstants.EMPLOYEE_PAYROLL_MANAGER )
                    .antMatchers(HttpMethod.DELETE,"/org/*/payroll/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN,RoleConstants.EMPLOYEE_MANAGER)
                    .antMatchers(HttpMethod.PUT,"/org/*/payroll/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN,RoleConstants.EMPLOYEE_MANAGER, RoleConstants.EMPLOYEE_PAYROLL_MANAGER )
                    .antMatchers(HttpMethod.GET,"/org/*/payroll/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.EMPLOYEE_MANAGER,RoleConstants.EMPLOYEE_PAYROLL_MANAGER)
//
//                  AssetController
                    .antMatchers(HttpMethod.GET,"/org/*/asset").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN,RoleConstants.INVENTORY_MANAGER )
                    .antMatchers(HttpMethod.DELETE,"/org/*/asset/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN )
                    .antMatchers(HttpMethod.PUT,"/org/*/asset/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN,RoleConstants.INVENTORY_MANAGER )
                    .antMatchers(HttpMethod.GET,"/org/*/asset/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.INVENTORY_MANAGER, RoleConstants.EMPLOYEE )
//
//                  TagsController
                    .antMatchers(HttpMethod.GET,"/org/*/tag").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN,RoleConstants.INVENTORY_MANAGER )
                    .antMatchers(HttpMethod.DELETE,"/org/*/tag/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN,RoleConstants.INVENTORY_MANAGER )
                    .antMatchers(HttpMethod.PUT,"/org/*/tag/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN,RoleConstants.INVENTORY_MANAGER )
                    .antMatchers(HttpMethod.GET,"/org/*/tag/**").hasAnyAuthority(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.INVENTORY_MANAGER )
//
                    .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
