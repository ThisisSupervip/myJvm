package com.lgb.rtda.heap.methodarea;

import com.lgb.classfile.fundamental.AttributeInfoType;
import com.lgb.classfile.fundamental.MemberInfo;
import com.lgb.rtda.heap.MethodDescriptor;
import com.lgb.rtda.heap.constantPool.ClassRef;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.util.ClassNameHelper;
import com.sun.org.apache.bcel.internal.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

public class Method extends ClassMember {

    @Getter
    private int maxStack;
    @Getter
    private int maxLocals;
    @Getter @Setter
    private byte[] code;
    private ExceptionTable exceptionTable;
    private AttributeInfoType.LineNumberTableAttribute lineNumberTable;
    public int argSlotCount;
    private AttributeInfoType.ExceptionsAttribute exceptions;

    @Getter
    private byte[] parameterAnnotationData;
    @Getter
    private byte[] annotationDefaultData;

    private MethodDescriptor parsedDescriptor;

    public Method() {
    }

    public Method(Class clazz, MemberInfo memberInfo) {
        super(clazz, memberInfo);
        AttributeInfoType.CodeAttribute codeAttribute = memberInfo.codeAttribute();
        if(Objects.nonNull(codeAttribute)) {
            this.maxStack = codeAttribute.maxStack.intValue;
            this.maxLocals = codeAttribute.maxLocals.intValue;
            this.code = codeAttribute.code;
            this.exceptionTable = new ExceptionTable(codeAttribute.exceptionTable, clazz.getConstantPool());
            this.lineNumberTable = codeAttribute.lineNumberTableAttribute();
            this.exceptions = memberInfo.exceptionsAttribute();
            this.annotationData = memberInfo.runtimeVisibleAnnotationsAttributeData();
            this.parameterAnnotationData = memberInfo.runtimeVisibleParameterAnnotationsAttributeData();
            this.annotationDefaultData = memberInfo.annotationDefaultAttributeData();
        }
        MethodDescriptor md = MethodDescriptor.parseMethodDescriptor(this.descriptor);
        this.parsedDescriptor = md;
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

    public int findExceptionHandler(Class exClass, int pc) {
        if(Objects.isNull(exceptionTable)){
            return -1;
        }
        ExceptionTable.ExceptionHandler handler = this.exceptionTable.findExceptionHandler(exClass, pc);
        if (handler != null) {
            return handler.handlerPC;
        }
        return -1;
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

    public int getLineNumber(int pc) {
        if (this.isNative()) return -2;
        if (this.lineNumberTable == null) return -1;
        return this.lineNumberTable.getLineNumber(pc);
    }

    public boolean isConstructor() {
        return !isStatic() && "<init>".equals(name);
    }

    public boolean isClinit() {
        return isStatic() && "<clinit>".equals(name);
    }

    // reflection
    public Class[] getParameterTypes() {
        if(argSlotCount == 0) {
            return new Class[]{};
        }

        List<String> paramTypes = parsedDescriptor.getParameterTypes();
        Class[] paramClasses = new Class[paramTypes.size()];
        for (int i = 0; i < paramClasses.length; i++) {
            String paramType = paramTypes.get(i);
            String paramClassName = ClassNameHelper.toClassName(paramType);
            paramClasses[i] = clazz.getClassloader().loadClass(paramClassName);
        }

        return paramClasses;
    }

    public Class[] getExceptionTypes() {
        if (exceptions == null){
            return new Class[]{};
        }

        int[] exIndexTable = exceptions.getExceptionIndexTable();
        Class[] exClasses = new Class[exIndexTable.length];
        ConstantPool cp = clazz.getConstantPool();

        for (int i = 0; i < exIndexTable.length; i++) {
            int exIndex = exIndexTable[i];
            ClassRef classRef = (ClassRef) cp.getConstant(exIndex);
            exClasses[i] = classRef.resolvedClass();
        }

        return exClasses;
    }

    public Class getReturnType(){
        String returnType = parsedDescriptor.getReturnType();
        String returnClassName = ClassNameHelper.toClassName(returnType);
        return clazz.getClassloader().loadClass(returnClassName);
    }
}
