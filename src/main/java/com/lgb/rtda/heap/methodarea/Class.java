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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    @Getter
    private Classloader classloader;
    private Class superClass;
    private Class[] interfaces;
    private int instanceSlotCount;
    private int staticSlotCount;
    private Slots staticVars;
    private boolean initStarted;
    //指向类对象
    @Getter
    @Setter
    private Object jClass;
    @Getter
    private String sourceFile;


    public Class() {
    }

    public Class(ClassFile classFile) {
        this.accessFlags = classFile.getAccessFlags().intValue;
        this.constantPool = new ConstantPool(this, classFile);
        this.name = ((ClassRef) constantPool.getConstant(classFile.getThisClass().intValue)).getClassName();
        parseSuperClassName(classFile);
        parseInterfaceNames(classFile);
        this.fields = Field.newFields(this, classFile.getFields());
        this.methods = Method.newMethods(this, classFile.getMethods());
        this.sourceFile = classFile.getSourceFile();
    }

    public Class(int accessFlags, String name, Classloader classloader, boolean initStarted, Class superClass, Class[] interfaces) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.classloader = classloader;
        this.initStarted = initStarted;
        this.superClass = superClass;
        this.interfaces = interfaces;
    }

    public Class(int accessFlags, String name, Classloader classloader, boolean initStarted) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.classloader = classloader;
        this.initStarted = initStarted;
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
            if ((superInterface == iface || superInterface.isSubInterfaceOf(iface))) {
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

    private Method getStaticMethod(String name, String descriptor, boolean isStatic) {
        for (Class c = this; c != null; c = c.superClass) {
            if (null == c.methods) continue;
            for (Method method : c.methods) {
                if (method.isStatic() == isStatic && method.name.equals(name) && method.descriptor.equals(descriptor)) {
                    return method;
                }
            }
        }
        throw new RuntimeException("method not find: " + name + " " + descriptor);
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

    public boolean isString() {
        return this.name.equals("java/lang/String");
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

    public String javaName() {
        return this.name.substring(0, 1) + this.name.substring(1).replace("/", ".");
    }

    public boolean isPrimitive() {
        return null != ClassNameHelper.primitiveTypes.get(this.name);
    }

    public Object getRefVar(String fieldName, String fieldDescriptor) {
        Field field = this.getField(fieldName, fieldDescriptor, true);
        return this.staticVars.getRef(field.getSlotId());
    }

    public Method getInstanceMethod(String name, String descriptor) {
        return this.getStaticMethod(name, descriptor, false);
    }

    public Class getSuperClazz() {
        return superClass;
    }

    public Field[] getFields(boolean publicOnly) {
        if (publicOnly) {
            List<Field> publicFields = new ArrayList<>();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if(field.isPublic()) {
                    publicFields.add(field);
                }
            }
            Field[] res = new Field[publicFields.size()];
            return publicFields.toArray(res);
        } else {
            return fields;
        }
    }



    public Method getConstructor(String descriptor) {
        return getInstanceMethod("<init>", descriptor);
    }

    public Method[] getConstructors(boolean publicOnly) {
        List<Method> constructors = new ArrayList<>();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if(method.isConstructor()) {
                if(!publicOnly || method.isPublic()) {
                    constructors.add(methods[i]);
                }
            }
        }
        Method[] res = new Method[constructors.size()];
        return constructors.toArray(res);
    }

    public Method[] getMethods(boolean publicOnly) {
        List<Method> methods = new ArrayList<>();
        for (int i = 0; i < this.methods.length; i++) {
            Method method = this.methods[i];
            if(!method.isClinit() && !method.isConstructor()) {
                if(!publicOnly || method.isPublic()) {
                    methods.add(this.methods[i]);
                }
            }
        }
        Method[] res = new Method[methods.size()];
        return methods.toArray(res);
    }

    public void setRefVar(String fieldName, String fieldDescriptor, Object ref) {
        Field field = this.getField(fieldName, fieldDescriptor, true);
        this.staticVars.setRef(field.getSlotId(), ref);
    }
}
