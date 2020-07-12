package com.lgb._native.java.lang;

import com.lgb._native.NativeMethod;
import com.lgb.instructions.base.MethodInvokeLogic;
import com.lgb.rtda.Frame;
import com.lgb.rtda.LocalVariables;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.Thread;
import com.lgb.rtda.heap.ShimMethods;
import com.lgb.rtda.heap.StringPool;
import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Field;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;

public class Class_getDeclaredFields0 {

    private static final String _fieldConstructorDescriptor = "" +
            "(Ljava/lang/Class;" +
            "Ljava/lang/String;" +
            "Ljava/lang/Class;" +
            "II" +
            "Ljava/lang/String;" +
            "[B)V";

    // private native Field[] getDeclaredFields0(boolean publicOnly);
    // (Z)[Ljava/lang/reflect/Field;
    public static NativeMethod getDeclaredFields0 = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object classObj = vars.getThis();
        boolean publicOnly = vars.getBoolean(1);

        Class clazz = (Class)classObj.getExtra();
        Field[] fields = clazz.getFields(publicOnly);
        int fieldCount = fields.length;

        Classloader classLoader = frame.method.getClazz().getClassloader();
        Class fieldClass=classLoader.loadClass("java/lang/reflect/Field");
        Object fieldArr=fieldClass.arrayClass().newArray(fieldCount);

        OperandStack stack=frame.operandStack;
        stack.pushRef(fieldArr);

        if (fieldCount > 0) {
            Thread thread=frame.thread;
            Object[] fieldObjs=fieldArr.refs();
            Method fieldConstructor=fieldClass.getConstructor(_fieldConstructorDescriptor);
            for (int i = 0; i < fields.length; i++) {
                Field goField = fields[i];
                Object fieldObj=fieldClass.newObject();
                fieldObj.setExtra(goField);
                fieldObjs[i] = fieldObj;

                OperandStack ops= new OperandStack(8);
                ops.pushRef(fieldObj);                                          // this
                ops.pushRef(classObj);                                          // declaringClass
                ops.pushRef(StringPool.jString(classLoader, goField.getName()));     // name
                ops.pushRef(goField.getType().getJClass());                           // type
                ops.pushInt(goField.getAccessFlags());                      // modifiers
                ops.pushInt(goField.getSlotId());                           // slot
                ops.pushRef(ClassHelper.getSignatureStr(classLoader, goField.getSignature())); // signature
                ops.pushRef(ClassHelper.toByteArr(classLoader, goField.getAnnotationData()));  // annotations

                Frame shimFrame = new Frame(ops, ShimMethods.shimReturnMethod(), thread);
                thread.pushFrame(shimFrame);

                // init fieldObj
                MethodInvokeLogic.invokeMethod(shimFrame, fieldConstructor);
            }
        }
    };
}
