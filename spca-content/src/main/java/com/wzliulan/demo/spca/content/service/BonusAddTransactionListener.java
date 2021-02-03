package com.wzliulan.demo.spca.content.service;

import com.alibaba.fastjson.JSON;
import com.wzliulan.demo.spca.content.dao.RocketMQTransactionLogMapper;
import com.wzliulan.demo.spca.content.domain.dto.ShareAuditDto;
import com.wzliulan.demo.spca.content.domain.model.RocketMQTransactionLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import javax.annotation.Resource;

@Slf4j
@RocketMQTransactionListener(txProducerGroup = "add-bonus-tx-group") // txProducerGroup 要与配置文件中的 group 一致
public class BonusAddTransactionListener implements RocketMQLocalTransactionListener {
    @Autowired
    private ShareService shareService;
    @Resource
    private RocketMQTransactionLogMapper rocketMQTransactionLogMapper;

    // 执行本地事务
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = headers.get(RocketMQHeaders.TRANSACTION_ID, String.class);
        Integer shareId = Integer.parseInt(headers.get("share_id", String.class));
        String auditDtoJson = headers.get("auditDtoJson", String.class);
        ShareAuditDto shareAuditDto = JSON.parseObject(auditDtoJson, ShareAuditDto.class); // 将Json转回对象

        // 执行本地事务
        try {
            shareService.auditByIdWithRocketMqLog(shareId, shareAuditDto, transactionId); // 审核、更新share表并记录事务日志到rocketmq_transaction_log表
            //shareService.auditByIdWithRocketMqLog(shareId, (ShareAuditDto) arg, transactionId); // 审核、更新share表并记录事务日志到rocketmq_transaction_log表
            // 二次确认：提交半消息
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            e.printStackTrace();
            // 二次确认：回滚半消息
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    // 回查本地事务
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        // 回查事务执行结果
        MessageHeaders headers = message.getHeaders();
        String transactionId = headers.get(RocketMQHeaders.TRANSACTION_ID, String.class);
        RocketMQTransactionLog rocketMQTransactionLog = rocketMQTransactionLogMapper.selectOne(RocketMQTransactionLog.builder()
                .transactionId(transactionId)
                .build());
        if (rocketMQTransactionLog != null) { // 回查、发现本地事务执行成功：提交半消息
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            return RocketMQLocalTransactionState.ROLLBACK; // 回查、发现本地事务执行结果并没有被登记：回滚半消息
        }
    }
}
