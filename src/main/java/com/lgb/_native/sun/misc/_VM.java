package com.lgb._native.sun.misc;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.instructions.base.MethodInvokeLogic;
import com.lgb.rtda.heap.StringPool;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;

public class _VM {

    private final String smvClass = "sun/misc/VM";

    public _VM() {
        Registry.register(smvClass, "initialize", "()V", initialize);
    }

    public NativeMethod initialize = (frame) -> {
        Class vmClass = frame.method.getClazz();
        Object savedProps = vmClass.getRefVar("savedProps", "Ljava/util/Properties;");
        Object key = StringPool.jString(vmClass.getClassloader(), "foo");
        Object val = StringPool.jString(vmClass.getClassloader(), "bar");

        frame.operandStack.pushRef(savedProps);
        frame.operandStack.pushRef(key);
        frame.operandStack.pushRef(val);

        Class propsClass = vmClass.getClassloader().loadClass("java/util/Properties");
        Method setPropMethod = propsClass.getInstanceMethod("setProperty",
                "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");
        MethodInvokeLogic.invokeMethod(frame, setPropMethod);
    };

}
