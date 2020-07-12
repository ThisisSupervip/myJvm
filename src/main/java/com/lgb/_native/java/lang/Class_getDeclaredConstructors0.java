package com.lgb._native.java.lang;

import com.lgb._native.NativeMethod;
import com.lgb.instructions.base.MethodInvokeLogic;
import com.lgb.rtda.Frame;
import com.lgb.rtda.LocalVariables;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.Thread;
import com.lgb.rtda.heap.ShimMethods;
import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;

import static com.lgb._native.java.lang.ClassHelper.toClassArr;

public class Class_getDeclaredConstructors0 {
    public static final String _constructorConstructorDescriptor = "" +
            "(Ljava/lang/Class;" +
            "[Ljava/lang/Class;" +
            "[Ljava/lang/Class;" +
            "II" +
            "Ljava/lang/String;" +
            "[B[B)V";
    // private native Constructor<T>[] getDeclaredConstructors0(boolean publicOnly);
    // (Z)[Ljava/lang/reflect/Constructor;
    public static NativeMethod getDeclaredConstructors0 = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object classObj = vars.getThis();
        boolean publicOnly = vars.getBoolean(1);

        Class clazz = (Class)classObj.getExtra();
        Method[] constructors = clazz.getConstructors(publicOnly);
        int constructorCount = constructors.length;

        Classloader classLoader = frame.method.getClazz().getClassloader();
        Class constructorClass = classLoader.loadClass("java/lang/reflect/Constructor");
        Object constructorArr = constructorClass.arrayClass().newArray(constructorCount);

        OperandStack stack=frame.operandStack;
        stack.pushRef(constructorArr);

        if (constructorCount > 0) {
            Thread thread = frame.thread;
            Object[] constructorObjs = constructorArr.refs();
            Method constructorInitMethod = constructorClass.getConstructor(_constructorConstructorDescriptor);
            for (int i = 0; i < constructors.length; i++) {
                Method constructor = constructors[i];
                Object constructorObj = constructorClass.newObject();
                constructorObj.setExtra(constructor);
                constructorObjs[i] = constructorObj;

                OperandStack ops = new OperandStack(9);
                ops.pushRef(constructorObj);                                                // this
                ops.pushRef(classObj);                                                      // declaringClass
                ops.pushRef(toClassArr(classLoader, constructor.getParameterTypes()));         // parameterTypes
                ops.pushRef(toClassArr(classLoader, constructor.getExceptionTypes()));         // checkedExceptions
                ops.pushInt(constructor.getAccessFlags());                              // modifiers
                ops.pushInt(0);                                                      // todo slot
                ops.pushRef(ClassHelper.getSignatureStr(classLoader, constructor.getSignature()));         // signature
                ops.pushRef(ClassHelper.toByteArr(classLoader, constructor.getAnnotationData()));          // annotations
                ops.pushRef(ClassHelper.toByteArr(classLoader, constructor.getParameterAnnotationData())); // parameterAnnotations

                Frame shimFrame = new Frame(ops, ShimMethods.shimReturnMethod(), thread);
                thread.pushFrame(shimFrame);

                // init constructorObj
                MethodInvokeLogic.invokeMethod(shimFrame, constructorInitMethod);
            }
        }
    };

}
