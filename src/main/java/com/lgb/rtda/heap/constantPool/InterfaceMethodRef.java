package com.lgb.rtda.heap.constantPool;

import com.lgb.classfile.fundamental.ConstantInfoType;

public class InterfaceMethodRef extends MemberRef {
    public InterfaceMethodRef(ConstantPool constantPool, ConstantInfoType.ConstantMemberRefInfo refInfo) {
        super(constantPool, refInfo);
    }
}
