package com.wzliulan.demo.spca.user.auth;

import com.wzliulan.demo.spca.user.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
public class AuthAspect {
    @Autowired
    private JwtOperator jwtOperator;

    @Around("@annotation(com.wzliulan.demo.spca.user.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
        // 1、从header中获取token
        HttpServletRequest request = this.getHttpServletRequest();
        String token = request.getHeader("X-Token");

        try {
            // 2、检验token是否合法
            Boolean validate = jwtOperator.validate(token);
            if (!validate) { // 非法token
                throw new LoginSecurityException("Token令牌无效！");
            }

            // 3、设置token到requestAttribute
            request.setAttribute("token", token);
            Claims claims = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("user_id", claims.get("user_id"));
            request.setAttribute("user_name", claims.get("user_name"));
            request.setAttribute("role", claims.get("role"));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new LoginSecurityException("Token令牌无效！");
        }

        return point.proceed();
    }

    @Around("@annotation(com.wzliulan.demo.spca.user.auth.PermissionAuth)")
    public Object checkPermission(ProceedingJoinPoint point) throws Throwable {
        // 1、登录认证
        this.checkLogin(point);

        // 2、权限鉴别
        HttpServletRequest request = this.getHttpServletRequest();
        String role = (String) request.getAttribute("role");

        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod(); // 获取方法
        PermissionAuth annotation = method.getAnnotation(PermissionAuth.class); // 获取注解
        String value = annotation.value(); // 获取注解配置的值

        try {
            if (!Objects.equals(role, value)) { // 鉴权不通过
                throw new LoginSecurityException("权限不足！");
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new LoginSecurityException("权限不足！");
        }
        return point.proceed();
    }

    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
        return attributes.getRequest();
    }
}
