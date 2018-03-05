package com.qiquan.model;

public class Stock {
    private String symbol;

    private String symbolName;

    private String insureDate;

    private String optionType;

    private String priceMode;

    private String offerPriceDate;

    private String symbolPrefix;

    private String optionCode;

    private String cycle;

    private String offerPrice;

    private String expireDate;

    private String exerciseMode;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName == null ? null : symbolName.trim();
    }

    public String getInsureDate() {
        return insureDate;
    }

    public void setInsureDate(String insureDate) {
        this.insureDate = insureDate == null ? null : insureDate.trim();
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType == null ? null : optionType.trim();
    }

    public String getPriceMode() {
        return priceMode;
    }

    public void setPriceMode(String priceMode) {
        this.priceMode = priceMode == null ? null : priceMode.trim();
    }

    public String getOfferPriceDate() {
        return offerPriceDate;
    }

    public void setOfferPriceDate(String offerPriceDate) {
        this.offerPriceDate = offerPriceDate == null ? null : offerPriceDate.trim();
    }

    public String getSymbolPrefix() {
        return symbolPrefix;
    }

    public void setSymbolPrefix(String symbolPrefix) {
        this.symbolPrefix = symbolPrefix == null ? null : symbolPrefix.trim();
    }

    public String getOptionCode() {
        return optionCode;
    }

    public void setOptionCode(String optionCode) {
        this.optionCode = optionCode == null ? null : optionCode.trim();
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle == null ? null : cycle.trim();
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice == null ? null : offerPrice.trim();
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate == null ? null : expireDate.trim();
    }

    public String getExerciseMode() {
        return exerciseMode;
    }

    public void setExerciseMode(String exerciseMode) {
        this.exerciseMode = exerciseMode == null ? null : exerciseMode.trim();
    }
}