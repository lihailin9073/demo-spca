package com.wzliulan.demo.spca.user.service;

import com.wzliulan.demo.spca.user.domain.message.UserAddBonusMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerListener {
    @Autowired
    private UserService userService;

    @StreamListener(Sink.INPUT)
    public void onMessage(UserAddBonusMsgDto userAddBonusMsgDto) {
        // 为用户添加积分
        userService.addBonus(userAddBonusMsgDto);
    }

}
