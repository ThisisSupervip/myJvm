package com.lgb.rtda.heap.constantPool;

import com.lgb.classfile.fundamental.ConstantInfoType;
import lombok.Getter;

public abstract class MemberRef extends SymRef {

    @Getter
    protected String name;
    @Getter
    protected String descriptor;

    public MemberRef(ConstantPool constantPool, ConstantInfoType.ConstantMemberRefInfo refInfo) {
        this.className = refInfo.className();
        this.name = refInfo.getName();
        this.descriptor = refInfo.getDescriptor();
        this.clazz = clazz;
        this.constantPool = constantPool;
    }
}
