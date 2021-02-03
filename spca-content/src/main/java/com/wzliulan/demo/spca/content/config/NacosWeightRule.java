//package com.wzliulan.demo.spca.content.config;
//
//
//import com.alibaba.nacos.api.exception.NacosException;
//import com.alibaba.nacos.api.naming.NamingService;
//import com.alibaba.nacos.api.naming.pojo.Instance;
//import com.netflix.client.config.IClientConfig;
//import com.netflix.loadbalancer.AbstractLoadBalancerRule;
//import com.netflix.loadbalancer.BaseLoadBalancer;
//import com.netflix.loadbalancer.ILoadBalancer;
//import com.netflix.loadbalancer.Server;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
//import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 自定义支持 Nacos 权重的 Ribbon 负载均衡规则
// */
//@Slf4j
//@Configuration
//public class NacosWeightRule extends AbstractLoadBalancerRule {
//    @Autowired
//    private NacosDiscoveryProperties nacosDiscoveryProperties;
//
//
//    @Override
//    public void initWithNiwsConfig(IClientConfig iClientConfig) {
//        // 读取配置文件、初始化
//    }
//
//    @Override
//    public Server choose(Object o) {
//        BaseLoadBalancer loadBalancer = (BaseLoadBalancer)this.getLoadBalancer();
//        log.info("lb = {}", loadBalancer);
//        // 想要请求的微服务名称
//        String name = loadBalancer.getName();
//        // 获取Nacos服务发现的相关API
//        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
//        // Nacos Client通过基于权重的负载均衡算法给我们选择了一个实例
//        try {
//            Instance instance = namingService.selectOneHealthyInstance(name);
//            log.info("选择的实例是：port={},instance={}", instance.getPort(), instance);
//            return new NacosServer(instance);
//        } catch (NacosException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
