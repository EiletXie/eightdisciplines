package com.suntak.eightdisciplines.db8d.dao;

import com.suntak.eightdisciplines.entity.BlameProcess;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Qualifier("db8dSqlSessionTemplate")
public interface BlameProcessDao {

    /**
     * 获取责任工序
     * @param base_uid
     * @return
     */
    List<BlameProcess> getBlamesByBaseUid(String base_uid);

    /**
     * 删除责任工序
     * @param base_uid
     */
    void deleteBlamesByBaseUid(String base_uid);

    /**
     * 添加责任工序
     * @param blames
     */
    void addBlameProcess(BlameProcess blames);


}
