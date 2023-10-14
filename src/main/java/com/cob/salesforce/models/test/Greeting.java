package com.cob.salesforce.models.test;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Greeting {
    private String content;

    public Greeting(String content) {
        this.content = content;
    }
}
