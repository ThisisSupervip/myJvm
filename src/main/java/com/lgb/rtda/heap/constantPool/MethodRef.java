package com.lgb.rtda.heap.constantPool;

import com.lgb.classfile.fundamental.ConstantInfoType;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;

import java.util.Objects;

import static com.lgb.rtda.heap.MethodLookup.lookupMethodInClass;
import static com.lgb.rtda.heap.MethodLookup.lookupMethodInInterfaces;

public class MethodRef extends MemberRef {
    private Method method;

    public MethodRef(ConstantPool constantPool, ConstantInfoType.ConstantMemberRefInfo refInfo) {
        super(constantPool, refInfo);
    }

    public Method resolveMethod() {
        if(Objects.isNull(this.method)){
           resolveMethodRef();
        }
        return this.method;
    }

    private void resolveMethodRef(){
        Class currentClazz = this.constantPool.getClazz();
        Class clazz = resolvedClass();
        if(clazz.isInterface()) {
            throw new IncompatibleClassChangeError();
        }
        Method method = lookupMethod(clazz, this.name, this.descriptor);
        if(Objects.isNull(method)){
            throw new NoSuchMethodError();
        }
        if(!method.isAccessibleTo(currentClazz)){
            throw new IllegalAccessError();
        }
        this.method = method;
    }

    private Method lookupMethod(Class clazz, String name, String descriptor) {
        Method method = lookupMethodInClass(clazz, name, descriptor);
        if(Objects.isNull(method)){
           method = lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
        }
        return method;
    }

}
