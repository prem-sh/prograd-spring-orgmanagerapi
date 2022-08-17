package io.github.premsh.orgmanager.config;

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
//                  UserController
                    .antMatchers(HttpMethod.POST,"/admin/user").hasAnyAuthority("SUPERADMIN")
                    .antMatchers(HttpMethod.DELETE, "/admin/user/**").hasAnyAuthority("SUPERADMIN")
                    .antMatchers(HttpMethod.PUT, "/admin/user/**").hasAnyAuthority("SUPERADMIN")
                    .antMatchers(HttpMethod.GET,"/admin/user/**").hasAnyAuthority("SUPERADMIN","SUPPORT")

//                  OrganizationController
                    .antMatchers(HttpMethod.POST, "/org").hasAnyAuthority("SUPERADMIN", "SUPPORT")
                    .antMatchers(HttpMethod.DELETE, "/org").hasAnyAuthority("SUPERADMIN", "SUPPORT")
                    .antMatchers(HttpMethod.PUT,  "/org").hasAnyAuthority("SUPERADMIN", "SUPPORT")
                    .antMatchers(HttpMethod.GET,"/org/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN" )

//                  MembersController
                    .antMatchers(HttpMethod.POST,"/org/*/members").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN" )
                    .antMatchers(HttpMethod.DELETE,"/org/*/members/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN" )
                    .antMatchers(HttpMethod.PUT,"/org/*/members/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN" )
                    .antMatchers(HttpMethod.GET,"/org/*/members/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "INSPECTOR" )

//                  RolesController
                    .antMatchers(HttpMethod.GET, "/org/*/role/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN" )

//                  EmployeesController
                    .antMatchers(HttpMethod.POST,"/org/*/employee").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER" )
                    .antMatchers(HttpMethod.DELETE,"/org/*/employee/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER" )
                    .antMatchers(HttpMethod.PUT,"/org/*/employee/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/employee/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "EMPLOYEE_MANAGER", "INSPECTOR", "EMPLOYEE_INSPECTOR", "EMPLOYEE_ATTENDANCE_MANAGER","EMPLOYEE_PAYROLL_MANAGER" )
//
//                  DepartmentController
                    .antMatchers(HttpMethod.POST,"/org/*/department").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER" )
                    .antMatchers(HttpMethod.DELETE,"/org/*/department/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER" )
                    .antMatchers(HttpMethod.PUT,"/org/*/department/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/department/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "EMPLOYEE_MANAGER", "INSPECTOR", "EMPLOYEE_INSPECTOR", "EMPLOYEE_ATTENDANCE_MANAGER","EMPLOYEE_PAYROLL_MANAGER")
//
//                  DesignationController
                    .antMatchers(HttpMethod.POST,"/org/*/designation").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER" )
                    .antMatchers(HttpMethod.DELETE,"/org/*/designation/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER" )
                    .antMatchers(HttpMethod.PUT,"/org/*/designation/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/designation/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "EMPLOYEE_MANAGER", "INSPECTOR", "EMPLOYEE_INSPECTOR", "EMPLOYEE_ATTENDANCE_MANAGER","EMPLOYEE_PAYROLL_MANAGER")
//
//                  AttendanceController
                    .antMatchers(HttpMethod.POST,"/org/*/attendance").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER", "EMPLOYEE_ATTENDANCE_MANAGER" )
                    .antMatchers(HttpMethod.DELETE,"/org/*/attendance/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER", "EMPLOYEE_ATTENDANCE_MANAGER" )
                    .antMatchers(HttpMethod.PUT,"/org/*/attendance/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER", "EMPLOYEE_ATTENDANCE_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/attendance/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "EMPLOYEE_MANAGER", "INSPECTOR", "EMPLOYEE_INSPECTOR", "EMPLOYEE_ATTENDANCE_MANAGER","EMPLOYEE_PAYROLL_MANAGER")
//
//                  SalarySlipController
                    .antMatchers(HttpMethod.POST,"/org/*/salaryslip").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER", "EMPLOYEE_PAYROLL_MANAGER" )
                    .antMatchers(HttpMethod.DELETE,"/org/*/salaryslip/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER", "EMPLOYEE_PAYROLL_MANAGER" )
                    .antMatchers(HttpMethod.PUT,"/org/*/salaryslip/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER", "EMPLOYEE_PAYROLL_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/salaryslip/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "EMPLOYEE_MANAGER", "INSPECTOR", "EMPLOYEE_INSPECTOR","EMPLOYEE_PAYROLL_MANAGER")
//
//                  PayrollController
                    .antMatchers(HttpMethod.POST,"/org/*/payroll").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER", "EMPLOYEE_PAYROLL_MANAGER" )
                    .antMatchers(HttpMethod.DELETE,"/org/*/payroll/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER", "EMPLOYEE_PAYROLL_MANAGER" )
                    .antMatchers(HttpMethod.PUT,"/org/*/payroll/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER", "EMPLOYEE_PAYROLL_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/payroll/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "EMPLOYEE_MANAGER", "INSPECTOR", "EMPLOYEE_INSPECTOR","EMPLOYEE_PAYROLL_MANAGER")
//
//                  AssetController
                    .antMatchers(HttpMethod.GET,"/org/*/asset").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","INVENTORY_MANAGER" )
                    .antMatchers(HttpMethod.DELETE,"/org/*/asset/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","INVENTORY_MANAGER" )
                    .antMatchers(HttpMethod.PUT,"/org/*/asset/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","INVENTORY_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/asset/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "INVENTORY_MANAGER", "INSPECTOR", "INVENTORY_INSPECTOR")
//
//                  TagsController
                    .antMatchers(HttpMethod.GET,"/org/*/tag").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","INVENTORY_MANAGER" )
                    .antMatchers(HttpMethod.DELETE,"/org/*/tag/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","INVENTORY_MANAGER" )
                    .antMatchers(HttpMethod.PUT,"/org/*/tag/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","INVENTORY_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/tag/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "INVENTORY_MANAGER", "INSPECTOR", "INVENTORY_INSPECTOR")
//
                    .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
