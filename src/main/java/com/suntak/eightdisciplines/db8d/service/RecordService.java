package com.suntak.eightdisciplines.db8d.service;

import com.suntak.eightdisciplines.entity.AttachFile;
import com.suntak.eightdisciplines.entity.Record;

import java.util.Date;
import java.util.List;


public interface RecordService {

    public int addRecord(Record record);

    public List<Record> getRecordByOptions(Date startdate, Date enddate, String leasts);

    public Record getRecordByRid(String rid);

    void addAttachFile(AttachFile attachFile);
}
