package com.lgb._native.java.lang;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Object;

public class _Thread {

    public _Thread() {
        Registry.register("java/lang/Thread", "currentThread", "()Ljava/lang/Thread;", currentThread);
        Registry.register("java/lang/Thread", "setPriority0", "(I)V", setPriority0);
        Registry.register("java/lang/Thread", "isAlive", "()Z", isAlive);
        Registry.register("java/lang/Thread", "start0", "()V", start0);
    }

    // public static native Thread currentThread();
    // ()Ljava/lang/Thread;
    public NativeMethod currentThread = (frame) -> {
        //jThread := frame.Thread().JThread()
        Classloader classLoader = frame.method.getClazz().getClassloader();
        Class threadClass = classLoader.loadClass("java/lang/Thread");
        Object jThread = threadClass.newObject();

        Class threadGroupClass = classLoader.loadClass("java/lang/ThreadGroup");
        Object jGroup = threadGroupClass.newObject();

        jThread.setRefVar("group", "Ljava/lang/ThreadGroup;", jGroup);
        jThread.setIntVar("priority", "I", 1);

        frame.operandStack.pushRef(jThread);
    };

    // private native void setPriority0(int newPriority);
    // (I)V
    public NativeMethod setPriority0 = (frame) -> {
        // vars := frame.LocalVars()
        // this := vars.GetThis()
        // newPriority := vars.GetInt(1))
        // todo
    };

    // public final native boolean isAlive();
    // ()Z
    public NativeMethod isAlive = (frame) -> {
        //vars := frame.LocalVars()
        //this := vars.GetThis()

        OperandStack stack = frame.operandStack;
        stack.pushBoolean(false);
    };

    // private native void start0();
    // ()V
    public NativeMethod start0 = (frame) -> {
        // todo
    };
}
