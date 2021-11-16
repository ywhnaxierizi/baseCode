package com.ywh.base.common.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "email")
    private String email;
    @Column(name = "mobile_phone")
    private String mobilePhone;
    @Column(name = "passwd")
    private String passwd;
    @Column(name = "use_state")
    private String useState;
    @Column(name = "del_state")
    private String delState;
    @Column(name = "remark")
    private String remark;
    @Column(name = "create_date")
    private Timestamp createDate;
    @Column(name = "create_by")
    private String createBy;
    @Column(name = "update_date")
    private Timestamp updateDate;
    @Column(name = "update_by")
    private String updateBy;
}
