package com.suntak.eightdisciplines.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: CXIE
 * @Date: 2019/9/9 13:58
 * @Description:
 */
@Data
public class FinanceDebit {
    private String base_uid;

    @Excel(name = "生产型号", width = 20)
    private String model; // 生产型号
    @Excel(name = "客户PO号", width = 20)
    private String customer_po; // 客户PO号
    @Excel(name = "订单头", width = 15)
    private String orderhead; // 订单头
    @Excel(name = "订单行", width = 15)
    private String orderline; // 订单行
    @Excel(name = "客户品名", width = 20)
    private String customer_category; // 客户品名 原字段名称 BADCATEGORY
    @Excel(name = "客户名称", width = 20)
    private String customer_code;
    @Excel(name = "客户编码", width = 20)
    private String customer_number;

    @Excel(name = "UNIT", width = 10)
    private String unit; // 索赔单位 PCS
    @Excel(name = "QTY", width = 10)
    private String qty; // 数量 默认为 -1
    @Excel(name = "总金额", width = 10)
    private String unitprice; // 合计金额-（PCB数量*单价）
    @Excel(name = "合计金额", width = 10)
    private String total_amount; // 合计金额

    @Excel(name = "收货", width = 35)
    private String receive_to; // 收货TO
    @Excel(name = "收货人电话", width = 15)
    private String receive_tel; // 收货TEL
    @Excel(name = "收货地址", width = 20)
    private String receive_bill_to; // 收货BILL
    private String receive_ship_to; // 收货SHIP
    private String receive_contact; // 收货联系人
    @Excel(name = "币别", width = 10)
    private String currency; // 收货币别
    private String receive_fax; // 收货传真
    @Excel(name = "收货方式", width = 10)
    private String fob_point_code; // 收货方式
    @Excel(name = "收货国家", width = 10)
    private String receive_country; // 收货国家

    @Excel(name = "发货公司", width = 35)
    private String send_from; // 发货公司 FROM
    @Excel(name = "发货地址", width = 35)
    private String send_address; // 发货地址
    private String send_tel; // 发货电话
    @Excel(name = "发货邮编", width = 10)
    private String send_postcode; // 收货公司邮编
    private String send_fax; // 发货传真
    private String terms_name; // 付款条件代码
    private String print_times; // 打印次数
    @Excel(name = "日期", width = 10)
    private String invoice_date;
    private String data_create;
    @Excel(name = "RMA", width = 20)
    private String invoice_no;  // 对应索赔单号
    @Excel(name = "产品信息", width = 40)
    private String product_info;
    private String status;

    private String price;    // 单板价格
    private String pcbnum;  // pcb数量
    private String clamtotal; // 总扣款金额
    private Date date_create;

    // 下面的几个字段作为已扣款单数据
    private String decrease_amount;
    private String decr_id;

    private String organization_id;
    private String organization_name;
}
