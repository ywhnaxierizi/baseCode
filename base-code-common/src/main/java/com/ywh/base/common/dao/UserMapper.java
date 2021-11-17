package com.ywh.base.common.dao;

import com.ywh.base.common.domain.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    List<User> findByUserName(String userName);

    int count();
}
