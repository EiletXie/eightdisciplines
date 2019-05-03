package com.suntak.eightdisciplines.dbErp.dao;

import com.suntak.eightdisciplines.entity.BlameProcess;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * ERP 公共查询类
 */
@Qualifier("dbErpSqlSessionTemplate")
public interface CommonUtilsDao {

    /**
     * 通过org_id 和 item_id 获取 责任工序选项
     * @param org_id
     * @param item_id
     * @return
     */
    List<BlameProcess> getBlameSelectOptions(@Param("org_id") String org_id,@Param("item_id") String item_id);
}
