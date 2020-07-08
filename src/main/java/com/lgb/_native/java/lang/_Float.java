package com.lgb._native.java.lang;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;

public class _Float {

    private final String jlFloat = "java/lang/Float";

    public _Float() {
        Registry.register(jlFloat, "floatToRawIntBits", "(F)I", floatToRawIntBits);
        Registry.register(jlFloat, "intBitsToFloat", "(I)F", intBitsToFloat);
        Registry.register(jlFloat, "registerNatives", "()V", registerNatives);
    }

    public NativeMethod registerNatives = (frame) -> {
        // do nothing
    };

    public NativeMethod floatToRawIntBits = (frame) -> {
        float val = frame.localVariables.getFloat(0);
        frame.operandStack.pushInt((int) val);
    };

    public NativeMethod intBitsToFloat = (frame) -> {
        int val = frame.localVariables.getInt(0);
        frame.operandStack.pushFloat(val);
    };

}
