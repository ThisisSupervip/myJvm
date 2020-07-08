package com.lgb._native.java.lang;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.rtda.LocalVariables;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Object;

public class _System {
    private final String jlSystem = "java/lang/System";

    public _System() {
        Registry.register(jlSystem, "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", arraycopy);
        Registry.register(jlSystem, "registerNatives", "()V", registerNatives);
    }

    public NativeMethod registerNatives = (frame) -> {
        // do nothing
    };

    private NativeMethod arraycopy = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object src = vars.getRef(0);
        int srcPos = vars.getInt(1);
        Object dest = vars.getRef(2);
        int destPos = vars.getInt(3);
        int length = vars.getInt(4);

        if (null == src || dest == null) {
            throw new NullPointerException();
        }

        if (!checkArrayCopy(src, dest)) {
            throw new ArrayStoreException();
        }

        if (srcPos < 0 || destPos < 0 || length < 0 ||
                srcPos + length > src.arrayLength() ||
                destPos + length > dest.arrayLength()) {
            throw new IndexOutOfBoundsException();
        }

        System.arraycopy(src.data, srcPos, dest.data, destPos, length);

    };

    private boolean checkArrayCopy(Object src, Object dest) {
        Class srcClass = src.clazz;
        Class destClass = dest.clazz;

        if (!srcClass.isArray() || !destClass.isArray()) {
            return false;
        }

        if (srcClass.componentClass().IsPrimitive() || destClass.componentClass().IsPrimitive()) {
            return srcClass == destClass;
        }

        return true;

    }
}
