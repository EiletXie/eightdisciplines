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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Beans;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;


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
                           @RequestParam("CUSTOMER_NUMBER") String CUSTOMER_NUMBER,
                           @RequestParam("CUSTOMER_CODE") String CUSTOMER_CODE) throws Exception {
        PageHelper.startPage(pn, 8);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 由于传入的结束时间是 那天 的0点不符合实际需求，改为当天23:59分
        Date startday = null;
        Date endday = null;
        if(startdate!= null && !startdate.equals(""))
         startday = sdf.parse(startdate);
        if(enddate != null && !enddate.equals("")) {
            endday = sdf.parse(enddate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(endday);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            endday = cal.getTime();
        }
        List<FinanceDebit> list = financeDebitService.getListWithOptional(startday, endday, INVOICE_NO, CUSTOMER_NUMBER,CUSTOMER_CODE);

        // 将数据转换为千分位显示
        for (FinanceDebit debit: list) {
            String unitprice = debit.getUnitprice();
            DecimalFormat usFormat = new DecimalFormat("###,###.00");
            String new_price = usFormat.format(Double.valueOf(unitprice));
            debit.setUnitprice(new_price);
        }
        PageInfo<FinanceDebit> page = new PageInfo<FinanceDebit>(list, 5);
        return Msg.success().add("pageInfo", page);
    }

    @ResponseBody
    @PostMapping("/decreaseRecords")
    public Msg findDecreaseRecords(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
                           @RequestParam("startdate") String startdate,
                           @RequestParam("enddate") String enddate,
                           @RequestParam("INVOICE_NO") String INVOICE_NO,
                           @RequestParam("CUSTOMER_NUMBER") String CUSTOMER_NUMBER,
                                   @RequestParam("CUSTOMER_CODE") String CUSTOMER_CODE) throws Exception {
        PageHelper.startPage(pn, 8);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 由于传入的结束时间是 那天 的0点不符合实际需求，改为当天23:59分
        Date startday = null;
        Date endday = null;
        if(startdate!= null && !startdate.equals("")) {
            startday = sdf.parse(startdate);
        }
        if(enddate != null && !enddate.equals("")) {
            endday = sdf.parse(enddate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(endday);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            endday = cal.getTime();
        }
        List<FinanceDebit> list = financeDebitService.getDecreaseListWithOptional(startday, endday, INVOICE_NO, CUSTOMER_NUMBER,CUSTOMER_CODE);
        for (FinanceDebit debit: list) {
            String unitprice = debit.getUnitprice();
            DecimalFormat usFormat = new DecimalFormat("###,###.00");
            String new_price = usFormat.format(Double.valueOf(unitprice));
            debit.setUnitprice(new_price);
            String new_decrease_amount = usFormat.format(Double.valueOf(debit.getDecrease_amount()));
            debit.setDecrease_amount(new_decrease_amount);
        }
        PageInfo<FinanceDebit> page = new PageInfo<FinanceDebit>(list, 5);
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
                                             @RequestParam("INVOICE_NO") String INVOICE_NO,
                                             @RequestParam("CUSTOMER_NUMBER") String CUSTOMER_NUMBER,
                                             @RequestParam("CUSTOMER_CODE") String CUSTOMER_CODE,
                                             HttpServletResponse response) throws Exception {
        // 如果要使用 自己的模板，而不是让 插件给你自动创建就引入模板，写入字节流
        //TemplateExportParams params = new TemplateExportParams("com/suntak/eightdisciplines/doc/record.xlsx");
        // params.setSheetName("客诉修改明细");
        response.reset();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 由于传入的结束时间是 那天 的0点不符合实际需求，改为当天23:59分
        Date startday = null;
        Date endday = null;
        if(startdate!= null && !startdate.equals(""))
            startday = sdf.parse(startdate);
        if(enddate != null && !enddate.equals("")) {
            endday = sdf.parse(enddate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(endday);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            endday = cal.getTime();
        }
        List<FinanceDebit> list = financeDebitService.getListWithOptional(startday, endday, INVOICE_NO, CUSTOMER_NUMBER,CUSTOMER_CODE);


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
        DecimalFormat usFormat = new DecimalFormat("#.00");
        String new_price = usFormat.format(Double.valueOf(unitprice));
        // 将千分位数字转换正常数量
        double normal_decrease_amount = 0;
        try {
            normal_decrease_amount = new DecimalFormat().parse(decrease_amount).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("扣款单扣款金额 千分位转换失败 , BASE_UID: " + base_uid);
        }

        financeDebit.setDecrease_amount(normal_decrease_amount+"");

        // 新建扣减扣款单对象，复制扣款单的信息
        FinanceDebit decreaseFinanceDebit = new FinanceDebit();
        BeanUtils.copyProperties(financeDebit,decreaseFinanceDebit);
        decreaseFinanceDebit.setOrganization_id(org_id);
        String org_name = "空";
        if(org_id.equals("81")){
            org_name="崇达股份";
        }else if(org_id.equals("83")){
            org_name="深圳崇达";
        }else if(org_id.equals("84")){
            org_name="大连崇达";
        }else if(org_id.equals("85")){
            org_name="江门一厂";
        }else if(org_id.equals("107")){
            org_name="江门二厂";
        }else if(org_id.equals("147")){
            org_name="崇达科技";
        }else if(org_id.equals("187")){
            org_name="大连崇达电子";
        }
        decreaseFinanceDebit.setOrganization_name(org_name);

        int count = financeDebitService.countRmaRecord(decreaseFinanceDebit.getInvoice_no()) + 1;
        // 1、前端对 扣减金额大小做判断，后端插入扣减的财务扣款单记录
        financeDebitService.addDecreaseFinanceDebit(decreaseFinanceDebit);
        log.info("拆分扣减的扣款单记录 {}",decreaseFinanceDebit);
        log.info("扣款单ID : " + base_uid + " ;扣减金额 : " + decrease_amount);
        // 2、 更新该扣款单的剩余的索赔金额
        String new_amount = Double.valueOf(new_price) - Double.valueOf(normal_decrease_amount) + "";
        financeDebitService.updateFinanceDebitAmount(new_amount,base_uid);
        // 3、 插入扣款记录到 ERP
        // 获取当前rma已扣减的记录 + 1,该RMA做分批扣减标识，扣减的扣款单不需要加这个标识，只需要ERP有区分就好了
        String erp_id = decreaseFinanceDebit.getInvoice_no();
        if(!(Double.valueOf(new_amount) == 0 && count == 1)) {
            String invoice_plus = decreaseFinanceDebit.getInvoice_no() + "-" + count;
            erp_id = invoice_plus.substring(3,invoice_plus.length());
        }
        decreaseFinanceDebit.setInvoice_no(erp_id);
        commonUtilsService.insertErpDecreaseFinanaceDebit(decreaseFinanceDebit);

        // 4、 当剩余索赔金额为扣减数量时，删除该扣款单
        if(Double.valueOf(new_amount) < 1 || (new_price != null && new_price.equals(normal_decrease_amount))){
              financeDebitService.deleteFinanceDebit(financeDebit.getBase_uid());
        }
        return  Msg.success();
    }


    @ResponseBody
    @PostMapping("/addOldFinanceDebit")
    public Msg addOldFinanceDebit(@RequestParam("INVOICE_NO") String INVOICE_NO) {
        FinanceDebit financeDebit = financeDebitService.getClaiInfo(INVOICE_NO);
        if (financeDebit != null) {
            FinanceDebit erpDebit = commonUtilsService.getErpFinanceDebitInfo(financeDebit.getOrderhead(),financeDebit.getOrderline());

            FinanceDebit result = CombineFinanceDebit(financeDebit, erpDebit);
            log.info("手动插入一条扣款单 ：{}", result);
            if(result.getUnitprice().equals("0")){
                return Msg.fail().add("content","需扣款金额不能为0，无法录入！");
            }else {
                boolean addFlag = financeDebitService.addFinanceDebit(result);
                if (addFlag)
                    return Msg.success().add("financeDebit", result);
                else
                    return Msg.fail().add("content", "该RMA号已被录入，禁止重复录入！");
            }

        } else {
            return Msg.fail().add("content","该RMA号无法在索赔记录查询到！");
        }
    }

    @RequestMapping("/downloadDebit")
    public void downloadDebit(HttpServletRequest request, HttpServletResponse response){
        String base_uid = request.getParameter("base_uid");
        // 获取最早拆除的那条扣款记录
        FinanceDebit financeDebit = financeDebitService.getDecreaseFinanceDebitById(base_uid);
        String unitprice = financeDebit.getUnitprice();
        DecimalFormat usFormat = new DecimalFormat("#.00");
        String new_price = usFormat.format(Double.valueOf(unitprice));
        financeDebit.setUnitprice(new_price);
        log.info("financeDebit ：{}", financeDebit);
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + financeDebit.getInvoice_no() + ".xls");// 设置文件名
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("form", financeDebit);
            fis = ExcelTool.exportExcel("financedebit.xls", map);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            bos = new BufferedOutputStream(os);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            //开始向网络传输文件流
            while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.flush();
            os.flush();
            os.close();
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            log.info("下载扣款单EXCEL : " + financeDebit.getInvoice_no());
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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
