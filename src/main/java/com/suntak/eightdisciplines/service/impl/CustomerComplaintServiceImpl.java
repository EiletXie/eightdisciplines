package com.suntak.eightdisciplines.service.impl;

import com.suntak.eightdisciplines.dao.CustomerComplaintDao;
import com.suntak.eightdisciplines.entity.CustomerComplaint;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("customerComplaintService")
public class CustomerComplaintServiceImpl implements  CustomerComplaintService{

    @Resource
    CustomerComplaintDao customerComplaintDao;

    public List<CustomerComplaint> getComplaintsByCar(String leasts){
        return customerComplaintDao.getComplaintsByCar(leasts);
    }

}
