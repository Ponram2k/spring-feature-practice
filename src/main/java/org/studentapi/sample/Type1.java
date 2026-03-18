package org.studentapi.sample;

import org.springframework.stereotype.Component;

@Component("type1")
public class Type1 implements BeanType{
    @Override
    public void name() {
        System.out.println("Bean Type 1");
    }
}
