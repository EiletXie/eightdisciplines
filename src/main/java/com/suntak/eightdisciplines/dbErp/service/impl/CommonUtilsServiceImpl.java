package com.suntak.eightdisciplines.dbErp.service.impl;

import com.suntak.eightdisciplines.dbErp.dao.CommonUtilsDao;
import com.suntak.eightdisciplines.dbErp.service.CommonUtilsService;
import com.suntak.eightdisciplines.entity.BlameProcess;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommonUtilsServiceImpl implements CommonUtilsService {

    @Resource
    CommonUtilsDao commonUtilsDao;

    @Override
    public List<BlameProcess> getBlameSelectOptions(String org_id, String item_id){
        return commonUtilsDao.getBlameSelectOptions(org_id,item_id);
    }
}
