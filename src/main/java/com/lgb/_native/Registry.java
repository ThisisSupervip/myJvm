package com.lgb._native;


import com.lgb._native.java.io._FileOutputStream;
import com.lgb._native.java.util.concurrent.atomic._AtomicLong;
import com.lgb._native.java.io._FileDescriptor;
import com.lgb._native.java.lang.*;
import com.lgb._native.java.security._AccessController;
import com.lgb._native.sun.misc._Unsafe;
import com.lgb._native.sun.misc._Unsafe_mem;
import com.lgb._native.sun.misc._VM;
import com.lgb._native.sun.reflect._NativeConstructorAccessorImpl;
import com.lgb._native.sun.reflect._Reflection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Registry {
    private static Map<String, NativeMethod> registry = new HashMap<>();

    static {
        initNativeMethod();
    }

    public static void initNativeMethod() {
        new _Object();
        new _Class();
        new _String();
        new _System();
        new _Double();
        new _Float();
        new _VM();
        new _Throwable();
        new _Unsafe();
        new _Reflection();
        new _FileDescriptor();
        new _AccessController();
        new _Thread();
        new _NativeConstructorAccessorImpl();
        new _Unsafe_mem();
        new _AtomicLong();
        new _ClassLoader();
        new _FileOutputStream();
        new _Runtime();
    }

    public static void register(String className, String methodName, String methodDescriptor, NativeMethod method) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        registry.put(key, method);
    }

    public static void register(Object object, String className, String methodName, String methodDescriptor) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        Class clazz = null;
        try {
            clazz = Class.forName(className.replace("/", "."));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Class clazz1 = clazz;
        registry.put(key, (frame)-> NativeMethodHelper.execNative(object, frame, clazz1));
    }

    public static NativeMethod findNativeMethod(String className, String methodName, String methodDescriptor) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        NativeMethod method = null;
        if (Objects.nonNull(method = registry.get(key))) {
            return method;
        }
        if ("()V".equals(methodDescriptor)){
            if("registerNatives".equals(methodName) || "initIDs".equals(methodName)) {
                return (frame) -> {
                    // do nothing
                };
            }
        }
        return null;
    }

}
