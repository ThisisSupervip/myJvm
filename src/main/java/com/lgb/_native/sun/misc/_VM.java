package com.lgb._native.sun.misc;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.instructions.base.MethodInvokeLogic;
import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;

public class _VM {

    private final String smvClass = "sun/misc/VM";

    public _VM() {
        Registry.register(smvClass, "initialize", "()V", initialize);
    }

    public NativeMethod initialize = (frame) -> {
        Classloader classLoader = frame.method.getClazz().getClassloader();
        Class jlSysClass = classLoader.loadClass("java/lang/System");
        Method initSysClass = jlSysClass.getStaticMethod("initializeSystemClass", "()V");
        MethodInvokeLogic.invokeMethod(frame, initSysClass);
    };

}
