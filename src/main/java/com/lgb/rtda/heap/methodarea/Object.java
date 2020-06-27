package com.lgb.rtda.heap.methodarea;

import com.lgb.rtda.LocalVariables;

public class Object {
    public final Class clazz;
    public final LocalVariables fields;

    public Object(Class clazz) {
        this.clazz = clazz;
        this.fields = new LocalVariables(clazz.getInstanceSlotCount());
    }

    public boolean isInstanceOf(Class clazz) {
        return clazz.isAssignableFrom(this.clazz);
    }
}
