package com.suntak.eightdisciplines.db8d.dao;

import com.suntak.eightdisciplines.entity.Record;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.List;

@Qualifier("db8dSqlSessionTemplate")
public interface RecordDao {
    void addRecord(Record record);

    List<Record> getRecordByOptions(Date startdate, Date enddate, @Param("leasts") String leasts);

    Record getRecordByRid(String rid);

}
