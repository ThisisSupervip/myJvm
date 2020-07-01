package com.lgb.rtda.heap.methodarea;

import com.lgb.classfile.fundamental.AttributeInfoType;
import com.lgb.classfile.fundamental.MemberInfo;
import com.lgb.rtda.heap.MethodDescriptor;
import com.sun.org.apache.bcel.internal.Constants;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

public class Method extends ClassMember {

    @Getter
    private int maxStack;
    @Getter
    private int maxLocals;
    @Getter
    private byte[] code;
    public final int argSlotCount;

    public Method(Class clazz, MemberInfo memberInfo) {
        super(clazz, memberInfo);
        AttributeInfoType.CodeAttribute codeAttribute = memberInfo.codeAttribute();
        if(Objects.nonNull(codeAttribute)) {
            this.maxStack = codeAttribute.maxStack.intValue;
            this.maxLocals = codeAttribute.maxLocals.intValue;
            this.code = codeAttribute.code;
        }
        this.argSlotCount = calcArgSlotCount();
    }

    public static Method[] newMethods(Class clazz, MemberInfo[] memberInfos) {
        Method[] methods = new Method[memberInfos.length];
        for (int i = 0; i < memberInfos.length; i++) {
            //忽略native方法
            /*if((memberInfos[i].getAccessFlags().intValue & Constants.ACC_NATIVE) != 0){
                continue;
            }*/
            methods[i] = new Method(clazz, memberInfos[i]);
        }
        return methods;
    }

    private int calcArgSlotCount() {
        int argSlotCount = 0;
        MethodDescriptor methodDescriptor = MethodDescriptor.parseMethodDescriptor(this.descriptor);
        List<String> parameterTypes = methodDescriptor.getParameterTypes();
        for (String parameterType : parameterTypes) {
            argSlotCount++;
            if("J".equals(parameterType) || "D".equals(parameterType)){//Long Double
               argSlotCount++;
            }
        }
        if(!isStatic()){
            argSlotCount++;
        }
        return argSlotCount;
    }

    public boolean isAbstract() {
        return 0 != (this.accessFlags & Constants.ACC_ABSTRACT);
    }

    public boolean isNative() {
        return 0 != (this.accessFlags & Constants.ACC_NATIVE);
    }
}
