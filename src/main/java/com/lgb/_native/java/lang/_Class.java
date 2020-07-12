package com.lgb._native.java.lang;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.instructions.base.ClassInitLogic;
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

import java.util.Objects;

public class _Class {

    private static final String jlClass = "java/lang/Class";


    public _Class() {
        Registry.register(jlClass, "getPrimitiveClass", "(Ljava/lang/String;)Ljava/lang/Class;", getPrimitiveClass);
        Registry.register(jlClass, "getName0", "()Ljava/lang/String;", getName0);
        Registry.register(jlClass, "desiredAssertionStatus0", "(Ljava/lang/Class;)Z", desiredAssertionStatus0);
        Registry.register(jlClass, "registerNatives", "()V", registerNatives);
        Registry.register(jlClass, "isInterface", "()Z", isInterface);
        Registry.register(jlClass, "isPrimitive", "()Z", isPrimitive);
        Registry.register(jlClass, "getDeclaredFields0", "(Z)[Ljava/lang/reflect/Field;", Class_getDeclaredFields0.getDeclaredFields0);
        Registry.register(jlClass, "forName0", "(Ljava/lang/String;ZLjava/lang/ClassLoader;Ljava/lang/Class;)Ljava/lang/Class;", forName0);
        Registry.register(jlClass, "getDeclaredConstructors0", "(Z)[Ljava/lang/reflect/Constructor;", Class_getDeclaredConstructors0.getDeclaredConstructors0);
        Registry.register(jlClass, "getModifiers", "()I", getModifiers);
        Registry.register(jlClass, "getSuperclass", "()Ljava/lang/Class;", getSuperclass);
        Registry.register(jlClass, "getInterfaces0", "()[Ljava/lang/Class;", getInterfaces0);
        Registry.register(jlClass, "isArray", "()Z", isArray);
        Registry.register(jlClass, "getDeclaredMethods0", "(Z)[Ljava/lang/reflect/Method;", Class_getDeclaredMethods0.getDeclaredMethods0);
        Registry.register(jlClass, "getComponentType", "()Ljava/lang/Class;", getComponentType);
        Registry.register(jlClass, "isAssignableFrom", "(Ljava/lang/Class;)Z", isAssignableFrom);
    }

    private NativeMethod registerNatives = (frame) -> {

    };

    private NativeMethod getPrimitiveClass = (frame) -> {
        Object nameObj = frame.localVariables.getRef(0);
        String name = StringPool.goString(nameObj);

        Classloader loader = frame.method.getClazz().getClassloader();
        Object jClass = loader.loadClass(name).getJClass();

        frame.operandStack.pushRef(jClass);
    };

    private NativeMethod getName0 = (frame) -> {
        Object thiz = frame.localVariables.getThis();
        Class clazz = (Class) thiz.getExtra();

        String name = clazz.javaName();
        Object nameObj = StringPool.jString(clazz.getClassloader(), name);

        frame.operandStack.pushRef(nameObj);
    };

    private NativeMethod desiredAssertionStatus0 = (frame) -> {
        frame.operandStack.pushBoolean(false);
    };

    // public native boolean isInterface();
    // ()Z
    private NativeMethod isInterface = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object thiz = vars.getThis();
        Class clazz = (Class) thiz.getExtra();

        OperandStack stack = frame.operandStack;
        stack.pushBoolean(clazz.isInterface());
    };

    // public native boolean isPrimitive();
    // ()Z
    private NativeMethod isPrimitive = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object thiz = vars.getThis();
        Class clazz = (Class) thiz.getExtra();

        OperandStack stack = frame.operandStack;
        stack.pushBoolean(clazz.isPrimitive());
    };

    // private static native Class<?> forName0(String name, boolean initialize,
    //                                         ClassLoader loader,
    //                                         Class<?> caller)
    //     throws ClassNotFoundException;
    // (Ljava/lang/String;ZLjava/lang/ClassLoader;Ljava/lang/Class;)Ljava/lang/Class;
    private NativeMethod forName0 = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object jName = vars.getRef(0);
        boolean initialize = vars.getBoolean(1);
        //jLoader := vars.GetRef(2)

        String goName = StringPool.goString(jName);
        goName = goName.replace(".", "/");
        Class goClass = frame.method.getClazz().getClassloader().loadClass(goName);
        Object jClass = goClass.getJClass();

        if (initialize && !goClass.isInitStarted()) {
            // undo forName0
            Thread thread = frame.thread;
            frame.setNextPC(thread.pc());
            // init clazz
            ClassInitLogic.initClass(thread, goClass);
        } else {
            OperandStack stack = frame.operandStack;
            stack.pushRef(jClass);
        }
    };

    // public native int getModifiers();
    // ()I
    private NativeMethod getModifiers = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object thiz = vars.getThis();
        Class clazz = (Class) thiz.getExtra();
        int modifiers = clazz.getAccessFlags();

        OperandStack stack = frame.operandStack;
        stack.pushInt(modifiers);
    };

    // public native Class<? super T> getSuperclazz();
    // ()Ljava/lang/Class;
    private NativeMethod getSuperclass = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object thiz = vars.getThis();
        Class clazz = (Class) thiz.getExtra();
        Class superClass = clazz.getSuperClazz();

        OperandStack stack = frame.operandStack;
        if (Objects.nonNull(superClass)) {
            stack.pushRef(superClass.getJClass());
        } else {
            stack.pushRef(null);
        }
    };

    // private native Class<?>[] getInterfaces0();
    // ()[Ljava/lang/Class;
    private NativeMethod getInterfaces0 = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object thiz = vars.getThis();
        Class clazz = (Class) thiz.getExtra();
        Class[] interfaces = clazz.getInterfaces();
        Object clazzArr = ClassHelper.toClassArr(clazz.getClassloader(), interfaces);

        OperandStack stack = frame.operandStack;
        stack.pushRef(clazzArr);
    };

    // public native boolean isArray();
    // ()Z
    private NativeMethod isArray = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object thiz = vars.getThis();
        Class clazz = (Class) thiz.getExtra();
        OperandStack stack = frame.operandStack;
        stack.pushBoolean(clazz.isArray());
    };

    // public native Class<?> getComponentType();
    // ()Ljava/lang/Class;
    private NativeMethod getComponentType = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object thiz = vars.getThis();
        Class clazz = (Class) thiz.getExtra();
        Class componentClass = clazz.componentClass();
        Object componentClassObj = componentClass.getJClass();

        OperandStack stack = frame.operandStack;
        stack.pushRef(componentClassObj);
    };

    // public native boolean isAssignableFrom(Class<?> cls);
    // (Ljava/lang/Class;)Z
    private NativeMethod isAssignableFrom = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object thiz = vars.getThis();
        Object cls = vars.getRef(1);

        Class thisClass = (Class) thiz.getExtra();
        Class clsClass = (Class) cls.getExtra();
        boolean ok = thisClass.isAssignableFrom(clsClass);

        OperandStack stack = frame.operandStack;
        stack.pushBoolean(ok);
    };

}
