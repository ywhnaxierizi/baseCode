package com.ywh.base.service.impl.auth;

import com.ywh.base.common.dao.UserMapper;
import com.ywh.base.common.domain.User;
import com.ywh.base.service.auth.UserService;
import com.ywh.base.service.common.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends CommonServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void test() {

    }
}
