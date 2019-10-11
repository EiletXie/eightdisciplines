package com.suntak.eightdisciplines.db8d.service.impl;

import com.suntak.eightdisciplines.ExcelUtil.ExcelTool;
import com.suntak.eightdisciplines.ExcelUtil.SendMailUtils;
import com.suntak.eightdisciplines.db8d.dao.FinanceDebitDao;
import com.suntak.eightdisciplines.db8d.dao.UserDao;
import com.suntak.eightdisciplines.db8d.service.FinanceDebitService;
import com.suntak.eightdisciplines.db8d.service.UserService;
import com.suntak.eightdisciplines.entity.FinanceDebit;
import com.suntak.eightdisciplines.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FinanceDebitServiceImpl implements FinanceDebitService {

    @Resource
    FinanceDebitDao financeDebitDao;

    @Override
    public FinanceDebit getFinanceDebitById(String BASE_UID) {
        return financeDebitDao.getFinanceDebitById(BASE_UID);
    }

    @Override
    public List<String> getEmailListById(String base_uid) {
        return financeDebitDao.getEmailListById(base_uid);
    }

    @Override
    public List<FinanceDebit> getListWithOptional(Date CREATE_DATE, Date END_DATE, String INVOICE_NO, String CUSTOMER_CODE) {
        return financeDebitDao.getListWithOptional(CREATE_DATE, END_DATE, INVOICE_NO, CUSTOMER_CODE);
    }

    @Override
    public FinanceDebit getClaiInfo(String invoice_no) {
        return financeDebitDao.getClaiInfo(invoice_no);
    }

    @Override
    public void timeDetectFinance() {
        // 1、 获取当前未更新的扣款单
        List<FinanceDebit> list = financeDebitDao.getFinanceDebitList();
        System.out.println(list.size());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String senddate = sdf.format(new Date());
        if (list.size() > 0) {
            //2、 发送邮件
            for (FinanceDebit financeDebit :
                    list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("form", financeDebit);
                InputStream is = ExcelTool.exportExcel("financedebit.xls", map);

                String subject = "8D财务扣款单 - " + financeDebit.getInvoice_no();


                String content = "8D财务扣款单<br><br>" +
                        "RMA-NO:  " + financeDebit.getInvoice_no() + "<br>" +
                        "客户名称:  " + financeDebit.getCustomer_code() + "<br>" +
                        "客户编码:  " + financeDebit.getCustomer_number() + "<br>" +
                        "生产型号:  " + financeDebit.getModel() + "<br>" +
                        "金额:  " + financeDebit.getUnitprice() + " " + financeDebit.getCurrency() + "<br>" +
                        "已签核结束<br>" +
                        "发送日期: " + senddate;

                List<String> emailList = financeDebitDao.getEmailListById(financeDebit.getBase_uid());

                emailList.clear();
                emailList.add("cxie@suntakpcb.com");
                ByteArrayOutputStream baos = cloneInputStream(is);
                for (String receive :
                        emailList) {
                    // 发送邮件
                    try {
                        InputStream newStream = new ByteArrayInputStream(baos.toByteArray());
                        boolean flag = SendMailUtils.sendMail(subject, receive, content, newStream, financeDebit.getInvoice_no());
                        log.info("扣款单邮件发送状态 : {} ", flag);
                        log.info("接收邮箱 : {} ", receive);
                        log.info("扣款单BASE_UID : {} ", financeDebit.getBase_uid());
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.error("邮件发送失败");
                    }
                }
            }

            // 3、 更新扣款单状态
            financeDebitDao.updateFinanceDebitStatus(list);
        } else {
            log.info(senddate + " 无需要发送的扣款单");
        }

        log.info("执行8D财务扣款单邮件发送 静态定时任务时间: " + LocalDateTime.now());
    }

    /**
     * 由于流要多次读取，这里做复制转换，以便每次新建inputStream,注意只适合小文件的转换
     *
     * @param input
     * @return
     */
    private static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 检查表中是否已被录入，禁止手动重录
     * @param financeDebit
     * @return
     */
    public boolean addFinanceDebit(FinanceDebit financeDebit) {
        int count = financeDebitDao.checkFinanceDebitByBase_uid(financeDebit.getBase_uid());
        if (count == 0) {
            financeDebitDao.addFinanceDebit(financeDebit);
            return true;
        } else
            return false;
    }

    @Override
    public void addDecreaseFinanceDebit(FinanceDebit financeDebit) {
        financeDebitDao.addDecreaseFinanceDebit(financeDebit);
    }

    @Override
    public List<FinanceDebit> getDecreaseListWithOptional(Date CREATE_DATE, Date END_DATE, String INVOICE_NO, String CUSTOMER_CODE) {
        return financeDebitDao.getDecreaseListWithOptional(CREATE_DATE, END_DATE, INVOICE_NO, CUSTOMER_CODE);
    }

    @Override
    public void updateFinanceDebitAmount(String UNITPRICE, String BASE_UID) {
        financeDebitDao.updateFinanceDebitAmount(UNITPRICE,BASE_UID);
    }

    @Override
    public void deleteFinanceDebit(String base_uid) {
        financeDebitDao.deleteFinanceDebit(base_uid);
    }
}
