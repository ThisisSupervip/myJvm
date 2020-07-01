package com.lgb.rtda.heap;

import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;

import java.util.Objects;

public class MethodLookup {
    public static Method lookupMethodInClass(Class clazz, String name, String descriptor) {
        Class c;
        for(c = clazz; c != null; c = c.getSuperClass()) {
            for (Method method : c.getMethods()) {
                if(Objects.isNull(method)) continue;
                if(method.getName().equals(name) && method.getDescriptor().equals(descriptor)){
                    return method;
                }
            }
        }
        return null;
    }

    public static Method lookupMethodInInterfaces(Class[] ifaces, String name, String descriptor) {
        for (int i = 0; i < ifaces.length; i++) {
            Class c = ifaces[i];
            for (Method method : c.getMethods()) {
                if(method.getName().equals(name) && method.getDescriptor().equals(descriptor)){
                    return method;
                }
            }
            return lookupMethodInInterfaces(c.getInterfaces(), name, descriptor);
        }
        return null;
    }


}
