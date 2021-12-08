package com.ywh.base.common.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author ywh
 * @description user导入映射实体
 * @Date 2021/12/8 14:35
 */
@Data
public class UserImport {
    @ExcelProperty("id")
    private Integer id;
    @ExcelProperty("用户名")
    private String userName;
    @ExcelProperty("昵称")
    private String nickName;
    @ExcelProperty("邮箱")
    private String email;
    @ExcelProperty("手机")
    private String mobilePhone;
    @ExcelProperty("密码")
    private String passwd;
    @ExcelProperty("创建时间")
    private Timestamp createDate;
    @ExcelProperty("创建人")
    private String createBy;
    @ExcelProperty("更新时间")
    private Timestamp updateDate;
    @ExcelProperty("更新人")
    private String updateBy;
}
