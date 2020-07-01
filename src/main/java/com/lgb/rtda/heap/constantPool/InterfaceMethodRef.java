package com.lgb.rtda.heap.constantPool;

import com.lgb.classfile.fundamental.ConstantInfoType;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;

import java.util.Objects;

import static com.lgb.rtda.heap.MethodLookup.lookupMethodInInterfaces;

public class InterfaceMethodRef extends MemberRef {

    private Method method;

    public InterfaceMethodRef(ConstantPool constantPool, ConstantInfoType.ConstantMemberRefInfo refInfo) {
        super(constantPool, refInfo);
    }

    public Method resolveInterfaceMethod() {
        if(Objects.isNull(this.method)) {
            resolveInterfaceMethodRef();
        }
        return method;
    }

    private void resolveInterfaceMethodRef() {
        Class currentClazz = this.constantPool.getClazz();
        Class clazz = resolvedClass();
        if(!clazz.isInterface()) {
            throw new IncompatibleClassChangeError();
        }
        Method method = lookupMethodInInterfaces(new Class[]{clazz}, this.name, this.descriptor);
        if(Objects.isNull(method)){
            throw new NoSuchMethodError();
        }
        if(!method.isAccessibleTo(currentClazz)){
            throw new IllegalAccessError();
        }
        this.method = method;
    }



}
