package com.lgb._native.java.lang;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.rtda.heap.StringPool;
import com.lgb.rtda.heap.methodarea.Object;

public class _String {

    private final String jlString = "java/lang/String";

    public _String() {
        Registry.register(jlString, "intern", "()Ljava/lang/String;", intern);
        Registry.register(jlString, "registerNatives", "()V", registerNatives);
    }

    public NativeMethod registerNatives = (frame) -> {
        // do nothing
    };

    public NativeMethod intern = (frame) -> {
        Object thiz = frame.localVariables.getThis();
        Object interned = StringPool.internString(thiz);
        frame.operandStack.pushRef(interned);
    };

}
