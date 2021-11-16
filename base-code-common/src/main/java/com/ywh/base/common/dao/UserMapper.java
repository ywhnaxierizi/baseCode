package com.ywh.base.common.dao;

import com.ywh.base.common.domain.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    int count();
}
