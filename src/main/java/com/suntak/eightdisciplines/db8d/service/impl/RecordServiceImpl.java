package com.suntak.eightdisciplines.db8d.service.impl;

import com.suntak.eightdisciplines.db8d.dao.RecordDao;
import com.suntak.eightdisciplines.db8d.service.RecordService;
import com.suntak.eightdisciplines.entity.Record;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Resource
    RecordDao recordDao;

    @Override
    public void addRecord(Record record) {
        recordDao.addRecord(record);
    }

    @Override
    public List<Record> getRecordByOptions(Date startdate, Date enddate,String leasts) {
        return recordDao.getRecordByOptions(startdate,enddate,leasts);
    }

    @Override
    public Record getRecordByRid(String rid) {
        return recordDao.getRecordByRid(rid);
    }
}
