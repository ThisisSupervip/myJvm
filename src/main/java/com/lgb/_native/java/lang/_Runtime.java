package com.lgb._native.java.lang;

import com.lgb._native.Registry;

public class _Runtime {
    public _Runtime() {
        Registry.register(Runtime.getRuntime(), "java/lang/Runtime", "availableProcessors", "()I");
    }
}
