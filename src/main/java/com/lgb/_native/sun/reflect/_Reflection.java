package com.lgb._native.sun.reflect;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.rtda.Frame;
import com.lgb.rtda.LocalVariables;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Object;

public class _Reflection {

    public _Reflection() {
        Registry.register("sun/reflect/Reflection", "getCallerClass", "()Ljava/lang/Class;", getCallerClass);
        Registry.register("sun/reflect/Reflection", "getClassAccessFlags", "(Ljava/lang/Class;)I", getClassAccessFlags);
    }

    // public static native Class<?> getCallerClass();
    // ()Ljava/lang/Class;
    private NativeMethod getCallerClass = (frame) -> {
        // top0 is sun/reflect/Reflection
        // top1 is the caller of getCallerClass()
        // top2 is the caller of method
        Frame callerFrame = frame.thread.getFrames()[2]; // todo
        Object callerClass = callerFrame.method.getClazz().getJClass();
        frame.operandStack.pushRef(callerClass);
    };

    // public static native int getClassAccessFlags(Class<?> type);
    // (Ljava/lang/Class;)I
    private NativeMethod getClassAccessFlags = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object _type = vars.getRef(0);

        Class goClass = (Class)_type.getExtra();
        int flags = goClass.getAccessFlags();

        OperandStack stack = frame.operandStack;
        stack.pushInt(flags);
    };
}
