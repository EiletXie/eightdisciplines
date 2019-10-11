package com.suntak.eightdisciplines.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;

@Data
public class AttachFile {
    private String fileid;
    private String rid;
    private String filepath;
    private String filesize;
    private String filename;
    private Date create_date;

}
