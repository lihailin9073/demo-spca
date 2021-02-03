//package com.wzliulan.demo.spca.content.config;
//
//
//import com.alibaba.nacos.api.exception.NacosException;
//import com.alibaba.nacos.api.naming.NamingService;
//import com.alibaba.nacos.api.naming.pojo.Instance;
//import com.alibaba.nacos.client.naming.core.Balancer;
//import com.netflix.client.config.IClientConfig;
//import com.netflix.loadbalancer.AbstractLoadBalancerRule;
//import com.netflix.loadbalancer.BaseLoadBalancer;
//import com.netflix.loadbalancer.Server;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
//import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.CollectionUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 自定义支持 Nacos 同集群下优先调用的 Ribbon 负载均衡规则
// */
//@Slf4j
//@Configuration
//public class NacosSameClusterRule extends AbstractLoadBalancerRule {
//    @Autowired
//    private NacosDiscoveryProperties nacosDiscoveryProperties;
//
//    @Override
//    public void initWithNiwsConfig(IClientConfig iClientConfig) {
//        // 读取配置文件、初始化
//    }
//
//    @Override
//    public Server choose(Object o) {
//        /**
//         * 业务流程：
//         *   1、找到指定服务的所有实例 A
//         *   2、过滤出相同集群下的所有实例 B
//         *   3、如果B是空，则用A
//         *   4、基于权重的负载均衡算法，返回1个实例
//         */
//
//        BaseLoadBalancer loadBalancer = (BaseLoadBalancer)this.getLoadBalancer();
//        String name = loadBalancer.getName(); // 被请求的微服务名称
//
//        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance(); // 获取Nacos服务发现的相关API
//        try {
//            // 1、找到指定服务的所有实例
//            List<Instance> instances = namingService.selectInstances(name, true);
//
//            // 2、过滤出相同集群下的所有实例 B
//            String cluserName = nacosDiscoveryProperties.getClusterName(); // 获取配置文件中设置的集群名称
//            List<Instance> sameClusterInstanceList = new ArrayList<>();
//            for (Instance instance:instances) {
//                // 找出同集群名称的实例
//                if (cluserName.equals(instance.getClusterName())) sameClusterInstanceList.add(instance);
//            }
//
//            // 3、如果B是空，则用A
//            List<Instance> chooseInstanceList = new ArrayList<>();
//            if (CollectionUtils.isEmpty(sameClusterInstanceList)) {
//                chooseInstanceList = instances;
//                log.warn("发生了跨集群调用...name={},cluster-name={},instances={}", name, cluserName, instances);
//            } else {
//                chooseInstanceList = sameClusterInstanceList;
//            }
//
//            // 4、基于权重的负载均衡算法，返回1个实例
//            Instance instance = ExtendBalancer.getHostByRandomWeight2(chooseInstanceList);
//            log.info("选择的实例是：port={},instance={}", instance.getPort(), instance);
//            return new NacosServer(instance);
//        } catch (NacosException e) {
//            e.printStackTrace();
//            log.error("寻找同集群下的实例时发生异常...");
//            return null;
//        }
//    }
//}
//
///**
// * 借用Nacos中的Balancer提供的方法
// */
//class ExtendBalancer extends Balancer {
//    public static Instance getHostByRandomWeight2(List<Instance> hosts) {
//        return getHostByRandomWeight(hosts);
//    }
//}