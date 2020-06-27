package com.lgb.rtda.heap.constantPool;

import com.lgb.classfile.fundamental.ConstantInfoType;

public class MethodRef extends MemberRef {
    public MethodRef(ConstantPool constantPool, ConstantInfoType.ConstantMemberRefInfo refInfo) {
        super(constantPool, refInfo);
    }
}
