package com.suntak.eightdisciplines.db8d.service;

import com.suntak.eightdisciplines.entity.FinanceDebit;

import java.util.Date;
import java.util.List;

public interface FinanceDebitService {




    /**
     * 根据BASE_UID 查询详细信息
     *
     * @param BASE_UID
     */
    FinanceDebit getFinanceDebitById(String BASE_UID);

    /**
     * 根据条件过滤扣款单数据
     *
     * @param CREATE_DATE
     * @param END_DATE
     * @param INVOICE_NO
     * @param CUSTOMER_CODE
     * @return
     */
    List<FinanceDebit> getListWithOptional(Date CREATE_DATE,
                                           Date END_DATE,
                                           String INVOICE_NO,
                                           String CUSTOMER_CODE);

    /**
     * 通过表单ID查询对应财务和商务人员的邮件集合
     * @param base_uid
     * @return
     */
    List<String> getEmailListById(String base_uid);

    /**
     * 定时任务 更新扣款单并发送邮件给指定人
     * @return
     */
    void timeDetectFinance();

    /**
     * 获取扣款单相关的索赔信息
     * @param invoice_no
     * @return
     */
    FinanceDebit getClaiInfo(String invoice_no);


    /**
     * 新增扣款单记录，同时检查表中是否已被录入，禁止手动重录
     * @param financeDebit
     * @return
     */
    boolean addFinanceDebit(FinanceDebit financeDebit);

    /**
     * 新增扣款单被扣减的记录
     * @param financeDebit
     */
    void addDecreaseFinanceDebit(FinanceDebit financeDebit);

    /**
     * 根据条件过滤已扣减的扣款单数据
     * @param CREATE_DATE
     * @param END_DATE
     * @param INVOICE_NO
     * @param CUSTOMER_CODE
     * @return
     */
    List<FinanceDebit> getDecreaseListWithOptional(Date CREATE_DATE,
                                                   Date END_DATE,
                                                   String INVOICE_NO,
                                                   String CUSTOMER_CODE);

    /**
     * 更新扣款单的索赔金额
     * @param UNITPRICE
     * @param BASE_UID
     */
    void updateFinanceDebitAmount(String UNITPRICE,
                                  String BASE_UID);

    /**
     * 删除扣款单
     * @param base_uid
     */
    void deleteFinanceDebit(String base_uid);
}
