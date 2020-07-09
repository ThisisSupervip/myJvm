package com.lgb._native.java.lang;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.rtda.Frame;
import com.lgb.rtda.Thread;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class _Throwable {

    private final String jlThrowable = "java/lang/Throwable";

    public _Throwable() {
        Registry.register(jlThrowable, "fillInStackTrace", "(I)Ljava/lang/Throwable;", fillInStackTrace);
        Registry.register(jlThrowable, "registerNatives", "()V", registerNatives);
    }

    private StackTraceElement stackTraceElement;

    private static class StackTraceElement {
        private String fileName;
        private String className;
        private String methodName;
        private int lineNumber;

        public StackTraceElement(Frame frame) {
            Method method = frame.method;
            Class clazz = method.getClazz();
            this.fileName = clazz.getSourceFile();
            this.className = clazz.javaName();
            this.methodName = method.getName();
            this.lineNumber = method.getLineNumber(frame.nextPC() - 1);
        }
    }


    private static _Throwable createStackTraceElement(Frame frame) {
        _Throwable res = new _Throwable();
        res.stackTraceElement = new StackTraceElement(frame);
        return res;
    }

    public NativeMethod registerNatives = (frame) -> {
        // do nothing
    };

    public NativeMethod fillInStackTrace = (frame) -> {
        Object thiz = frame.localVariables.getThis();
        frame.operandStack.pushRef(thiz);

        _Throwable[] stes = createStackTraceElements(thiz, frame.thread);
        thiz.setExtra(stes);
    };

    private _Throwable[] createStackTraceElements(Object tObj, Thread thread) {
        int skip = distanceToObject(tObj.clazz) + 1;
        Frame[] frames = thread.getFrames();
        int stackLen = frames.length - skip;
        List<_Throwable> stes = new ArrayList<>(stackLen);
        for (int i = 0; i < stackLen; i++) {
            stes.add(createStackTraceElement(frames[i]));
        }
        Collections.reverse(stes);
        _Throwable[] res = new _Throwable[stackLen];
        return stes.toArray(res);
    }

    private int distanceToObject(Class clazz) {
        int distance = 0;
        for (Class c = clazz.getSuperClazz(); c != null; c = c.getSuperClazz()) {
            distance++;
        }
        return distance;
    }

    public String toString() {
        return String.format("%s.%s(%s:%d)", this.stackTraceElement.className, this.stackTraceElement.methodName, this.stackTraceElement.fileName, this.stackTraceElement.lineNumber);
    }

}
