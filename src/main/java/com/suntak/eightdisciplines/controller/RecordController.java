package com.suntak.eightdisciplines.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suntak.eightdisciplines.entity.Msg;
import com.suntak.eightdisciplines.entity.Record;
import com.suntak.eightdisciplines.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/record")
public class RecordController {

    @Resource
    RecordService recordService;

    @ResponseBody
    @PostMapping("/records")
    public Msg findRecords(@RequestParam(value="pn",defaultValue="1") Integer pn,
                           @RequestParam("startdate") String startdate,
                           @RequestParam("enddate") String enddate,
                           @RequestParam("leasts") String leasts)  throws  Exception{
        PageHelper.startPage(pn,5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 由于传入的结束时间是 那天 的0点不符合实际需求，改为当天23:59分
        Date startday = sdf.parse(startdate);
        Date endday = sdf.parse(enddate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(endday);
        cal.add(Calendar.DAY_OF_MONTH,1);
        endday = cal.getTime();
        System.out.println("le----------" + leasts);
        List<Record> list = recordService.getRecordByOptions(startday,endday,leasts);
        PageInfo<Record> page = new PageInfo<Record>(list,5);
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
}
