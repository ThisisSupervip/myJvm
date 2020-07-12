package com.lgb._native.java.security;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.instructions.base.MethodInvokeLogic;
import com.lgb.rtda.LocalVariables;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;

public class _AccessController {

    public _AccessController() {
        Registry.register("java/security/AccessController", "doPrivileged", "(Ljava/security/PrivilegedAction;)Ljava/lang/Object;", doPrivileged);
        Registry.register("java/security/AccessController", "doPrivileged", "(Ljava/security/PrivilegedExceptionAction;)Ljava/lang/Object;", doPrivileged);
        Registry.register("java/security/AccessController", "getStackAccessControlContext", "()Ljava/security/AccessControlContext;", getStackAccessControlContext);
    }


    // @CallerSensitive
    // public static native <T> T
    //     doPrivileged(PrivilegedExceptionAction<T> action)
    //     throws PrivilegedActionException;
    // (Ljava/security/PrivilegedExceptionAction;)Ljava/lang/Object;

        // @CallerSensitive
    // public static native <T> T doPrivileged(PrivilegedAction<T> action);
    // (Ljava/security/PrivilegedAction;)Ljava/lang/Object;
    private NativeMethod doPrivileged = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object action = vars.getRef(0);

        OperandStack stack = frame.operandStack;
        stack.pushRef(action);

        Method method = action.clazz.getInstanceMethod("run", "()Ljava/lang/Object;"); // todo
        MethodInvokeLogic.invokeMethod(frame, method);
    };

    // private static native AccessControlContext getStackAccessControlContext();
    // ()Ljava/security/AccessControlContext;
    private NativeMethod getStackAccessControlContext = (frame) -> {
        // todo
        frame.operandStack.pushRef(null);
    };
}
