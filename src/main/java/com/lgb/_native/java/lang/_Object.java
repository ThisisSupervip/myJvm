package com.lgb._native.java.lang;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Object;

public class _Object {
    private final String jlObject = "java/lang/Object";

    public _Object() {
        Registry.register(jlObject, "getClass", "()Ljava/lang/Class;", getClass);
        Registry.register(jlObject, "hashCode", "()I", _hashCode);
        Registry.register(jlObject, "clone", "()Ljava/lang/Object;", _clone);
        Registry.register(jlObject, "registerNatives", "()V", registerNatives);
    }

    private NativeMethod getClass = (frame) -> {
        Object thiz = frame.localVariables.getThis();
        Object clazz = thiz.clazz.getJClass();
        frame.operandStack.pushRef(clazz);
    };

    public NativeMethod registerNatives = (frame) -> {
        // do nothing
    };

    public NativeMethod _hashCode = (frame) -> {
        Object thiz = frame.localVariables.getThis();
        frame.operandStack.pushInt(thiz.hashCode());
    };

    public NativeMethod _clone = (frame) -> {
        Object thiz = frame.localVariables.getThis();

        Class cloneable = thiz.clazz.getClassloader().loadClass("java/lang/Cloneable");

        if (!thiz.clazz.isImplements(cloneable)) {
            throw new RuntimeException("CloneNotSupportedException");
        }

        frame.operandStack.pushRef(thiz._clone());
    };


}
