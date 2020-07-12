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
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;

import static com.lgb._native.java.lang.ClassHelper.toClassArr;

public class Class_getDeclaredMethods0 {
    public static final String _methodConstructorDescriptor = "" +
            "(Ljava/lang/Class;" +
            "Ljava/lang/String;" +
            "[Ljava/lang/Class;" +
            "Ljava/lang/Class;" +
            "[Ljava/lang/Class;" +
            "II" +
            "Ljava/lang/String;" +
            "[B[B[B)V";

    // private native Method[] getDeclaredMethods0(boolean publicOnly);
    // (Z)[Ljava/lang/reflect/Method;
    public static NativeMethod getDeclaredMethods0 = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object classObj = vars.getThis();
        boolean publicOnly = vars.getBoolean(1);

        Class clazz = (Class)classObj.getExtra();
        Method[] methods = clazz.getMethods(publicOnly);
        int methodCount = methods.length;

        Classloader classLoader = clazz.getClassloader();
        Class methodClass = classLoader.loadClass("java/lang/reflect/Method");
        Object methodArr = methodClass.arrayClass().newArray(methodCount);

        OperandStack stack=frame.operandStack;
        stack.pushRef(methodArr);

        // create method objs
        if(methodCount > 0) {
            Thread thread = frame.thread;
            Object[] methodObjs = methodArr.refs();
            Method methodConstructor = methodClass.getConstructor(_methodConstructorDescriptor);
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                Object methodObj = methodClass.newObject();
                methodObj.setExtra(method);
                methodObjs[i] = methodObj;

                OperandStack ops = new OperandStack(12);
                ops.pushRef(methodObj);                                                // this
                ops.pushRef(classObj);                                                 // declaringClass
                ops.pushRef(StringPool.jString(classLoader, method.getName()));                 // name
                ops.pushRef(toClassArr(classLoader, method.getParameterTypes()));         // parameterTypes
                ops.pushRef(method.getReturnType().getJClass());                             // returnType
                ops.pushRef(toClassArr(classLoader, method.getExceptionTypes()));         // checkedExceptions
                ops.pushInt(method.getAccessFlags());                              // modifiers
                ops.pushInt(0);                                                 // todo: slot
                ops.pushRef(ClassHelper.getSignatureStr(classLoader, method.getSignature()));         // signature
                ops.pushRef(ClassHelper.toByteArr(classLoader, method.getAnnotationData()));          // annotations
                ops.pushRef(ClassHelper.toByteArr(classLoader, method.getParameterAnnotationData())); // parameterAnnotations
                ops.pushRef(ClassHelper.toByteArr(classLoader, method.getAnnotationDefaultData()));   // annotationDefault

                Frame shimFrame = new Frame(ops, ShimMethods.shimReturnMethod(), thread);
                thread.pushFrame(shimFrame);

                // init methodObj
                MethodInvokeLogic.invokeMethod(shimFrame, methodConstructor);
            }

        }
    };
}
