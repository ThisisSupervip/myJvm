package com.lgb.classfile.fundamental;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class U1 {
    public final byte[] value;

    public U1(byte[] value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        U1 u1 = (U1) o;
        return Arrays.equals(value, u1.value);
    }

    public short toShort(){
        byte[] v = new byte[2];
        v[1] = value[0];
        return ByteBuffer.wrap(v).getShort();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
