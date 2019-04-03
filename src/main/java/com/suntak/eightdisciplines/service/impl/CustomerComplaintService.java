package com.suntak.eightdisciplines.service.impl;

import com.suntak.eightdisciplines.dao.CustomerComplaintDao;
import com.suntak.eightdisciplines.entity.CustomerComplaint;

import javax.annotation.Resource;
import java.util.List;

public interface CustomerComplaintService {


    List<CustomerComplaint> getComplaintsByCar(String leasts);

}
