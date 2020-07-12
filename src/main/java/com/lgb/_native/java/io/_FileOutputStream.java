package com.lgb._native.java.io;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.rtda.LocalVariables;
import com.lgb.rtda.heap.methodarea.Object;

public class _FileOutputStream {

    public static final String fos = "java/io/FileOutputStream";
    public _FileOutputStream() {
        Registry.register(fos, "writeBytes", "([BIIZ)V", writeBytes);
    }

    // private native void writeBytes(byte b[], int off, int len, boolean append) throws IOException;
    // ([BIIZ)V
    private NativeMethod writeBytes = (frame) -> {
        LocalVariables vars = frame.localVariables;
        //this := vars.GetRef(0)
        Object b = vars.getRef(1);
        int off = vars.getInt(2);
        int len = vars.getInt(3);
        boolean append = vars.getBoolean(4);

        byte[] jBytes = (byte[])b.data;
        System.out.write(jBytes, off, len);
    };
}
