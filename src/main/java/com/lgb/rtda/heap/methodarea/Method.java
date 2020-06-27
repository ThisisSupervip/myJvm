package com.lgb.rtda.heap.methodarea;

import com.lgb.classfile.fundamental.AttributeInfoType;
import com.lgb.classfile.fundamental.MemberInfo;
import com.sun.org.apache.bcel.internal.Constants;

public class Method extends ClassMember {
    public final int maxStack;
    public final int maxLocals;
    public final byte[] code;

    public Method(Class clazz, MemberInfo memberInfo) {
        super(clazz, memberInfo);
        AttributeInfoType.CodeAttribute codeAttribute = memberInfo.codeAttribute();
        this.maxStack = codeAttribute.maxStack.intValue;
        this.maxLocals = codeAttribute.maxLocals.intValue;
        this.code = codeAttribute.code;
    }

    public static Method[] newMethods(Class clazz, MemberInfo[] memberInfos) {
        Method[] methods = new Method[memberInfos.length];
        for (int i = 0; i < memberInfos.length; i++) {
            //忽略native方法
            if((memberInfos[i].getAccessFlags().intValue & Constants.ACC_NATIVE) != 0){
                continue;
            }
            methods[i] = new Method(clazz, memberInfos[i]);
        }
        return methods;
    }
}
