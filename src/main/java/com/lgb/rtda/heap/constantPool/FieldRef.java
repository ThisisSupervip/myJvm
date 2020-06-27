package com.lgb.rtda.heap.constantPool;

import com.lgb.classfile.fundamental.ConstantInfoType;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Field;

import java.util.Objects;

public class FieldRef extends MemberRef{

    private Field field;

    public FieldRef(ConstantPool constantPool, ConstantInfoType.ConstantMemberRefInfo refInfo) {
        super(constantPool, refInfo);
    }

    public Field resolvedField() {
        if(Objects.isNull(this.field)) {
            try {
                resolveFieldRef();
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("NoSuchFieldException");
            }
        }
        return this.field;
    }

    private void resolveFieldRef() throws NoSuchFieldException {
        Class d = constantPool.getClazz();
        Class c = this.resolvedClass();

        Field field = this.lookupField(c, this.name, this.descriptor);
        if (null == field) {
            throw new NoSuchFieldException();
        }

        if (!field.isAccessibleTo(d)) {
            throw new IllegalAccessError();
        }

        this.field = field;
    }

    private Field lookupField(Class c, String name, String descriptor) {
        for (Field field : c.getFields()) {
            if (field.getName().equals(name) && field.getDescriptor().equals(descriptor)) {
                return field;
            }
        }

        if (null != c.getInterfaces()) {
            for (Class iface : c.getInterfaces()) {
                Field field = lookupField(iface, name, descriptor);
                if (null != field) return field;
            }
        }

        if (c.getSuperClass()!= null) {
            return lookupField(c.getSuperClass(), name, descriptor);
        }

        return null;
    }
}
