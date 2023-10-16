package com.cob.salesforce.exceptions.business;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class SalesforceException extends Exception  {
    private HttpStatus status;
    private Object[] parameters;
    private String code;
    private String message;

    public SalesforceException(String message) {
        super(message);
    }
    public SalesforceException(String code, Object[] parameters) {
        super(code);
        this.code = code;
        this.parameters = parameters;
    }
    public SalesforceException(HttpStatus status, String code, Object[] parameters) {
        super(code);
        this.code = code;
        this.status = status;
        this.parameters = parameters;
    }
    enum Category {
        Validation("VAL"),
        Business("BUS"),
        System("SYS");

        private String type;

        Category(String type) {
            this.type = type;
        }

        public String value() {
            return type;
        }
    }
}
