package com.lgb._native.java.util.concurrent.atomic;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;

public class _AtomicLong {
    public _AtomicLong() {
        Registry.register("java/util/concurrent/atomic/AtomicLong", "VMSupportsCS8", "()Z", vmSupportsCS8);
    }

    private NativeMethod vmSupportsCS8 = (frame) -> {
        frame.operandStack.pushBoolean(false);
    };
}
