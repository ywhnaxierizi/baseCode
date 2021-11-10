package com.ywh.base.service.impl.common;

import java.util.List;

public interface CommonService<T> {

    T selectByPrimaryKey(Object id);

    List<T> selectByRecode(T t);

    List<T> selectAll();

    int deleteByPrimaryKey(Object id);

    int insert(T t);

    int insertSelective(T t);

    int updateByPrimaryKey(T t);

    int updateByPrimaryKeySelective(T t);
}
