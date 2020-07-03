package com.lgb.rtda.heap.methodarea;


import com.lgb.classfile.ClassFile;
import com.lgb.classfile.fundamental.U2;
import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.constantPool.ClassRef;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.util.ClassNameHelper;
import com.sun.org.apache.bcel.internal.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
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

    public Class(int accessFlags, String name, Classloader classloader, boolean initStarted, Class superClass, Class[] interfaces) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.classloader = classloader;
        this.initStarted = initStarted;
        this.superClass = superClass;
        this.interfaces = interfaces;
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

    public Field getField(String name, String descriptor, boolean isStatic) {
        for (Class c = this; c != null; c = c.superClass) {
            for (Field field : c.fields) {
                if (field.isStatic() == isStatic &&
                        field.name.equals(name) &&
                        field.descriptor.equals(descriptor)) {
                    return field;
                }
            }
        }
        return null;
    }

    public boolean isAssignableFrom(Class other) {
        Class s = other;
        Class t = this;

        if (s == t) {
            return true;
        }

        if (!s.isArray()) {
            if (!s.isInterface()) {
                if (!t.isInterface()) {
                    return s.isSubClassOf(t);
                } else {
                    return s.isImplements(t);
                }
            } else {
                if (!t.isInterface()) {
                    return t.isJlObject();
                } else {
                    return t.isSubInterfaceOf(s);
                }
            }
        } else {
            if (!t.isArray()) {

                if (!t.isInterface()) {
                    return t.isJlObject();
                } else {
                    return t.isJlCloneable() || t.isJioSerializable();
                }
            } else {
                Class sc = s.componentClass();
                Class tc = t.componentClass();
                return sc == tc || tc.isAssignableFrom(sc);
            }
        }
    }

    private boolean isJioSerializable() {
        return this.name.endsWith("java/io/Serializable");
    }

    private boolean isJlCloneable() {
        return this.name.equals("java/lang/Cloneable");
    }

    private boolean isJlObject() {
        return this.name.equals("java/lang/Object");
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
            if (Objects.isNull(m)) {
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

    public Class arrayClass() {
        String arrayClassName = ClassNameHelper.getArrayClassName(this.name);
        return this.classloader.loadClass(arrayClassName);
    }

    public boolean isArray() {
        return this.name.getBytes()[0] == '[';
    }

    public Object newArray(int count) {
        if (!this.isArray()) {
            throw new RuntimeException("Not array class " + this.name);
        }
        switch (this.name) {
            case "[Z":
                return new Object(this, new byte[count]);
            case "[B":
                return new Object(this, new byte[count]);
            case "[C":
                return new Object(this, new char[count]);
            case "[S":
                return new Object(this, new short[count]);
            case "[I":
                return new Object(this, new int[count]);
            case "[J":
                return new Object(this, new long[count]);
            case "[F":
                return new Object(this, new float[count]);
            case "[D":
                return new Object(this, new double[count]);
            default:
                return new Object(this, new Object[count]);
        }
    }

    public Class componentClass() {
        String componentClassName = ClassNameHelper.getComponentClassName(this.name);
        return this.classloader.loadClass(componentClassName);
    }
}
