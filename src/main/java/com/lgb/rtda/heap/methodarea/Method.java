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
        MethodDescriptor md = MethodDescriptor.parseMethodDescriptor(this.descriptor);
        this.argSlotCount = calcArgSlotCount(md.getParameterTypes());
        if (isNative()) {
            injectCodeAttribute(md.getReturnType());
        }
    }

    public static Method[] newMethods(Class clazz, MemberInfo[] memberInfos) {
        Method[] methods = new Method[memberInfos.length];
        for (int i = 0; i < memberInfos.length; i++) {
            methods[i] = new Method(clazz, memberInfos[i]);
        }
        return methods;
    }

    private int calcArgSlotCount(List<String> parameterTypes) {
        int argSlotCount = 0;
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

    private void injectCodeAttribute(String returnType) {
        this.maxStack = 4;
        this.maxLocals = this.argSlotCount;

        switch (returnType.getBytes()[0]) {
            case 'V':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xb1};
                break;
            case 'L':
            case '[':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xb0};
                break;
            case 'D':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xaf};
                break;
            case 'F':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xae};
                break;
            case 'J':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xad};
                break;
            default:
                this.code = new byte[]{(byte) 0xfe, (byte) 0xac};
                break;
        }
    }
}
