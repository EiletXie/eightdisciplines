package com.suntak.eightdisciplines.db8d.dao;

import com.suntak.eightdisciplines.entity.FinanceDebit;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.List;

@Qualifier("db8dSqlSessionTemplate")
public interface FinanceDebitDao {


    /**
     * 获取所有未发送的扣款单信息
     * @return
     */
    List<FinanceDebit> getFinanceDebitList();

    /**
     * 批量更新已发送的数据
     * @param list
     */
    void updateFinanceDebitStatus(List<FinanceDebit> list);


    /**
     * 根据BASE_UID 查询详细信息
     * @param base_uid
     */
    FinanceDebit getFinanceDebitById(String base_uid);

    /**
     * 根据条件过滤扣款单数据
     * @param START_DATE
     * @param END_DATE
     * @param INVOICE_NO
     * @param CUSTOMER_CODE
     * @return
     */
    List<FinanceDebit> getListWithOptional(@Param("START_DATE") Date START_DATE,
                                           @Param("END_DATE") Date END_DATE,
                                           @Param("INVOICE_NO") String INVOICE_NO,
                                           @Param("CUSTOMER_CODE") String CUSTOMER_CODE);


    /**
     * 通过表单ID查询对应财务和商务人员的邮件集合
     * @param base_uid
     * @return
     */
    List<String> getEmailListById(String base_uid);

    /**
     * 获取扣款单相关的索赔信息
     * @param invoice_no
     * @return
     */
    FinanceDebit getClaiInfo(String invoice_no);

    /**
     * 新增扣款单记录
     * @param financeDebit
     */
    void addFinanceDebit(FinanceDebit financeDebit);


    /**
     * 新增扣款单被扣减的记录
     * @param financeDebit
     */
    void addDecreaseFinanceDebit(FinanceDebit financeDebit);
    /**
     * 检查 扣款单是否已被录入
     * @param base_uid
     * @return
     */
    int checkFinanceDebitByBase_uid(String base_uid);

    /**
     * 根据条件过滤已扣减的扣款单数据
     * @param START_DATE
     * @param END_DATE
     * @param INVOICE_NO
     * @param CUSTOMER_CODE
     * @return
     */
    List<FinanceDebit> getDecreaseListWithOptional(@Param("START_DATE") Date START_DATE,
                                                   @Param("END_DATE") Date END_DATE,
                                                   @Param("INVOICE_NO") String INVOICE_NO,
                                                   @Param("CUSTOMER_CODE") String CUSTOMER_CODE);

    /**
     * 更新扣款单的索赔金额
     * @param UNITPRICE
     * @param BASE_UID
     */
    void updateFinanceDebitAmount(@Param("UNITPRICE") String UNITPRICE,
                                  @Param("BASE_UID") String BASE_UID);

    /**
     * 删除扣款单
     * @param base_uid
     */
    void deleteFinanceDebit(String base_uid);
}