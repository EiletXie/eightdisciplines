package com.suntak.eightdisciplines.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;

@EntityScan
public class AttachFile {
    private String fileid;
    private String rid;
    private String filepath;
    private String filesize;
    private String filename;
    private Date create_date;


    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    @Override
    public String toString() {
        return "AttachFile{" +
                "fileid='" + fileid + '\'' +
                ", rid='" + rid + '\'' +
                ", filepath='" + filepath + '\'' +
                ", filesize='" + filesize + '\'' +
                ", filename='" + filename + '\'' +
                ", create_date=" + create_date +
                '}';
    }
}
