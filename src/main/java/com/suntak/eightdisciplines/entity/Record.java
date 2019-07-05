package com.suntak.eightdisciplines.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;
import java.util.List;

@EntityScan
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

    private List<AttachFile> attachFiles;

    public List<AttachFile> getAttachFiles() {
        return attachFiles;
    }

    public void setAttachFiles(List<AttachFile> attachFiles) {
        this.attachFiles = attachFiles;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getModify_username() {
        return modify_username;
    }

    public void setModify_username(String modify_username) {
        this.modify_username = modify_username;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getLeasts() {
        return leasts;
    }

    public void setLeasts(String leasts) {
        this.leasts = leasts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    @Override
    public String toString() {
        return "Record{" +
                "rid='" + rid + '\'' +
                ", modify_username='" + modify_username + '\'' +
                ", emp_id='" + emp_id + '\'' +
                ", create_date=" + create_date +
                ", leasts='" + leasts + '\'' +
                ", customercode='" + customercode + '\'' +
                ", content='" + content + '\'' +
                ", reason='" + reason + '\'' +
                ", attachFiles=" + attachFiles +
                '}';
    }
}
