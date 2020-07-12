package com.lgb._native.java.lang;

import com.lgb._native.Registry;

public class _ClassLoader {
    public _ClassLoader() {
        Registry.register(null, "java/lang/ClassLoader", "findBuiltinLib", "(Ljava/lang/String;)Ljava/lang/String;");
    }
}
