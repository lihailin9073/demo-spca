package com.wzliulan.demo.spca.content.feign;

//import com.wzliulan.demo.spca.content.config.UserCenterFeignConfig;
import com.wzliulan.demo.spca.content.domain.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "spca-user", contextId = "1001") //, configuration = UserCenterFeignConfig.class)
public interface UserCenterFeign {

    /**
     * 根据ID查询用户资料
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/user/{userId}")
    UserDto findById(@PathVariable Integer userId);
    //UserDto findById(@PathVariable Integer userId, @RequestHeader("X-Token") String token);

    // Feign构造Get型多参数请求
    @GetMapping("/user/query")
    UserDto query(@SpringQueryMap UserDto userDto);
    // Feign构造Post型多参数请求
    @PostMapping("/user/search")
    UserDto search(@RequestBody UserDto userDto);

}
