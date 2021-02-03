package com.wzliulan.demo.spca.content.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LoginSecurityException.class)
    public ResponseEntity<ErrorBody> error(LoginSecurityException exception) {
        log.warn("认证/鉴权发生异常", exception);
        //ResponseEntity<String> responseEntity = new ResponseEntity<>("令牌Token不合法，已禁止访问。", HttpStatus.UNAUTHORIZED);
        ResponseEntity<ErrorBody> responseEntity = new ResponseEntity<>(
                ErrorBody.builder()
                        .body(exception.getMessage())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
        return  responseEntity;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class ErrorBody {
    private String body;
    private Integer status;
}