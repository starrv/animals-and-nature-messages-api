package com.animalsandnature.messages;

import com.fasterxml.jackson.annotation.JsonView;

public class Mail {

    @JsonView(WithMailView.class)
    private Header[] headers;

    @JsonView(WithMailView.class)
    private String timestamp;

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Mail(){}

    @Override
    public String toString(){
        StringBuilder result=new StringBuilder("Mail: ");
        final Header[] headers=getHeaders();
        final int numOfHeaders=headers.length;
        for(int i=0; i<numOfHeaders; i++){
            result.append(headers[i].toString());
            result.append(",");
        }
        result.append("timestamp: "+timestamp);
        return result.toString();
    }
}
