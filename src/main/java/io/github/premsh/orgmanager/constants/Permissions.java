package io.github.premsh.orgmanager.constants;

import java.util.List;

public class Permissions {

    //Tag controller permissions
    public static final List<String> TAG_CREATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.INVENTORY_MANAGER);
    public static final List<String> TAG_READ   = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.INVENTORY_MANAGER);
    public static final List<String> TAG_UPDATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.INVENTORY_MANAGER);
    public static final List<String> TAG_DELETE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.INVENTORY_MANAGER);

    //Asset controller permissions
    public static final List<String> ASSET_CREATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.INVENTORY_MANAGER);
    public static final List<String> ASSET_READ   = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.INVENTORY_MANAGER);
    public static final List<String> ASSET_UPDATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.INVENTORY_MANAGER);
    public static final List<String> ASSET_DELETE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN);

    //Department controller permissions
    public static final List<String> DEPARTMENT_CREATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN);
    public static final List<String> DEPARTMENT_READ   = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.EMPLOYEE_MANAGER);
    public static final List<String> DEPARTMENT_UPDATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN);
    public static final List<String> DEPARTMENT_DELETE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN);

    //Designation controller permissions
    public static final List<String> DESIGNATION_CREATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN);
    public static final List<String> DESIGNATION_READ   = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.EMPLOYEE_MANAGER);
    public static final List<String> DESIGNATION_UPDATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN);
    public static final List<String> DESIGNATION_DELETE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN);

    //Payroll controller permissions
    public static final List<String> PAYROLL_CREATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.EMPLOYEE_MANAGER);
    public static final List<String> PAYROLL_READ   = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.EMPLOYEE_MANAGER);
    public static final List<String> PAYROLL_UPDATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.EMPLOYEE_MANAGER);
    public static final List<String> PAYROLL_DELETE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.EMPLOYEE_MANAGER);

    //Employee controller permissions
    public static final List<String> EMPLOYEE_CREATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.EMPLOYEE_MANAGER);
    public static final List<String> EMPLOYEE_READ   = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.EMPLOYEE_MANAGER);
    public static final List<String> EMPLOYEE_UPDATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN, RoleConstants.EMPLOYEE_MANAGER);
    public static final List<String> EMPLOYEE_DELETE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN);

    //Roles controller permissions
    public static final List<String> ROLE_READ = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN);

    //Member controller permissions
    public static final List<String> MEMBER_CREATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN);
    public static final List<String> MEMBER_READ   = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN);
    public static final List<String> MEMBER_UPDATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN);
    public static final List<String> MEMBER_DELETE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT, RoleConstants.ADMIN);

    //User controller permissions
    public static final List<String> USER_CREATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT);
    public static final List<String> USER_READ   = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT);
    public static final List<String> USER_UPDATE = List.of(RoleConstants.SUPERADMIN, RoleConstants.SUPPORT);
    public static final List<String> USER_DELETE = List.of(RoleConstants.SUPERADMIN);

}
