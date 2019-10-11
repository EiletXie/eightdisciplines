package com.suntak.eightdisciplines.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

/**
 * 客诉表
 * create by cxie 2019/4/2
 */
@Data
public class CustomerComplaint {
    private String base_uid;
    private String customcode;
    private String customer_id;
    private String customnumber;
    private String model; //生产型号
    private String orderhead;
    private String orderline;
    private String new_company_id;
    private String defedtdesc; // 缺陷描述
    private String salesprice;
    private String currency; // 币别
    private String badcategory; // 客户品名
    private String claimtypes; // 投诉类别
    private String leasts; // CAR号
    private String category;
    private String shippcs; // 出货数量
    private String layers; // 层数
    private String producttype;
    private String salesarea; //销售区域
    private String customprocess; //处理类别
    private String customlevel; // 客诉等级
    private String revokeresult; // 不计入理由
    private String mfg_org_id; // 组织ID
    private String inventory_item_id; // 型号ID

    private String status; // 表单状态
    private List<BlameProcess> blameProcesses; // 责任产生工序
    private List<BlameProcess> outBlameProcesses; // 责任流出工序


}
