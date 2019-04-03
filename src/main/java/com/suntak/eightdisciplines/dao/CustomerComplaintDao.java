package com.suntak.eightdisciplines.dao;

import com.suntak.eightdisciplines.entity.CustomerComplaint;

import java.util.List;

public interface CustomerComplaintDao {

    /**
     * 通过car号获取相关的客诉信息
     * @param leasts
     * @return
     */
    List<CustomerComplaint> getComplaintsByCar(String leasts);

    /**
     * 更新相关的客诉
     * @param complaint
     * @return
     */
    int updateComplaint(CustomerComplaint complaint);

    /**
     * 根据car号删除客诉
     * @param leasts
     * @return
     */
    int deleteComplaint(String leasts);
}
