package com.victor.pruebas.data.entity;

import java.util.List;

public class ErrorEntity {

    private String code;
    private List<String> messages;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}