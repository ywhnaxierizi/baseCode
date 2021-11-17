package com.ywh.base.common.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ywh
 * @description
 * @Date 2021/11/17 16:46
 */
@Data
public class UserInfo {

    private Integer userId;
    private String userName;
    private String nickName;
    private List<String> rolse = new ArrayList<>();
}
