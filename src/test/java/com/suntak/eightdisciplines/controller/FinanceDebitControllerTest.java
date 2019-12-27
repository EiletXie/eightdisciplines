package com.suntak.eightdisciplines.controller;

import com.suntak.eightdisciplines.db8d.dao.FinanceDebitDao;
import com.suntak.eightdisciplines.db8d.service.FinanceDebitService;
import com.suntak.eightdisciplines.dbErp.dao.CommonUtilsDao;
import com.suntak.eightdisciplines.dbErp.service.CommonUtilsService;
import com.suntak.eightdisciplines.entity.FinanceDebit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.Assert.*;

/**
 * @Auther: CXIE
 * @Date: 2019/10/23 08:28
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FinanceDebitControllerTest {
    @Resource
    FinanceDebitDao financeDebitDao;

    @Resource
    CommonUtilsDao commonUtilsDao;

    @Resource
    FinanceDebitService financeDebitService;

    @Resource
    CommonUtilsService commonUtilsService;

    @Test
    public void fixOrgData() throws Exception{
        List<FinanceDebit> list =  financeDebitDao.getListWithOptional(null,null,null,null,"");
        System.out.println(list.size());
        for (FinanceDebit obj: list
             ) {
             String org_id = commonUtilsDao.getOrgIdByOrder(obj.getOrderhead(),obj.getOrderline());
             if(org_id != null){
                 financeDebitDao.updateFinanceDebitOrgId(org_id,obj.getBase_uid());
             }
        }

    }

    @Test
    public void checkAddOldFinanceDebit(){
        FinanceDebit financeDebit = new FinanceDebit();
        financeDebit.setBase_uid("190900000729");
        System.out.println(financeDebitDao.checkDecreaseFinanceDebitByBase_uid("190900000729"));
    }


//    @Test
//    public void addOldFinanceDebitByBase_uid(){
//        // 这个测试方法专门用来处理 以前老数据的存在RMA相同的，手工录入记录错误的情况
//        FinanceDebit financeDebit = financeDebitService.getClaiInfoByBaseuid("191100000069");
//        FinanceDebit erpDebit = commonUtilsService.getErpFinanceDebitInfo(financeDebit.getOrderhead(),financeDebit.getOrderline());
//
//        FinanceDebit result = CombineFinanceDebit(financeDebit, erpDebit);
//
//        boolean addFlag = financeDebitService.addFinanceDebit(result);
//    }

    private FinanceDebit CombineFinanceDebit(FinanceDebit financeDebit, FinanceDebit erpDebit) {
        financeDebit.setQty("-1");
        financeDebit.setOrganization_id(erpDebit.getOrganization_id());
        financeDebit.setReceive_to(erpDebit.getReceive_to());
        financeDebit.setReceive_bill_to(erpDebit.getReceive_bill_to());
        financeDebit.setReceive_ship_to(erpDebit.getReceive_ship_to());
        financeDebit.setReceive_tel(erpDebit.getReceive_tel());
        financeDebit.setReceive_fax(erpDebit.getReceive_fax());
        financeDebit.setReceive_contact(erpDebit.getReceive_contact());

        financeDebit.setSend_from(erpDebit.getSend_from());
        financeDebit.setSend_address(erpDebit.getSend_address());
        financeDebit.setSend_tel(erpDebit.getSend_tel());
        financeDebit.setSend_fax(erpDebit.getSend_fax());
        financeDebit.setSend_postcode(erpDebit.getSend_postcode());
        financeDebit.setTerms_name(erpDebit.getTerms_name());
        financeDebit.setFob_point_code(erpDebit.getFob_point_code());

        String pcbnum = financeDebit.getPcbnum() == null ? "0" : financeDebit.getPcbnum();
        String price = financeDebit.getPrice() == null ? "0" : financeDebit.getPrice();
        String clamtotal = financeDebit.getClamtotal() == null ? "0" : financeDebit.getClamtotal();

        BigDecimal pcbnumDecimal = new BigDecimal(Double.valueOf(pcbnum));
        BigDecimal priceBigDecimal = new BigDecimal(Double.valueOf(price));
        BigDecimal clamtotalBigDecimal = new BigDecimal(Double.valueOf(clamtotal));
        BigDecimal total =  clamtotalBigDecimal.subtract(pcbnumDecimal.multiply(priceBigDecimal));
        // 这里由于浮点数的小数点位数过大，容易出现 E的现象，必须做大数运算后进行大数FORMAT才行
        DecimalFormat df = new DecimalFormat("#.00");
        String unitPrice = df.format(total).toString();
        if(Double.valueOf(unitPrice) == 0){
            unitPrice = "0";
        }
        financeDebit.setUnitprice(unitPrice);
        financeDebit.setTotal_amount("-" + unitPrice);
        return financeDebit;
    }
}