package com.ywh.base.common.request.admin;

import com.ywh.base.common.request.BaseReq;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author ywh
 * @description
 * @Date 2021/12/10 15:08
 */
@Data
public class UserExportReq extends BaseReq {

    private Integer id;
    private String userName;
    private String nickName;
    private String email;
    private String mobilePhone;
    private String useState;
}
