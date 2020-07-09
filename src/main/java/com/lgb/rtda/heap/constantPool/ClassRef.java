package com.lgb.rtda.heap.constantPool;

import com.lgb.classfile.fundamental.ConstantInfoType;

/**
 * 类裁判
 *
 * @author Thisisvip
 * @date 2020/07/09
 */
public class ClassRef extends SymRef {
    public ClassRef(ConstantPool constantPool, ConstantInfoType.ConstantClassInfo classInfo) {
        this.className = classInfo.getName();
        this.constantPool = constantPool;
    }
}
