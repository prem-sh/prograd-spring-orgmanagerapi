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
                    .antMatchers("/admin/user", "/admin/user/**").hasAnyAuthority("SUPERADMIN")
                    .antMatchers(HttpMethod.GET,"/admin/user", "/admin/user/**").hasAnyAuthority("SUPERADMIN", "SUPPORT")

//                  OrganizationController
                    .antMatchers("/org/**", "/org").hasAnyAuthority("SUPERADMIN", "SUPPORT")
                    .antMatchers(HttpMethod.GET,"/org/**", "/org").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN" )

//                  MembersController
                    .antMatchers("/org/*/members", "/org/*/members/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN" )
                    .antMatchers(HttpMethod.GET,"/org/*/members/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "INSPECTOR" )

//                  RolesController
                    .antMatchers("/org/*/role/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN" )

//                  EmployeesController
                    .antMatchers("/org/*/employee", "/org/*/employee/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/employee/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "EMPLOYEE_MANAGER", "INSPECTOR", "EMPLOYEE_INSPECTOR", "EMPLOYEE_ATTENDANCE_MANAGER","EMPLOYEE_PAYROLL_MANAGER" )
//
//                  DepartmentController
                    .antMatchers("/org/*/department", "/org/*/department/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/department/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "EMPLOYEE_MANAGER", "INSPECTOR", "EMPLOYEE_INSPECTOR", "EMPLOYEE_ATTENDANCE_MANAGER","EMPLOYEE_PAYROLL_MANAGER")
//
//                  DesignationController
                    .antMatchers("/org/*/designation", "/org/*/designation/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/designation/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "EMPLOYEE_MANAGER", "INSPECTOR", "EMPLOYEE_INSPECTOR", "EMPLOYEE_ATTENDANCE_MANAGER","EMPLOYEE_PAYROLL_MANAGER")
//
//                  AttendanceController
                    .antMatchers("/org/*/attendance", "/org/*/attendance/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER", "EMPLOYEE_ATTENDANCE_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/attendance/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "EMPLOYEE_MANAGER", "INSPECTOR", "EMPLOYEE_INSPECTOR", "EMPLOYEE_ATTENDANCE_MANAGER","EMPLOYEE_PAYROLL_MANAGER")
//
//                  SalarySlipController
                    .antMatchers("/org/*/salaryslip", "/org/*/salaryslip/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER", "EMPLOYEE_PAYROLL_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/salaryslip/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "EMPLOYEE_MANAGER", "INSPECTOR", "EMPLOYEE_INSPECTOR","EMPLOYEE_PAYROLL_MANAGER")
//
//                  PayrollController
                    .antMatchers("/org/*/payroll", "/org/*/payroll/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","EMPLOYEE_MANAGER", "EMPLOYEE_PAYROLL_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/payroll/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "EMPLOYEE_MANAGER", "INSPECTOR", "EMPLOYEE_INSPECTOR","EMPLOYEE_PAYROLL_MANAGER")
//
//                  AssetController
                    .antMatchers("/org/*/asset", "/org/*/asset/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","INVENTORY_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/asset/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "INVENTORY_MANAGER", "INSPECTOR", "INVENTORY_INSPECTOR")
//
//                  TagsController
                    .antMatchers("/org/*/tag", "/org/*/tag/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN","INVENTORY_MANAGER" )
                    .antMatchers(HttpMethod.GET,"/org/*/tag/**").hasAnyAuthority("SUPERADMIN", "SUPPORT", "ADMIN", "INVENTORY_MANAGER", "INSPECTOR", "INVENTORY_INSPECTOR")
//
                    .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
