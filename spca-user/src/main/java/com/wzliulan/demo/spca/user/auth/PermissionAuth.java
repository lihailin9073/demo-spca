package com.wzliulan.demo.spca.user.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // 允许运行期用反射取到这个自定义注解
public @interface PermissionAuth {
    String value();
}
