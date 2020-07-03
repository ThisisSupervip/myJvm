package com.lgb.rtda.heap.constantPool;

import com.lgb.classfile.ClassFile;
import com.lgb.classfile.fundamental.ConstantInfo;
import com.lgb.classfile.fundamental.ConstantInfoType;
import com.lgb.classfile.fundamental.ConstantNumberInfo;
import com.lgb.rtda.heap.methodarea.Class;

public class ConstantPool {
    private Class clazz;
    private Object[] consts;

    public ConstantPool(Class clazz, ClassFile classFile) {
        ConstantInfo[] cpInfos = classFile.getConstantPool();
        ConstantPool rtCp = this;
        this.clazz = clazz;
        consts = new Object[cpInfos.length];
        try {
            for (int i = 0; i < cpInfos.length; i++) {
                ConstantInfo cpInfo = cpInfos[i];
                if (cpInfo instanceof ConstantNumberInfo) {
                    ConstantNumberInfo numberInfo = (ConstantNumberInfo) cpInfo;
                    consts[i] = numberInfo.getValue();
                } else if (cpInfo instanceof ConstantInfoType.ConstantStringInfo) {
                    consts[i] = ((ConstantInfoType.ConstantStringInfo) cpInfo).getStringVal();
                } else if (cpInfo instanceof ConstantInfoType.ConstantClassInfo) {
                    ConstantInfoType.ConstantClassInfo classInfo = (ConstantInfoType.ConstantClassInfo) cpInfo;
                    consts[i] = new ClassRef(rtCp, classInfo);
                } else if (cpInfo instanceof ConstantInfoType.ConstantFieldRefInfo) {
                    ConstantInfoType.ConstantFieldRefInfo fieldRefInfo = (ConstantInfoType.ConstantFieldRefInfo) cpInfo;
                    consts[i] = new FieldRef(rtCp, fieldRefInfo);
                } else if (cpInfo instanceof ConstantInfoType.ConstantMethodRefInfo) {
                    ConstantInfoType.ConstantMethodRefInfo methodRefInfo = (ConstantInfoType.ConstantMethodRefInfo) cpInfo;
                    consts[i] = new MethodRef(rtCp, methodRefInfo);
                } else if (cpInfo instanceof ConstantInfoType.ConstantInterfaceMethodRefInfo) {
                    ConstantInfoType.ConstantInterfaceMethodRefInfo interfaceMethodRefInfo = (ConstantInfoType.ConstantInterfaceMethodRefInfo) cpInfo;
                    consts[i] = new InterfaceMethodRef(rtCp, interfaceMethodRefInfo);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public java.lang.Object getConstant(int index) {
        return consts[index];
    }

    public Class getClazz() {
        return clazz;
    }
}
