package com.suntak.eightdisciplines.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suntak.eightdisciplines.entity.Msg;
import com.suntak.eightdisciplines.entity.Record;
import com.suntak.eightdisciplines.db8d.service.RecordService;
import org.apache.poi.ss.usermodel.Workbook;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Controller
@RequestMapping("/record")
public class RecordController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RecordController.class);

    @Resource
    RecordService recordService;

    @ResponseBody
    @PostMapping("/records")
    public Msg findRecords(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
                           @RequestParam("startdate") String startdate,
                           @RequestParam("enddate") String enddate,
                           @RequestParam("leasts") String leasts) throws Exception {
        PageHelper.startPage(pn, 5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 由于传入的结束时间是 那天 的0点不符合实际需求，改为当天23:59分
        Date startday = sdf.parse(startdate);
        Date endday = sdf.parse(enddate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(endday);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        endday = cal.getTime();
        List<Record> list = recordService.getRecordByOptions(startday, endday, leasts);
        PageInfo<Record> page = new PageInfo<Record>(list, 5);
        return Msg.success().add("pageInfo", page);
    }

    @ResponseBody
    @PostMapping("/recordByRid")
    public Msg findRecordByRid(@RequestParam("rid") String rid) {
        Record record = recordService.getRecordByRid(rid);
        return Msg.success().add("record", record);
    }

    @GetMapping("/complaintRecord")
    public String toRecord() {
        return "complaintRecord";
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
        List<Record> list = recordService.getRecordByOptions(startday, endday, "");


        //ExcelExportUtil.exportExcel( new ExportParams("8D客诉修改记录明细","客诉修改明细"),Record.class, list)
        // 这种是帮你 自行创建 EXCEL表格
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("8D修改数据明细表", "UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), Record.class, list);
        workbook.write(response.getOutputStream());
    }


    @RequestMapping("/download")
    public void file(HttpServletRequest request, HttpServletResponse response) {
        String filepath = request.getParameter("filepath");
        String filename = request.getParameter("filename");
        File file = new File(filepath);


        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + filename);// 设置文件名
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                bos = new BufferedOutputStream(os);
                while (true) {
                    int datum = bis.read();
                    if (datum == -1)
                        break;
                    bos.write(datum);
                }

                os.close();
                response.flushBuffer();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            } finally {
                logger.info("下载文件 : " + filename);
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
    }

}
