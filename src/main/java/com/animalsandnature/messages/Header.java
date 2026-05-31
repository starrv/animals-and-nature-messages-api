package com.animalsandnature.messages;

import com.fasterxml.jackson.annotation.JsonView;

public class Header {

    @JsonView(WithMailView.class)
    private String name;

    @JsonView(WithMailView.class)
    private String value;

    public Header(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return "Header: {name:"+getName()+" value: "+getValue()+"}";
    }
}
