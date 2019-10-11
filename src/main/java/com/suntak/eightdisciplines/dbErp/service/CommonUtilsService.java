package com.suntak.eightdisciplines.dbErp.service;

import com.suntak.eightdisciplines.entity.BlameProcess;
import com.suntak.eightdisciplines.entity.FinanceDebit;

import java.util.List;

public interface CommonUtilsService {


    /**
     * 通过org_id 和 item_id 获取 责任工序选项
     *
     * @param org_id
     * @param item_id
     * @return
     */
    List<BlameProcess> getBlameSelectOptions(String org_id, String item_id);


    /**
     * 获取订单行对应的 扣款单发送方和接收方信息
     * @param orderhead
     * @return
     */
    FinanceDebit getErpFinanceDebitInfo(String orderhead);

    /**
     * 插入一条 扣款记录到ERP中
     * @param financeDebit
     */
    void insertErpDecreaseFinanaceDebit(FinanceDebit financeDebit);
}
