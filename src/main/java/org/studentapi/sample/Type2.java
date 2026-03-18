package org.studentapi.sample;

import org.springframework.stereotype.Component;

@Component("type2")
public class Type2 implements BeanType{
    @Override
    public void name() {
        System.out.println("Bean Type 2");
    }
}
