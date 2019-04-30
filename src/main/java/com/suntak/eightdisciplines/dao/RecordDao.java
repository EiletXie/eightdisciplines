package com.suntak.eightdisciplines.dao;

import com.suntak.eightdisciplines.entity.Record;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RecordDao {
    void addRecord(Record record);

    List<Record> getRecordByOptions(Date startdate, Date enddate,@Param("leasts")String leasts);

    Record getRecordByRid(String rid);

}
