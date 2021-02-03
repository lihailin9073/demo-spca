package com.wzliulan.demo.spca.content.feign;

import com.wzliulan.demo.spca.content.domain.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "spca-user", contextId = "1002")
public interface UserCenterFeign2 {

    /**
     * 根据ID查询用户资料
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/user/{userId}")
    UserDto findById(@PathVariable Integer userId);

}
