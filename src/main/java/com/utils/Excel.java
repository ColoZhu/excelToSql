package com.utils;


public class Excel {
    private String item;    //属性列
    private String type;    //类型
    private String isNeed;  //是否必填
    private String annotation;  //注释

    @Override
    public String toString() {
        return "Excel{" +
                "item='" + item + '\'' +
                ", type='" + type + '\'' +
                ", isNeed='" + isNeed + '\'' +
                ", annotation='" + annotation + '\'' +
                '}';
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsNeed() {
        return isNeed;
    }

    public void setIsNeed(String isNeed) {
        this.isNeed = isNeed;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }
}
