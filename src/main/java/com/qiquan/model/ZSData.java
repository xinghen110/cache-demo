package com.qiquan.model;

public class ZSData {

    private transient String productCode;         //股票代码（如：000001.SZ）
    private String min_time;            //数据时间
    private String last_px;             //价格
    //private String ave_px;              //均价
    private String business_amount;     //成交量(股票数量)
    //private String price_rate;          //涨跌幅度


    public ZSData(String min_time, String last_px, String business_amount) {
        this.min_time = min_time;
        this.last_px = last_px;
        this.business_amount = business_amount;
    }

    public ZSData() {
    }

    public String getMin_time() {
        return min_time;
    }

    public void setMin_time(String min_time) {
        this.min_time = min_time;
    }

    public String getLast_px() {
        return last_px;
    }

    public void setLast_px(String last_px) {
        this.last_px = last_px;
    }

    public String getBusiness_amount() {
        return business_amount;
    }

    public void setBusiness_amount(String business_amount) {
        this.business_amount = business_amount;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
