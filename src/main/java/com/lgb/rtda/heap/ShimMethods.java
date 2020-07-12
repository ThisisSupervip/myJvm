package com.lgb.rtda.heap;

import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;

public class ShimMethods {
    private static Class _shimClass  = new Class() {{setName("~shim");}};
    private static byte[] _returnCode = new byte[]{(byte) 0xb1};
    public static Method shimReturnMethod(){
        Method method = new Method();
        method.setName("<return>");
        method.setClazz(_shimClass);
        method.setCode(_returnCode);
        return method;
    }
}
