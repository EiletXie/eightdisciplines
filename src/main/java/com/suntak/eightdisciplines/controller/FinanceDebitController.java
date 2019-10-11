package com.suntak.eightdisciplines.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suntak.eightdisciplines.ExcelUtil.ExcelTool;
import com.suntak.eightdisciplines.ExcelUtil.SendMailUtils;
import com.suntak.eightdisciplines.db8d.service.FinanceDebitService;
import com.suntak.eightdisciplines.dbErp.service.CommonUtilsService;
import com.suntak.eightdisciplines.entity.FinanceDebit;
import com.suntak.eightdisciplines.entity.Msg;
import com.suntak.eightdisciplines.entity.Record;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.beans.Beans;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Auther: CXIE
 * @Date: 2019/9/12 13:53
 * @Description:
 */
@Controller
@Slf4j
@RequestMapping("/financeDebit")
public class FinanceDebitController {

    @Resource
    FinanceDebitService financeDebitService;

    @Resource
    CommonUtilsService commonUtilsService;

    @GetMapping("/financeDebitRecord")
    public String toRecord() {
        return "financeDebitRecord";
    }

    @GetMapping("/financeDebitDecreaseRecord")
    public String toDecreaseRecord() {
        return "financeDebitDecreaseRecord";
    }

    @ResponseBody
    @PostMapping("/records")
    public Msg findRecords(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
                           @RequestParam("startdate") String startdate,
                           @RequestParam("enddate") String enddate,
                           @RequestParam("INVOICE_NO") String INVOICE_NO,
                           @RequestParam("CUSTOMER_CODE") String CUSTOMER_CODE) throws Exception {
        PageHelper.startPage(pn, 8);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 由于传入的结束时间是 那天 的0点不符合实际需求，改为当天23:59分
        Date startday = sdf.parse(startdate);
        Date endday = sdf.parse(enddate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(endday);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        endday = cal.getTime();
        List<FinanceDebit> list = financeDebitService.getListWithOptional(startday, endday, INVOICE_NO, CUSTOMER_CODE);
        PageInfo<FinanceDebit> page = new PageInfo<FinanceDebit>(list, 8);
        return Msg.success().add("pageInfo", page);
    }

    @ResponseBody
    @PostMapping("/decreaseRecords")
    public Msg findDecreaseRecords(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
                           @RequestParam("startdate") String startdate,
                           @RequestParam("enddate") String enddate,
                           @RequestParam("INVOICE_NO") String INVOICE_NO,
                           @RequestParam("CUSTOMER_CODE") String CUSTOMER_CODE) throws Exception {
        PageHelper.startPage(pn, 8);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 由于传入的结束时间是 那天 的0点不符合实际需求，改为当天23:59分
        Date startday = sdf.parse(startdate);
        Date endday = sdf.parse(enddate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(endday);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        endday = cal.getTime();
        List<FinanceDebit> list = financeDebitService.getDecreaseListWithOptional(startday, endday, INVOICE_NO, CUSTOMER_CODE);
        PageInfo<FinanceDebit> page = new PageInfo<FinanceDebit>(list, 8);
        return Msg.success().add("pageInfo", page);
    }

    @ResponseBody
    @PostMapping("/financeDebitById")
    public Msg findFinanceDebitById(@RequestParam("BASE_UID") String BASE_UID) {
        FinanceDebit financeDebit = financeDebitService.getFinanceDebitById(BASE_UID);
        log.info("financeDebit ：{}", financeDebit);
        return Msg.success().add("financeDebit", financeDebit);
    }

    @GetMapping("/export")
    public void complaintChangeRecordToExcel(@RequestParam("startdate") String startdate,
                                             @RequestParam("enddate") String enddate,
                                             HttpServletResponse response) throws Exception {
        // 如果要使用 自己的模板，而不是让 插件给你自动创建就引入模板，写入字节流
        //TemplateExportParams params = new TemplateExportParams("com/suntak/eightdisciplines/doc/record.xlsx");
        // params.setSheetName("客诉修改明细");
        response.reset();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 由于传入的结束时间是 那天 的0点不符合实际需求，改为当天23:59分
        Date startday = sdf.parse(startdate);
        Date endday = sdf.parse(enddate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(endday);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        endday = cal.getTime();
        List<FinanceDebit> list = financeDebitService.getListWithOptional(startday, endday, "", "");


        //ExcelExportUtil.exportExcel( new ExportParams("8D客诉修改记录明细","客诉修改明细"),Record.class, list)
        // 这种是帮你 自行创建 EXCEL表格
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("8D财务扣款单明细表", "UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), FinanceDebit.class, list);
        workbook.write(response.getOutputStream());
    }


    @ResponseBody
    @PostMapping("/decreaseFinanceDebit")
    @Transactional
    public Msg decreaseFinanceDebit(@RequestParam("base_uid") String base_uid,
                                    @RequestParam("decrease_amount") String decrease_amount,
                                    @RequestParam("org_id") String org_id) {
        FinanceDebit financeDebit = financeDebitService.getFinanceDebitById(base_uid);
        String unitprice = financeDebit.getUnitprice();
        financeDebit.setDecrease_amount(decrease_amount);
        // 1、前端对 扣减金额大小做判断，后端插入扣减的财务扣款单记录
        financeDebitService.addDecreaseFinanceDebit(financeDebit);

        log.info("扣款单ID : " + base_uid + " ;扣减金额 : " + decrease_amount);
        // 2、 更新该扣款单的剩余的索赔金额
        String new_amount = Double.valueOf(unitprice) - Double.valueOf(decrease_amount) + "";
        financeDebitService.updateFinanceDebitAmount(new_amount,base_uid);
        // 3、 插入扣款记录到 ERP
        financeDebit.setOrganization_id(org_id);
        commonUtilsService.insertErpDecreaseFinanaceDebit(financeDebit);

        // 4、 当剩余索赔金额为扣减数量时，删除该扣款单
        if(Double.valueOf(new_amount) < 1 || (unitprice != null && unitprice.equals(decrease_amount))){
              financeDebitService.deleteFinanceDebit(financeDebit.getBase_uid());
        }
        return  Msg.success();
    }


    @ResponseBody
    @PostMapping("/addOldFinanceDebit")
    public Msg addOldFinanceDebit(@RequestParam("INVOICE_NO") String INVOICE_NO) {
        FinanceDebit financeDebit = financeDebitService.getClaiInfo(INVOICE_NO);
        if (financeDebit != null) {
            FinanceDebit erpDebit = commonUtilsService.getErpFinanceDebitInfo(financeDebit.getOrderhead());

            FinanceDebit result = CombineFinanceDebit(financeDebit, erpDebit);
            log.info("financeDebit ：{}", result);
            boolean addFlag = financeDebitService.addFinanceDebit(result);
            if (addFlag)
                return Msg.success().add("financeDebit", result);
            else
                return Msg.fail().add("content","该RMA号已被录入，禁止重复录入！");
        } else {
            return Msg.fail().add("content","该RMA号无法在索赔记录查询到！");
        }
    }

    private FinanceDebit CombineFinanceDebit(FinanceDebit financeDebit, FinanceDebit erpDebit) {
        financeDebit.setQty("-1");
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
        String unitPrice = (Double.valueOf(clamtotal) - Double.valueOf(pcbnum) * Double.valueOf(price)) * 1000 / 1000 + "";
        financeDebit.setUnitprice(unitPrice);
        financeDebit.setTotal_amount("-" + unitPrice);
        return financeDebit;
    }
}
