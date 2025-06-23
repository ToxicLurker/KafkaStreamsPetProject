package com.processing.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ComplexType {
    private String submessage1;
    private String[] submessage2;

    public ComplexType(String submessage1, String[] submessage2) {
        this.submessage1 = submessage1;
        this.submessage2 = submessage2;
    }

    public ComplexType() {

    }

    public void setSubmessage1(String submessage1) {
        this.submessage1 = submessage1;
    }

    public String getSubmessage1() {
        return this.submessage1;
    }

    public void setSubmessage2(String[] submessage2) {
        this.submessage2 = submessage2;
    }

    public String[] getSubmessage2() {
        return this.submessage2;
    }

    @Override
    public String toString() {
        Map<String, String> return_map = new HashMap<>();
        return_map.put("submessage1", getSubmessage1());
        return_map.put("submessage2", Arrays.toString(getSubmessage2()));
        return return_map.toString();
    }

}