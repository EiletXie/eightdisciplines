package com.suntak.eightdisciplines.dao;

import com.suntak.eightdisciplines.entity.CustomerComplaint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerComplaintDaoTest {

//    @Resource
//    CustomerComplaintDao customerComplaintDao;
//
//    @Test
//    public void getComplaintsByCar() {
//        List<CustomerComplaint> list = customerComplaintDao.getComplaintsByCar("CAR.NO-11123172");
//        System.out.println(list.get(1));
//        assertEquals(1,list.size());
//    }
}