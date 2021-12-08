package com.ywh.base.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ywh
 * @description easyExecl自定义监听器，数据处理逻辑在本类中，注意本监听器不能被spring容器管理
 * @Date 2021/12/8 15:20
 */
@Slf4j
public class EasyExeclListener<T> extends AnalysisEventListener<T> {


    /**
     * 解析文件时每解析一条数据都会调用此方法
     * @param t
     * @param analysisContext
     */
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {

    }

    /**
     * 在解析完所有的数据后调用此方法
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    /**
     *返回false将停止解析并且不会调用doAfterAllAnalysed方法
     * @param context
     * @return
     */
    @Override
    public boolean hasNext(AnalysisContext context) {
        return true;
    }
}
