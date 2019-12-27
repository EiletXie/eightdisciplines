package com.suntak.eightdisciplines.dbErp.service.impl;

import com.suntak.eightdisciplines.dbErp.dao.CommonUtilsDao;
import com.suntak.eightdisciplines.dbErp.service.CommonUtilsService;
import com.suntak.eightdisciplines.entity.BlameProcess;
import com.suntak.eightdisciplines.entity.FinanceDebit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommonUtilsServiceImpl implements CommonUtilsService {

    @Resource
    CommonUtilsDao commonUtilsDao;

    @Override
    public List<BlameProcess> getBlameSelectOptions(String org_id, String item_id) {
        return commonUtilsDao.getBlameSelectOptions(org_id, item_id);
    }

    @Override
    public FinanceDebit getErpFinanceDebitInfo(String orderhead,String orderline) {
        FinanceDebit financeDebit = commonUtilsDao.getErpFinanceDebitInfo(orderhead);
        String org_id = commonUtilsDao.getOrgIdByOrder(orderhead,orderline);
        financeDebit.setOrganization_id(org_id);
        return financeDebit;
    }

    @Override
    public void insertErpDecreaseFinanaceDebit(FinanceDebit financeDebit) {
        commonUtilsDao.insertErpDecreaseFinanaceDebit(financeDebit);
    }
}
