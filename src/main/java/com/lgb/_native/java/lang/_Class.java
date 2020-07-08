package com.lgb._native.java.lang;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.rtda.heap.StringPool;
import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Object;

public class _Class {

    private final String jlClass = "java/lang/Class";

    public _Class() {
        Registry.register(jlClass, "getPrimitiveClass", "(Ljava/lang/String;)Ljava/lang/Class;", getPrimitiveClass);
        Registry.register(jlClass, "getName0", "()Ljava/lang/String;", getName0);
        Registry.register(jlClass, "desiredAssertionStatus0", "(Ljava/lang/Class;)Z", desiredAssertionStatus0);
        Registry.register(jlClass, "registerNatives", "()V", registerNatives);
    }

    private NativeMethod registerNatives = (frame)->{

    };

    private NativeMethod getPrimitiveClass = (frame)->{
        Object nameObj = frame.localVariables.getRef(0);
        String name = StringPool.goString(nameObj);

        Classloader loader = frame.method.getClazz().getClassloader();
        Object jClass = loader.loadClass(name).getJClass();

        frame.operandStack.pushRef(jClass);
    };

    private NativeMethod getName0 = (frame)->{
        Object thiz = frame.localVariables.getThis();
        Class clazz = (Class) thiz.getExtra();

        String name = clazz.javaName();
        Object nameObj = StringPool.jString(clazz.getClassloader(), name);

        frame.operandStack.pushRef(nameObj);
    };

    private NativeMethod desiredAssertionStatus0 = (frame)->{
        frame.operandStack.pushBoolean(false);
    };

}
