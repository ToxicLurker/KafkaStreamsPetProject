package com.processing.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SimpleMessage {
    public String message;
    public String[] message2;
    public ComplexType message3;
    public int message4;

    public SimpleMessage(String message,
            String[] message2,
            ComplexType message3,
            int message4) {
        this.message = message;
        this.message2 = message2;
        this.message3 = message3;
        this.message4 = message4;

    }

    public SimpleMessage() {

    }

    @JsonIgnore
    public String getContent() {
        Map<String, String> return_map = new HashMap<>();
        return_map.put("message", this.message);
        return_map.put("message3", Arrays.toString(this.message2));
        return_map.put("message3", this.message3.toString());
        return_map.put("message4", Integer.valueOf(this.message4).toString());
        return return_map.toString();

    }
}
