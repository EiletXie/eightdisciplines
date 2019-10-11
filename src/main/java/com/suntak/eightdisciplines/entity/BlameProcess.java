package com.suntak.eightdisciplines.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
public class BlameProcess {

    private String base_uid;
    private String standard_operation_code; // 工序编码
    private String operation_description; // 工序名称
    private String blame_content; // 描述内容
    private String blame_type; // 责任类别： 产生工序 流出工序
    private String comments; // 备注


    public String getBase_uid() {
        return base_uid;
    }

    public void setBase_uid(String base_uid) {
        this.base_uid = base_uid;
    }

    public String getStandard_operation_code() {
        return standard_operation_code;
    }

    public void setStandard_operation_code(String standard_operation_code) {
        this.standard_operation_code = standard_operation_code;
    }

    public String getOperation_description() {
        return operation_description;
    }

    public void setOperation_description(String operation_description) {
        this.operation_description = operation_description;
    }


    public String getBlame_content() {
        return blame_content;
    }

    public void setBlame_content(String blame_content) {
        this.blame_content = blame_content;
    }

    public String getBlame_type() {
        return blame_type;
    }

    public void setBlame_type(String blame_type) {
        this.blame_type = blame_type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "BlameProcess{" +
                "base_uid='" + base_uid + '\'' +
                ", standard_operation_code='" + standard_operation_code + '\'' +
                ", operation_description='" + operation_description + '\'' +
                ", blame_content='" + blame_content + '\'' +
                ", blame_type='" + blame_type + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
