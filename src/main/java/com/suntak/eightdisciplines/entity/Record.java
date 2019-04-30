package com.suntak.eightdisciplines.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;

@EntityScan
public class Record {

    private String rid;
    private String modify_username;
    private String emp_id;
    private Date create_date;
    private String leasts;
    private String customercode;
    private String content;
    private String reason;

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
                ", empId='" + emp_id + '\'' +
                ", create_date='" + create_date + '\'' +
                ", leasts='" + leasts + '\'' +
                ", customerCode='" + customercode + '\'' +
                ", content='" + content + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
