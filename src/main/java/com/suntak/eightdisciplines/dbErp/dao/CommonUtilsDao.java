package com.suntak.eightdisciplines.dbErp.dao;

import com.suntak.eightdisciplines.entity.BlameProcess;
import com.suntak.eightdisciplines.entity.FinanceDebit;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * ERP 公共查询类
 */
@Qualifier("dbErpSqlSessionTemplate")
public interface CommonUtilsDao {

    /**
     * 通过org_id 和 item_id 获取 责任工序选项
     *
     * @param org_id
     * @param item_id
     * @return
     */
    List<BlameProcess> getBlameSelectOptions(@Param("org_id") String org_id, @Param("item_id") String item_id);

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

    /**
     * 获取订单对应的工厂
     * @param orderhead
     * @param orderline
     * @return
     */
    String getOrgIdByOrder(@Param("orderhead")String orderhead, @Param("orderline") String orderline);


    /**
     * 根据客户编码获取对应的财务人员
     * @param customer_number
     * @return
     */
    String getFinanceNameByCustNum(String customer_number);
}
