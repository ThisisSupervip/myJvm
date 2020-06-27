package com.lgb.rtda.heap.constantPool;

import com.lgb.classfile.fundamental.ConstantInfoType;

public class ClassRef extends SymRef {
    public ClassRef(ConstantPool constantPool, ConstantInfoType.ConstantClassInfo classInfo) {
        this.className = classInfo.getName();
        this.constantPool = constantPool;
    }
}
