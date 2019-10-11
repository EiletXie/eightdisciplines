package com.suntak.eightdisciplines.task;

import com.suntak.eightdisciplines.ExcelUtil.ExcelTool;
import com.suntak.eightdisciplines.ExcelUtil.SendMailUtils;
import com.suntak.eightdisciplines.db8d.service.FinanceDebitService;
import com.suntak.eightdisciplines.entity.FinanceDebit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


/**
 * @Auther: CXIE
 * @Date: 2019/9/16 11:02
 * @Description: 8D扣款单定时发送任务
 */
//@Component
//@Configuration
//@EnableScheduling
//@Slf4j
public class FinanceDebitScheduleTask {


    @Resource
    FinanceDebitService financeDebitService;


    @Transactional
//    @Scheduled(cron = "0 0/30 8-20 * * ?")
//   @Scheduled(cron = "0/10 * * * * ?")
    @RequestMapping("/sendMail")
    public void timeDetectFinance(){
        financeDebitService.timeDetectFinance();
     }
}
