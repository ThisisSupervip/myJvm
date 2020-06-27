package com.lgb.rtda.heap.classloader;

import com.lgb.classfile.ClassFile;
import com.lgb.classpath.Classpath;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Field;
import com.lgb.rtda.heap.methodarea.Slots;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Classloader {
    private Classpath classpath;
    private Map<String, Class> map; //loaded classes

    public Classloader(Classpath classpath) {
        this.classpath = classpath;
        this.map = new HashMap<>();
    }

    public Class loadClass(String name) {
        Class res = null;
        if(Objects.nonNull(res = map.get(name))) {
            return res;
        }
        res = loadNonArrayClass(name);
        return res;
    }

    private Class loadNonArrayClass(String name) {
        //加载
        byte[] bytes = readClass(name);
        Class clazz = defineClass(bytes);
        //连接
        link(clazz);
        System.out.printf("[Loaded class: %s]", name);
        return clazz;
    }

    private byte[] readClass(String name) {
        byte[] bytes = classpath.readClass(name);
        if(Objects.isNull(bytes)){
            throw new RuntimeException("java.lang.ClassNotFoundException: " + name);
        }
        return bytes;
    }

    private Class defineClass(byte[] data) {
        Class clazz = parseClass(data);
        clazz.setClassloader(this);
        resolveSuperClass(clazz);
        resolveInterface(clazz);
        this.map.put(clazz.getName(), clazz);
        return clazz;
    }

    private Class parseClass(byte[] data) {
        ClassFile classFile = new ClassFile(data);
        return new Class(classFile);
    }

    private void resolveSuperClass(Class clazz) {
        if(Objects.equals(clazz.getName(), "java/lang/Object")) {
            clazz.setSuperClass(clazz.getClassloader().loadClass(clazz.getSuperClassName()));
        }
    }

    private void resolveInterface(Class clazz) {
        String[] interfaceNames = clazz.getInterfaceNames();
        if(interfaceNames.length>0) {
            Class[] interfaces = new Class[interfaceNames.length];
            for (int i = 0; i < interfaceNames.length; i++) {
                interfaces[i] = clazz.getClassloader().loadClass(interfaceNames[i]);
            }
            clazz.setInterfaces(interfaces);
        }
    }

    private void link(Class clazz) {
        verify(clazz);
        //为类变量分配内存 设置类变量初始值
        prepare(clazz);
    }

    private void verify(Class clazz) {
        //todo
    }

    private void prepare(Class clazz) {
        calcInstanceFieldSlotIds(clazz);
        calcStaticFieldSlotIds(clazz);
        allocAndInitStaticVars(clazz);
    }

    private void calcInstanceFieldSlotIds(Class clazz) {
        int slotId = 0;
        if (clazz.getSuperClass() != null) {
            slotId = clazz.getSuperClass().getInstanceSlotCount();
        }
        for (Field field : clazz.getFields()) {
            if (!field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.setInstanceSlotCount(slotId);
    }

    private void calcStaticFieldSlotIds(Class clazz) {
        int slotId = 0;
        for (Field field : clazz.getFields()) {
            if (field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.setStaticSlotCount(slotId);
    }

    private void allocAndInitStaticVars(Class clazz) {
        clazz.setStaticVars(new Slots(clazz.getStaticSlotCount()));
        for (Field field: clazz.getFields()){
            if(field.isStatic() && field.isFinal()){
                initStaticFinalVar(clazz, field);
            }
        }
    }

    private void initStaticFinalVar(Class clazz, Field field) {
        Slots staticVars = clazz.getStaticVars();
        ConstantPool constantPool = clazz.getConstantPool();
        int cpIndex = field.getConstValueIndex();
        String descriptor = field.getDescriptor().substring(0, 1);
        int slotId = field.getSlotId();
        if(cpIndex>0){
            switch (descriptor){
                case "Z":
                case "B":
                case "C":
                case "S":
                case "I":
                    staticVars.setInt(slotId, (Integer) constantPool.getConstant(cpIndex));
                    break;
                case "J":
                    staticVars.setLong(slotId, (Long) constantPool.getConstant(cpIndex));
                    break;
                case "F":
                    staticVars.setFloat(slotId, (Float) constantPool.getConstant(cpIndex));
                    break;
                case "D":
                    staticVars.setDouble(slotId, (Double) constantPool.getConstant(cpIndex));
                    break;
                case "L":
                    //staticVars.setRef(slotId, );
                /*case "Ljava/lang/String;":
                    //todo
                    break;*/
            }
        }
    }
}
