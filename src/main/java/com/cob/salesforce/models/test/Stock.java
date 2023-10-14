package com.cob.salesforce.models.test;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Stock {
    String name;
    String icon;
    float price;
    boolean increased;

    public Stock(String name, String icon, float price) {
        this.name = name;
        this.icon = icon;
        this.price = price;
    }

}
