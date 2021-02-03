package com.wzliulan.demo.spca.user.service;

import com.wzliulan.demo.spca.user.dao.BonusEventLogMapper;
import com.wzliulan.demo.spca.user.dao.UserMapper;
import com.wzliulan.demo.spca.user.domain.message.UserAddBonusMsgDto;
import com.wzliulan.demo.spca.user.domain.model.BonusEventLog;
import com.wzliulan.demo.spca.user.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private BonusEventLogMapper bonusEventLogMapper;

    /**
     * 用户查询方法
     * @param userId 用户ID
     * @return
     */
    public User findUser(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 为用户添加积分
     * @param userAddBonusMsgDto
     */
    @Transactional(rollbackFor = Exception.class)
    public void addBonus(UserAddBonusMsgDto userAddBonusMsgDto) {
        // 1、为用户加积分
        Integer userId = userAddBonusMsgDto.getUserId();
        Integer bonus = userAddBonusMsgDto.getBonus();
        User user = userMapper.selectByPrimaryKey(userId);
        if (null != user) user.setBonus(user.getBonus() + bonus);
        userMapper.updateByPrimaryKeySelective(user);

        // 2、记录日志到bonus_event_log
        BonusEventLog bonusEventLog = BonusEventLog.builder()
                .userId(userId).value(bonus+"")
                .event("share-content").description("投稿加积分").createTime(new Date())
                .build();
        bonusEventLogMapper.insert(bonusEventLog);
        log.info("收到消息：userID="+userId+"，bonus="+bonus);
    }

    /**
     * 用户登录验证
     * @param userName 账号
     * @param password 密码
     * @return 登录成功返回User对象，否则返回null
     */
    public User login(String userName, String password) {
        // 1、根据账号查询用户
        User user= userMapper.selectOne(User.builder()
                .wxId(userName)
                .build());
        // 2、验证密码正确性
        // TODO

        // 3、返回登录成功的用户
        return user;
    }

}
