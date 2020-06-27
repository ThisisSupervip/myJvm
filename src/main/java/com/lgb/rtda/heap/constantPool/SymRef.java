package com.lgb.rtda.heap.constantPool;

import com.lgb.rtda.heap.methodarea.Class;
import lombok.Getter;

public abstract class SymRef {
    @Getter
    protected String className;
    protected ConstantPool constantPool;
    protected Class clazz;

    public Class resolvedClass() {
        if (null != this.clazz) return this.clazz;
        Class d = constantPool.getClazz();
        Class c = d.getClassloader().loadClass(this.className);;
        this.clazz = c;
        return this.clazz;
    }
}
