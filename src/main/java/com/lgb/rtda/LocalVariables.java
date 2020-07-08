package com.lgb.rtda;

import com.lgb.rtda.heap.methodarea.Object;

public class LocalVariables extends Variables {

    public LocalVariables(int maxLocals) {
        super(maxLocals);
    }

    public Object getThis() {
        return getRef(0);
    }

    @Override
    public java.lang.Object clone() {
        LocalVariables res = new LocalVariables(this.size);
        res.byteBuffer = byteBuffer.wrap(this.byteBuffer.array().clone());
        return res;
    }
}
