package com.suntak.eightdisciplines.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;
import java.util.List;

@Data
public class Record {

    private String rid;


    @Excel(name = "修改人", width = 20)
    private String modify_username;
    @Excel(name = "工号", width = 20)
    private String emp_id;
    @Excel(name = "日期", width = 25, exportFormat = "yyyy-MM-dd HH:mm")
    private Date create_date;
    @Excel(name = "CAR号", width = 30)
    private String leasts;
    @Excel(name = "客户编码", width = 35)
    private String customercode;
    @Excel(name = "修改内容", width = 60)
    private String content;
    @Excel(name = "修改理由", width = 50)
    private String reason;

   List<AttachFile> attachFiles;
}
