package com.lgb._native.java.io;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;

public class _FileDescriptor {

    public static final String fd = "java/io/FileDescriptor";

    public _FileDescriptor() {
        Registry.register(fd, "set", "(I)J", set);
    }

    // private static native long set(int d);
    // (I)J
    private NativeMethod set = (frame) -> {
        // todo
        frame.operandStack.pushLong(0);
    };
}
