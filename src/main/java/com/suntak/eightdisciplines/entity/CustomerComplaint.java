package com.suntak.eightdisciplines.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

/**
 * 客诉表
 * create by cxie 2019/4/2
 */
@EntityScan
public class CustomerComplaint {
    private String BASE_UID;
    private String WD_UID;
    private String WD_NO;
    private String CUSTOMCODE;
    private String CUSTOMER_ID;
    private String DELIVERY;
    private String CUSTOMNUMBER;
    private String CUSTOMDATE;
    private String MODEL; //生产型号
    private String ORDERHEAD;
    private String ORDERLINE;
    private String NEW_COMPANY_ID;
    private String OWN_DEPATMENT;
    private String DEFEDTDESC; // 缺陷描述
    private String BADDESC;
    private String RESPON;
    private String SALESPRICE;
    private String CURRENCY; // 币别
    private String BADCATEGORY; // 客户品名
    private String CLAIMTYPES; // 投诉类别
    private String LEASTS; // CAR号
    private String CATEGORY;
    private String NUMBERSET;
    private String CUSTOMER_NUMBER;
    private String NUMBERPCS;
    private String SHIPSET; //
    private String SHIPPCS; // 出货数量
    private String LAYERS; // 层数
    private String PRODUCTTYPE;
    private String SALESAREA; //销售区域
    private String CUSTOMPROCESS; //处理类别
    private String CUSTOMLEVEL; // 客诉等级
    private String REVOKERESULT; // 不计入理由
    private String BLAME_OPER; // 责任工序

    public String getBASE_UID() {
        return BASE_UID;
    }

    public void setBASE_UID(String BASE_UID) {
        this.BASE_UID = BASE_UID;
    }

    public String getWD_UID() {
        return WD_UID;
    }

    public void setWD_UID(String WD_UID) {
        this.WD_UID = WD_UID;
    }

    public String getWD_NO() {
        return WD_NO;
    }

    public void setWD_NO(String WD_NO) {
        this.WD_NO = WD_NO;
    }

    public String getCUSTOMCODE() {
        return CUSTOMCODE;
    }

    public void setCUSTOMCODE(String CUSTOMCODE) {
        this.CUSTOMCODE = CUSTOMCODE;
    }

    public String getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(String CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public String getDELIVERY() {
        return DELIVERY;
    }

    public void setDELIVERY(String DELIVERY) {
        this.DELIVERY = DELIVERY;
    }

    public String getCUSTOMNUMBER() {
        return CUSTOMNUMBER;
    }

    public void setCUSTOMNUMBER(String CUSTOMNUMBER) {
        this.CUSTOMNUMBER = CUSTOMNUMBER;
    }

    public String getCUSTOMDATE() {
        return CUSTOMDATE;
    }

    public void setCUSTOMDATE(String CUSTOMDATE) {
        this.CUSTOMDATE = CUSTOMDATE;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

    public String getORDERHEAD() {
        return ORDERHEAD;
    }

    public void setORDERHEAD(String ORDERHEAD) {
        this.ORDERHEAD = ORDERHEAD;
    }

    public String getORDERLINE() {
        return ORDERLINE;
    }

    public void setORDERLINE(String ORDERLINE) {
        this.ORDERLINE = ORDERLINE;
    }

    public String getNEW_COMPANY_ID() {
        return NEW_COMPANY_ID;
    }

    public void setNEW_COMPANY_ID(String NEW_COMPANY_ID) {
        this.NEW_COMPANY_ID = NEW_COMPANY_ID;
    }

    public String getOWN_DEPATMENT() {
        return OWN_DEPATMENT;
    }

    public void setOWN_DEPATMENT(String OWN_DEPATMENT) {
        this.OWN_DEPATMENT = OWN_DEPATMENT;
    }

    public String getDEFEDTDESC() {
        return DEFEDTDESC;
    }

    public void setDEFEDTDESC(String DEFEDTDESC) {
        this.DEFEDTDESC = DEFEDTDESC;
    }

    public String getBADDESC() {
        return BADDESC;
    }

    public void setBADDESC(String BADDESC) {
        this.BADDESC = BADDESC;
    }

    public String getRESPON() {
        return RESPON;
    }

    public void setRESPON(String RESPON) {
        this.RESPON = RESPON;
    }

    public String getSALESPRICE() {
        return SALESPRICE;
    }

    public void setSALESPRICE(String SALESPRICE) {
        this.SALESPRICE = SALESPRICE;
    }

    public String getCURRENCY() {
        return CURRENCY;
    }

    public void setCURRENCY(String CURRENCY) {
        this.CURRENCY = CURRENCY;
    }

    public String getBADCATEGORY() {
        return BADCATEGORY;
    }

    public void setBADCATEGORY(String BADCATEGORY) {
        this.BADCATEGORY = BADCATEGORY;
    }

    public String getCLAIMTYPES() {
        return CLAIMTYPES;
    }

    public void setCLAIMTYPES(String CLAIMTYPES) {
        this.CLAIMTYPES = CLAIMTYPES;
    }

    public String getLEASTS() {
        return LEASTS;
    }

    public void setLEASTS(String LEASTS) {
        this.LEASTS = LEASTS;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getNUMBERSET() {
        return NUMBERSET;
    }

    public void setNUMBERSET(String NUMBERSET) {
        this.NUMBERSET = NUMBERSET;
    }

    public String getCUSTOMER_NUMBER() {
        return CUSTOMER_NUMBER;
    }

    public void setCUSTOMER_NUMBER(String CUSTOMER_NUMBER) {
        this.CUSTOMER_NUMBER = CUSTOMER_NUMBER;
    }

    public String getNUMBERPCS() {
        return NUMBERPCS;
    }

    public void setNUMBERPCS(String NUMBERPCS) {
        this.NUMBERPCS = NUMBERPCS;
    }

    public String getSHIPSET() {
        return SHIPSET;
    }

    public void setSHIPSET(String SHIPSET) {
        this.SHIPSET = SHIPSET;
    }

    public String getSHIPPCS() {
        return SHIPPCS;
    }

    public void setSHIPPCS(String SHIPPCS) {
        this.SHIPPCS = SHIPPCS;
    }

    public String getLAYERS() {
        return LAYERS;
    }

    public void setLAYERS(String LAYERS) {
        this.LAYERS = LAYERS;
    }

    public String getPRODUCTTYPE() {
        return PRODUCTTYPE;
    }

    public void setPRODUCTTYPE(String PRODUCTTYPE) {
        this.PRODUCTTYPE = PRODUCTTYPE;
    }

    public String getSALESAREA() {
        return SALESAREA;
    }

    public void setSALESAREA(String SALESAREA) {
        this.SALESAREA = SALESAREA;
    }

    public String getCUSTOMPROCESS() {
        return CUSTOMPROCESS;
    }

    public void setCUSTOMPROCESS(String CUSTOMPROCESS) {
        this.CUSTOMPROCESS = CUSTOMPROCESS;
    }

    public String getCUSTOMLEVEL() {
        return CUSTOMLEVEL;
    }

    public void setCUSTOMLEVEL(String CUSTOMLEVEL) {
        this.CUSTOMLEVEL = CUSTOMLEVEL;
    }

    public String getREVOKERESULT() {
        return REVOKERESULT;
    }

    public void setREVOKERESULT(String REVOKERESULT) {
        this.REVOKERESULT = REVOKERESULT;
    }

    public String getBLAME_OPER() {
        return BLAME_OPER;
    }

    public void setBLAME_OPER(String BLAME_OPER) {
        this.BLAME_OPER = BLAME_OPER;
    }

    @Override
    public String toString() {
        return "CustomerComplaint{" +
                "BASE_UID='" + BASE_UID + '\'' +
                ", WD_UID='" + WD_UID + '\'' +
                ", WD_NO='" + WD_NO + '\'' +
                ", CUSTOMCODE='" + CUSTOMCODE + '\'' +
                ", CUSTOMER_ID='" + CUSTOMER_ID + '\'' +
                ", DELIVERY='" + DELIVERY + '\'' +
                ", CUSTOMNUMBER='" + CUSTOMNUMBER + '\'' +
                ", CUSTOMDATE='" + CUSTOMDATE + '\'' +
                ", MODEL='" + MODEL + '\'' +
                ", ORDERHEAD='" + ORDERHEAD + '\'' +
                ", ORDERLINE='" + ORDERLINE + '\'' +
                ", NEW_COMPANY_ID='" + NEW_COMPANY_ID + '\'' +
                ", OWN_DEPATMENT='" + OWN_DEPATMENT + '\'' +
                ", DEFEDTDESC='" + DEFEDTDESC + '\'' +
                ", BADDESC='" + BADDESC + '\'' +
                ", RESPON='" + RESPON + '\'' +
                ", SALESPRICE='" + SALESPRICE + '\'' +
                ", CURRENCY='" + CURRENCY + '\'' +
                ", BADCATEGORY='" + BADCATEGORY + '\'' +
                ", CLAIMTYPES='" + CLAIMTYPES + '\'' +
                ", LEASTS='" + LEASTS + '\'' +
                ", CATEGORY='" + CATEGORY + '\'' +
                ", NUMBERSET='" + NUMBERSET + '\'' +
                ", CUSTOMER_NUMBER='" + CUSTOMER_NUMBER + '\'' +
                ", NUMBERPCS='" + NUMBERPCS + '\'' +
                ", SHIPSET='" + SHIPSET + '\'' +
                ", SHIPPCS='" + SHIPPCS + '\'' +
                ", LAYERS='" + LAYERS + '\'' +
                ", PRODUCTTYPE='" + PRODUCTTYPE + '\'' +
                ", SALESAREA='" + SALESAREA + '\'' +
                ", CUSTOMPROCESS='" + CUSTOMPROCESS + '\'' +
                ", CUSTOMLEVEL='" + CUSTOMLEVEL + '\'' +
                ", REVOKERESULT='" + REVOKERESULT + '\'' +
                ", BLAME_OPER='" + BLAME_OPER + '\'' +
                '}';
    }
}
