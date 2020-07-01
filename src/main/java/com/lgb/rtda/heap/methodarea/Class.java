package com.lgb.rtda.heap.methodarea;


import com.lgb.classfile.ClassFile;
import com.lgb.classfile.fundamental.U2;
import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.constantPool.ClassRef;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.sun.org.apache.bcel.internal.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Class {

    private int accessFlags;
    private String name;
    private String superClassName;
    private String[] interfaceNames;
    private ConstantPool constantPool;
    private Field[] fields;
    private Method[] methods;
    private Classloader classloader;
    private Class superClass;
    private Class[] interfaces;
    private int instanceSlotCount;
    private int staticSlotCount;
    private Slots staticVars;
    private boolean initStarted;


    public Class(ClassFile classFile) {
        this.accessFlags = classFile.getAccessFlags().intValue;
        this.constantPool = new ConstantPool(this, classFile);
        this.name = ((ClassRef) constantPool.getConstant(classFile.getThisClass().intValue)).getClassName();
        parseSuperClassName(classFile);
        parseInterfaceNames(classFile);
        this.fields = Field.newFields(this, classFile.getFields());
        this.methods = Method.newMethods(this, classFile.getMethods());
    }

    public String getPackageName() {
        int i = this.name.lastIndexOf("/");
        if (i >= 0) return this.name.substring(0, i);
        return "";
    }

    public boolean isSubClassOf(Class other) {
        for (Class c = this.superClass; c != null; c = c.superClass) {
            if (c == other) {
                return true;
            }
        }
        return false;
    }

    public boolean isSuperClassOf(Class other) {
        for (Class c = other; c != null; c = c.superClass) {
            if (c == this) {
                return true;
            }
        }
        return false;
    }


    public boolean isImplements(Class other) {

        for (Class c = this; c != null; c = c.superClass) {
            for (Class clazz : c.interfaces) {
                if (clazz == other || clazz.isSubInterfaceOf(other)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSubInterfaceOf(Class iface) {
        for (Class superInterface : this.interfaces) {
            if (superInterface == iface || superInterface.isSubInterfaceOf(iface)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAssignableFrom(Class other) {
        if (this == other) return true;
        if (!other.isInterface()) {
            return this.isSubClassOf(other);
        } else {
            return this.isImplements(other);
        }
    }

    public Object newObject() {
        return new Object(this);
    }

    public boolean isPublic() {
        return 0 != (this.accessFlags & Constants.ACC_PUBLIC);
    }

    public boolean isFinal() {
        return 0 != (this.accessFlags & Constants.ACC_FINAL);
    }

    public boolean isSuper() {
        return 0 != (this.accessFlags & Constants.ACC_SUPER);
    }

    public boolean isInterface() {
        return 0 != (this.accessFlags & Constants.ACC_INTERFACE);
    }

    public boolean isAbstract() {
        return 0 != (this.accessFlags & Constants.ACC_ABSTRACT);
    }

    public Method getStaticMethod(String name, String descriptor) {
        for (Method m : methods) {
            if(Objects.isNull(m)) {
                continue;
            }
            if (m.isStatic() && m.getName().equals(name) && m.getDescriptor().equals(descriptor)) {
                return m;
            }
        }
        return null;
    }

    private void parseSuperClassName(ClassFile classFile) {
        int index = classFile.getSuperClass().intValue;
        if (index <= 0) {
            this.superClassName = "";
        } else {
            this.superClassName = ((ClassRef) constantPool.getConstant(index)).getClassName();
        }
    }

    public void parseInterfaceNames(ClassFile classFile) {
        U2[] interfaces = classFile.getInterfaces();
        String[] interfaceNames = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            interfaceNames[i] = ((ClassRef) this.constantPool.getConstant(interfaces[i].intValue)).getClassName();
        }
        this.interfaceNames = interfaceNames;
    }

    public boolean isInitStarted() {
        return initStarted;
    }

    public Method getClinitMethod() {
        return getStaticMethod("<clinit>", "()V");
    }

    public void startInit() {
        this.initStarted = true;
    }

    @Override
    public String toString() {
        return name;
    }
}
