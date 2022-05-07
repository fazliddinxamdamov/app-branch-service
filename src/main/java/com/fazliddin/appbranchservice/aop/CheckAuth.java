package com.fazliddin.appbranchservice.aop;

import com.fazliddin.library.enums.PermissionEnum;
import com.fazliddin.library.enums.RoleTypeEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface CheckAuth {
    PermissionEnum[] permissions() default {};

    RoleTypeEnum[] roles() default {};
}
