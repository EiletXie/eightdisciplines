package com.suntak.eightdisciplines.db8d.dao;

import com.suntak.eightdisciplines.entity.CustomerComplaint;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Qualifier("db8dSqlSessionTemplate")
public interface CustomerComplaintDao {

    /**
     * 通过car号获取相关的客诉信息
     * @param leasts
     * @return
     */
    List<CustomerComplaint> getComplaintsByCar(String leasts);

    /**
     * 通过base_uid获取客诉信息
     * @param base_uid
     * @return
     */
    CustomerComplaint getComplaintByBaseUid(String base_uid);

    /**
     * 更新相关的客诉
     * @param complaint
     * @return
     */
    void updateComplaint(CustomerComplaint complaint);

    /**
     * 根据car号删除客诉
     * @param base_uid
     * @return
     */
    void deleteComplaint(String base_uid);


    /**
     * 通过编码获取含义
     * @param code
     * @param code_type
     * @return
     */
    String getMeaningByCode(@Param("code") String code,@Param("code_type") String code_type);


    /**
     * 获取不计入客诉理由下拉列表
     * @return
     */
    List<Map<Object,Object>> generateRevokeresultList();


    /**
     * 获取投诉类别下拉列表
     * @return
     */
    List<Map<Object,Object>> generateClaimtypeList();
}
