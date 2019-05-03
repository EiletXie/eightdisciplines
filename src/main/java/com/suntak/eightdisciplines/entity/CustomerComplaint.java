package com.suntak.eightdisciplines.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

/**
 * 客诉表
 * create by cxie 2019/4/2
 */
@EntityScan
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBase_uid() {
        return base_uid;
    }

    public void setBase_uid(String base_uid) {
        this.base_uid = base_uid;
    }

    public String getCustomcode() {
        return customcode;
    }

    public void setCustomcode(String customcode) {
        this.customcode = customcode;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomnumber() {
        return customnumber;
    }

    public void setCustomnumber(String customnumber) {
        this.customnumber = customnumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOrderhead() {
        return orderhead;
    }

    public void setOrderhead(String orderhead) {
        this.orderhead = orderhead;
    }

    public String getOrderline() {
        return orderline;
    }

    public void setOrderline(String orderline) {
        this.orderline = orderline;
    }

    public String getNew_company_id() {
        return new_company_id;
    }

    public void setNew_company_id(String new_company_id) {
        this.new_company_id = new_company_id;
    }

    public String getDefedtdesc() {
        return defedtdesc;
    }

    public void setDefedtdesc(String defedtdesc) {
        this.defedtdesc = defedtdesc;
    }

    public String getSalesprice() {
        return salesprice;
    }

    public void setSalesprice(String salesprice) {
        this.salesprice = salesprice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBadcategory() {
        return badcategory;
    }

    public void setBadcategory(String badcategory) {
        this.badcategory = badcategory;
    }

    public String getClaimtypes() {
        return claimtypes;
    }

    public void setClaimtypes(String claimtypes) {
        this.claimtypes = claimtypes;
    }

    public String getLeasts() {
        return leasts;
    }

    public void setLeasts(String leasts) {
        this.leasts = leasts;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShippcs() {
        return shippcs;
    }

    public void setShippcs(String shippcs) {
        this.shippcs = shippcs;
    }

    public String getLayers() {
        return layers;
    }

    public void setLayers(String layers) {
        this.layers = layers;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getSalesarea() {
        return salesarea;
    }

    public void setSalesarea(String salesarea) {
        this.salesarea = salesarea;
    }

    public String getCustomprocess() {
        return customprocess;
    }

    public void setCustomprocess(String customprocess) {
        this.customprocess = customprocess;
    }

    public String getCustomlevel() {
        return customlevel;
    }

    public void setCustomlevel(String customlevel) {
        this.customlevel = customlevel;
    }

    public String getRevokeresult() {
        return revokeresult;
    }

    public void setRevokeresult(String revokeresult) {
        this.revokeresult = revokeresult;
    }

    public List<BlameProcess> getBlameProcesses() {
        return blameProcesses;
    }

    public void setBlameProcesses(List<BlameProcess> blameProcesses) {
        this.blameProcesses = blameProcesses;
    }


    public String getMfg_org_id() {
        return mfg_org_id;
    }

    public void setMfg_org_id(String mfg_org_id) {
        this.mfg_org_id = mfg_org_id;
    }

    public String getInventory_item_id() {
        return inventory_item_id;
    }

    public void setInventory_item_id(String inventory_item_id) {
        this.inventory_item_id = inventory_item_id;
    }

    public List<BlameProcess> getOutBlameProcesses() {
        return outBlameProcesses;
    }

    public void setOutBlameProcesses(List<BlameProcess> outBlameProcesses) {
        this.outBlameProcesses = outBlameProcesses;
    }

    @Override
    public String toString() {
        return "CustomerComplaint{" +
                "base_uid='" + base_uid + '\'' +
                ", customcode='" + customcode + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", customnumber='" + customnumber + '\'' +
                ", model='" + model + '\'' +
                ", orderhead='" + orderhead + '\'' +
                ", orderline='" + orderline + '\'' +
                ", new_company_id='" + new_company_id + '\'' +
                ", defedtdesc='" + defedtdesc + '\'' +
                ", salesprice='" + salesprice + '\'' +
                ", currency='" + currency + '\'' +
                ", badcategory='" + badcategory + '\'' +
                ", claimtypes='" + claimtypes + '\'' +
                ", leasts='" + leasts + '\'' +
                ", category='" + category + '\'' +
                ", shippcs='" + shippcs + '\'' +
                ", layers='" + layers + '\'' +
                ", producttype='" + producttype + '\'' +
                ", salesarea='" + salesarea + '\'' +
                ", customprocess='" + customprocess + '\'' +
                ", customlevel='" + customlevel + '\'' +
                ", revokeresult='" + revokeresult + '\'' +
                ", mfg_org_id='" + mfg_org_id + '\'' +
                ", inventory_item_id='" + inventory_item_id + '\'' +
                ", status='" + status + '\'' +
                ", blameProcesses=" + blameProcesses +
                ", outBlameProcesses=" + outBlameProcesses +
                '}';
    }
}
