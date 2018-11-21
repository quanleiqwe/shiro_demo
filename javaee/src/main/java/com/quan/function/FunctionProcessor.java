package com.quan.function;

import javax.annotation.PostConstruct;
import java.util.HashMap;

public class FunctionProcessor {

    private HashMap<String,FunctionProcessor> hashMap  = new HashMap();

    @PostConstruct
    public void add(){
        hashMap.put(this.getClass().getName(),this.getClass())
    }
}
