package com.wzliulan.demo.spca.content.service;

import com.alibaba.fastjson.JSON;
import com.wzliulan.demo.spca.content.dao.RocketMQTransactionLogMapper;
import com.wzliulan.demo.spca.content.dao.ShareMapper;
import com.wzliulan.demo.spca.content.domain.dto.ShareAuditDto;
import com.wzliulan.demo.spca.content.domain.dto.ShareDto;
import com.wzliulan.demo.spca.content.domain.dto.UserDto;
import com.wzliulan.demo.spca.content.domain.enums.AuditStatusEnum;
import com.wzliulan.demo.spca.content.domain.message.UserAddBonusMsgDto;
import com.wzliulan.demo.spca.content.domain.model.RocketMQTransactionLog;
import com.wzliulan.demo.spca.content.domain.model.Share;
import com.wzliulan.demo.spca.content.feign.UserCenterFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ShareService {
    @Resource
    private ShareMapper shareMapper;
    @Resource
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 查询分享
     * @param id
     * @return
     */
    public ShareDto findShare(Integer id) throws Exception {
        // 查询分享内容
        Share share = shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();

        // 远程调用用户中心微服务
        List<ServiceInstance> instances = discoveryClient.getInstances("spca-user"); // 获取用户中心的注册实例
        if (null==instances || instances.size()<=0)  throw new Exception("无法找到注册实例");

        /** 最原始的 restTemplate 调用实现 */
        // UserDto userDto = restTemplate.getForObject("http://localhost:8100/user/{id}", UserDto.class, userId);
        /** 使用微服务注册实例的 restTemplate 调用 */
        // String serviceUri = "";
        // serviceUri = instances.get(0).getUri().toString(); // 取第一个实例的uri
        // UserDto userDto = restTemplate.getForObject(serviceUri + "/user/{id}", UserDto.class, userId);
        /** 使用随机策略的负载均衡的 restTemplate 调用 */
        // List<String> serviceUris = new ArrayList<>();
        // for (int i=0;i<instances.size();i++) {
        //     serviceUris.add(instances.get(i).getUri().toString());
        // }
        // int randomNum = ThreadLocalRandom.current().nextInt(serviceUris.size()); // 生成随机数
        // UserDto userDto = restTemplate.getForObject(serviceUris.get(randomNum) + "/user/{id}", UserDto.class, userId);
        /** 使用Ribbon加持的 restTemplate 调用 */
        UserDto userDto = restTemplate.getForObject("http://spca-user/user/{id}", UserDto.class, userId);
        //log.info("*** spca-user注册实例服务地址为：" + serviceUris.get(randomNum));

        ShareDto shareDto = new ShareDto();
        BeanUtils.copyProperties(share, shareDto);
        shareDto.setWxNickName(userDto.getWxNickname());

        return shareDto;
    }

    /**
     * 查询分享
     * @param id
     * @return
     */
    @Autowired
    private UserCenterFeign userCenterFeign;
    public ShareDto findShare2Feign(Integer id)  {
        // 1、查询分享内容
        Share share = shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();

        // 2、使用Feign调用用户微服务
        UserDto userDto = userCenterFeign.findById(userId);
        //UserDto userDto = userCenterFeign.findById(userId, token);

        // 3、封装返回数据
        ShareDto shareDto = new ShareDto();
        BeanUtils.copyProperties(share, shareDto);
        shareDto.setWxNickName(userDto.getWxNickname());

        return shareDto;
    }

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private Source source;
    public Share auditShare(Integer shareId, ShareAuditDto auditDto) {
        // 1、查询Share
        Share share = shareMapper.selectByPrimaryKey(shareId);
        if (null==share) throw new IllegalArgumentException("参数非法，该分享不存在！");
        if (!"NOT_YET".equals(share.getAuditStatus())) throw new IllegalArgumentException("参数非法，该分享已审核处理（通过/拒绝）！");

        // 2、如果为PASS，发送半消息到RocketMQ
        String transactionId = UUID.randomUUID().toString();
        if (AuditStatusEnum.PASS.equals(auditDto.getAuditStatusEnum())) { // 审核通过、发送半消息
            UserAddBonusMsgDto messageDto = UserAddBonusMsgDto.builder().userId(share.getUserId()).bonus(50).build();
            Message message = MessageBuilder.withPayload(messageDto)
                    .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                    .setHeader("share_id", shareId)
                    .setHeader("auditDtoJson", JSON.toJSONString(auditDto)) // header传参只能是String，所以转化成json，方便后面转回对象
                    .build();

            source.output().send(message); // 使用Stream发送消息，其中的 group 在配置文件中指定

            //rocketMQTemplate.sendMessageInTransaction("add-bonus-tx-group", "spca-content-tx", message, auditDto);

        } else { // 审核不通过、只需要更新share表的审核状态并不需要用户中心那边做加积分的操作
            this.auditByIdInDataBase(shareId, auditDto);
        }
        return share;
    }

    // 审核、更新数据表Share记录
    // @Transactional(rollbackFor = Exception.class)
    public void auditByIdInDataBase(Integer id, ShareAuditDto auditDto) {
        Share share = Share.builder()
                .id(id)
                .auditStatus(auditDto.getAuditStatusEnum().toString())
                .reason(auditDto.getReason())
                .build();
        share.setAuditStatus(auditDto.getAuditStatusEnum().toString());
        share.setReason(auditDto.getReason());
        shareMapper.updateByPrimaryKeySelective(share);
    }

    // 审核、更新share表记录、登记日志到rocketmq_transaction_log表
    @Resource
    private RocketMQTransactionLogMapper rocketMQTransactionLogMapper;
    @Transactional(rollbackFor = Exception.class)
    public void auditByIdWithRocketMqLog(Integer id, ShareAuditDto auditDto, String transactionId){
        // 审核、更新数据库share记录
        this.auditByIdInDataBase(id, auditDto);

        // 记录日志到rocketmq_transaction_log表
        RocketMQTransactionLog rocketMQTransactionLog = RocketMQTransactionLog.builder()
                .transactionId(transactionId)
                .log("事件类型：分享审核；审核时间："+ new Date())
                .build();
        rocketMQTransactionLogMapper.insertSelective(rocketMQTransactionLog);
    }
}
