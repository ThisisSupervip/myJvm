package com.lgb._native.sun.reflect;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.instructions.base.ClassInitLogic;
import com.lgb.instructions.base.MethodInvokeLogic;
import com.lgb.rtda.Frame;
import com.lgb.rtda.LocalVariables;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.ShimMethods;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;

public class _NativeConstructorAccessorImpl {


    public _NativeConstructorAccessorImpl() {
        Registry.register("sun/reflect/NativeConstructorAccessorImpl", "newInstance0", "(Ljava/lang/reflect/Constructor;[Ljava/lang/Object;)Ljava/lang/Object;", newInstance0);
    }

    // private static native Object newInstance0(Constructor<?> c, Object[] os)
    // throws InstantiationException, IllegalArgumentException, InvocationTargetException;
    // (Ljava/lang/reflect/Constructor;[Ljava/lang/Object;)Ljava/lang/Object;
    private NativeMethod newInstance0 = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object constructorObj = vars.getRef(0);
        Object argArrObj = vars.getRef(1);

        Method goConstructor = getGoConstructor(constructorObj);
        Class goClass = goConstructor.getClazz();
        if (!goClass.isInitStarted()) {
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.thread, goClass);
            return;
        }

        Object obj = goClass.newObject();
        OperandStack stack = frame.operandStack;
        stack.pushRef(obj);

        // call <init>
        OperandStack ops = convertArgs(obj, argArrObj, goConstructor);
        Frame shimFrame = new Frame(ops, ShimMethods.shimReturnMethod(), frame.thread);
        frame.thread.pushFrame(shimFrame);

        MethodInvokeLogic.invokeMethod(shimFrame, goConstructor);
    };

    private Method getGoMethod(Object methodObj) {
        return _getGoMethod(methodObj, false);
    }
    private Method getGoConstructor(Object constructorObj) {
        return _getGoMethod(constructorObj, true);
    }
    private Method _getGoMethod(Object methodObj, boolean isConstructor) {
        java.lang.Object extra = methodObj.getExtra();
        if (extra != null) {
            return (Method) extra;
        }

        if(isConstructor) {
            Object root = methodObj.getRefVar("root", "Ljava/lang/reflect/Constructor;");
            return (Method) root.getExtra();
        } else {
            Object root = methodObj.getRefVar("root", "Ljava/lang/reflect/Method;");
            return (Method) root.getExtra();
        }
    }

    private OperandStack convertArgs(Object thiz, Object argArr, Method method) {
        if (method.argSlotCount == 0) {
            return new OperandStack(0);
        }

        OperandStack ops = new OperandStack(method.argSlotCount);
        if (!method.isStatic()) {
            ops.pushRef(thiz);
        }
        if (method.argSlotCount == 1 && !method.isStatic()) {
            return ops;
        }

        return ops;
    }
}
