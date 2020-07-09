package com.lgb._native;


import com.lgb._native.java.lang.*;
import com.lgb._native.sun.misc._VM;

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
    }

    public static void register(String className, String methodName, String methodDescriptor, NativeMethod method) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        registry.put(key, method);
    }

    public static NativeMethod findNativeMethod(String className, String methodName, String methodDescriptor) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        NativeMethod method = null;
        if (Objects.nonNull(method = registry.get(key))) {
            return method;
        }
        if ("()V".equals(methodDescriptor) && "registerNatives".equals(methodName)) {
            return (frame) -> {
                // do nothing
            };
        }
        return null;
    }

}
