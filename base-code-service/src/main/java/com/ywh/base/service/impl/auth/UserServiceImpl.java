package com.ywh.base.service.impl.auth;

import com.ywh.base.common.dao.UserMapper;
import com.ywh.base.common.domain.User;
import com.ywh.base.service.auth.UserService;
import com.ywh.base.service.impl.common.CommonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl extends CommonServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /***
     * 通过用户名获取用户信息
     * @param userName
     * @return
     */
    @Override
    public User findByUserName(String userName) {
        List<User> userList = userMapper.findByUserName(userName);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return userList.get(0);
    }
}
