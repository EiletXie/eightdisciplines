package com.suntak.eightdisciplines.dbErp.service;

import com.suntak.eightdisciplines.entity.BlameProcess;

import java.util.List;

public interface CommonUtilsService {


    /**
     * 通过org_id 和 item_id 获取 责任工序选项
     *
     * @param org_id
     * @param item_id
     * @return
     */
    List<BlameProcess> getBlameSelectOptions(String org_id, String item_id);
}
