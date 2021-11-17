package com.ywh.base.service.auth;

import com.ywh.base.common.domain.User;
import com.ywh.base.service.impl.common.CommonService;

public interface UserService extends CommonService<User> {

    /***
     * 通过用户名获取用户信息
     * @param userName
     * @return
     */
    User findByUserName(String userName);
}
