package com.ywh.base.service.common;

import java.util.List;

/**
 * @author ywh
 * @description easyExcel公共接口
 * @Date 2021/12/8 15:16
 */
public interface EasyExcelService<T> {

    void saveData(List<T> list);
}
