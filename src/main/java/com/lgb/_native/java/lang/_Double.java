package com.lgb._native.java.lang;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;

public class _Double {

    private final String jlDouble = "java/lang/Double";

    public _Double() {
        Registry.register(jlDouble, "doubleToRawLongBits", "(D)J", doubleToRawLongBits);
        Registry.register(jlDouble, "longBitsToDouble", "(J)D", longBitsToDouble);
        Registry.register(jlDouble, "registerNatives", "()V", registerNatives);
    }

    public NativeMethod registerNatives = (frame) -> {
        // do nothing
    };

    public NativeMethod doubleToRawLongBits = (frame) -> {
        double val = frame.localVariables.getDouble(0);
        frame.operandStack.pushLong((long) val);
    };

    public NativeMethod longBitsToDouble = (frame) -> {
        long val = frame.localVariables.getLong(0);
        frame.operandStack.pushDouble(val);
    };

}
