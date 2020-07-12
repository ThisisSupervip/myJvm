package com.lgb._native;

import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.MethodDescriptor;
import com.lgb.rtda.heap.StringPool;
import com.lgb.rtda.heap.classloader.Classloader;
import com.sun.org.apache.bcel.internal.Constants;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class NativeMethodHelper {

    /**
     * 通过反射执行java本地方法
     *
     * @param frame
     * @param clazz
     */
    public static void execNative(Object object, Frame frame, Class clazz, Object... params) {

        String descriptor = frame.method.getDescriptor();
        String methodMame = frame.method.getName();
        MethodDescriptor methodDescriptor = MethodDescriptor.parseMethodDescriptor(descriptor);
        Class[] classes = methodDescriptor.javaOrgParameterTypes();
        Class returnType = methodDescriptor.getOrgReturnType();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodMame, classes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (ObjectUtils.isEmpty(params)) {
            params = frame.localVariables.getJavaParams(classes, frame.method.isStatic());
        }
        Object res = null;
        try {
            if(!method.isAccessible()){
                method.setAccessible(true);
            }
            res = method.invoke(object, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (Objects.nonNull(returnType)) {
            //todo
            returnResult(frame, returnType, res);
        }

    }

    private static void returnResult(Frame frame, Class resCls, Object res) {
        OperandStack ops = frame.operandStack;
        com.lgb.rtda.heap.methodarea.Method method = frame.method;
        if (resCls == int.class) {
            ops.pushInt((Integer) res);
            method.setCode(new byte[]{(byte) Constants.IRETURN});
        } else if (resCls == long.class) {
            ops.pushLong((Long) res);
            method.setCode(new byte[]{(byte) Constants.LRETURN});
        } else if (resCls == double.class) {
            ops.pushDouble((Double) res);
            method.setCode(new byte[]{(byte) Constants.DRETURN});
        } else if (resCls == byte.class) {
            ops.pushInt((byte) res);
            method.setCode(new byte[]{(byte) Constants.IRETURN});
        } else if (resCls == boolean.class) {
            ops.pushBoolean((Boolean) res);
            method.setCode(new byte[]{(byte) Constants.IRETURN});
        } else if (resCls == String.class) {
            Classloader classloader = frame.method.getClazz().getClassloader();
            com.lgb.rtda.heap.methodarea.Object jString = StringPool.jString(classloader, (String)res);
            ops.pushRef(jString);
            method.setCode(new byte[]{(byte) Constants.ARETURN});
        }
        frame.setNextPC(0);
        //frame.method.setCode();
    }
}
