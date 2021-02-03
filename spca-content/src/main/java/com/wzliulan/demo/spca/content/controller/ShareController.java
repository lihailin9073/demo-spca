package com.wzliulan.demo.spca.content.controller;

import com.wzliulan.demo.spca.content.auth.CheckLogin;
import com.wzliulan.demo.spca.content.domain.dto.ShareDto;
import com.wzliulan.demo.spca.content.domain.dto.UserDto;
import com.wzliulan.demo.spca.content.feign.UserCenterFeign;
import com.wzliulan.demo.spca.content.service.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RequestMapping("/share")
@RestController
public class ShareController {

    /** DiscoveryClient的基本使用
     @Autowired
     private DiscoveryClient discoveryClient;
     @GetMapping("/get-service")
     public Object getServiceInfo(String serviceName) {
     if (null==serviceName || "".equals(serviceName)) serviceName="spca-user";
     // 返回指定名称的服务信息
     return discoveryClient.getInstances(serviceName);
     }
     */

    @Resource
    private ShareService shareService;

    //@CheckLogin
    @GetMapping("/{id}")
    public Object findById(@PathVariable Integer id) {
        ShareDto shareDto = null;
        try {
            // 使用RestTemplate的实现
            // shareDto = shareService.findShare(id);

            // 使用Feign的实现
            shareDto = shareService.findShare2Feign(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shareDto;
    }

    // Feign构造Get型的多参数请求
    // 测试地址：http://localhost:8101/share/query-user?id=201&wxNickname=lihailin9073
    @Autowired
    private UserCenterFeign userCenterFeign;
    @GetMapping("query-user")
    public Object queryUser(UserDto userDto) {
        return userCenterFeign.query(userDto);
    }
    // Feign构造Post型的多参数请求
    // 测试地址[Postman]：http://localhost:8101/share/search-user
    @PostMapping("search-user")
    public Object searchUser(@RequestBody UserDto userDto) {
        return userCenterFeign.search(userDto);
    }

}
