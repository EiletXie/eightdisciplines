package com.suntak.eightdisciplines.db8d.service;

import com.suntak.eightdisciplines.entity.CustomerComplaint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CustomerComplaintService {


    /**
     * 通过CAR号获取相应的客诉组
     * @param leasts
     * @return
     */
    List<CustomerComplaint> getComplaintsByCar(String leasts);

    /**
     * 通过id获取客诉信息
     * @param base_uid
     * @return
     */
    CustomerComplaint getComplaintByBaseUid(String base_uid);

    /**
     * 删除客诉，更新BASE_FORM状态为‘T’
     * @param base_uid
     */
    void deleteComplaint(String base_uid);

    /**
     * 更新客诉
     * @param customerComplaint
     */
    void updateComplaint(CustomerComplaint customerComplaint);

    /**
     * 传入字符编码，获取具体含义解释
     * @param code
     * @param code_type
     * @return
     */
    String getMeaningByCode(String code, String code_type);

    /**
     * 传入新修改的客诉对象，与数据库的未修改客诉对象进行比对
     * @param complaint
     * @return 内容前后变更情况
     */
    String getComplaintChangeContent(CustomerComplaint complaint);


    /**
     * 初始化下拉列表
     * @return
     */
    HashMap<String,Object> generateSelectList();
}
