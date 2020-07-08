package com.lgb.rtda.heap.methodarea;

import com.lgb.rtda.Variables;

public class Slots extends Variables {

    public Slots(int size) {
        super(size);
    }

    @Override
    public java.lang.Object clone() {
        Slots res = new Slots(this.size);
        res.byteBuffer = byteBuffer.wrap(this.byteBuffer.array().clone());
        return res;
    }
}
