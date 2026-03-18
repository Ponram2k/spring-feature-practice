package org.studentapi.sample;

import org.springframework.stereotype.Component;

@Component("type3")
public class Type3 implements BeanType{
    @Override
    public void name() {
        System.out.println("Bean Type 3");
    }
}
