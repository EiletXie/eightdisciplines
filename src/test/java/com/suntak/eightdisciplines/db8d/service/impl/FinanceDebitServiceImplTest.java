package com.suntak.eightdisciplines.db8d.service.impl;

import com.suntak.eightdisciplines.db8d.dao.FinanceDebitDao;
import com.suntak.eightdisciplines.entity.FinanceDebit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.List;


/**
 * @Auther: CXIE
 * @Date: 2019/9/12 14:14
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FinanceDebitServiceImplTest {

    @Autowired
    FinanceDebitDao financeDebitDao;

    @Test
    public void getFinanceDebitList() {
        List<FinanceDebit> list = financeDebitDao.getFinanceDebitList();
        System.out.println(list.size());
    }

    @Test
    public void updateFinanceDebitStatus() {
    }
}