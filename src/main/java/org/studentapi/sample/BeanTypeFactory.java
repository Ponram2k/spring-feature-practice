package org.studentapi.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BeanTypeFactory {

    @Autowired
    private Map<String,BeanType> beanTypeMap;

    public void printBeanType(String option){
        BeanType type = beanTypeMap.get(option);
        type.name();
    }
}
