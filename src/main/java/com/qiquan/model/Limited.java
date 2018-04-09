package com.qiquan.model;

public class Limited {
    private String symbol;

    private String symbolname;

    private String up;

    private String one;

    private String date;

    private String temp;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public String getSymbolname() {
        return symbolname;
    }

    public void setSymbolname(String symbolname) {
        this.symbolname = symbolname == null ? null : symbolname.trim();
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up == null ? null : up.trim();
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one == null ? null : one.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp == null ? null : temp.trim();
    }
}