package com.lgb.classfile.fundamental;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class U2 {
    public byte[] value;

    public U2(byte[] value) {
        this.value = value;
    }

    public int toInt(){
        byte[] v = new byte[4];
        System.arraycopy(value, 0, v, 2, 2);
        return ByteBuffer.wrap(v).getInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        U2 u2 = (U2) o;
        return Arrays.equals(value, u2.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
