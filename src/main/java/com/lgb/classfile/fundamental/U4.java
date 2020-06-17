package com.lgb.classfile.fundamental;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class U4 {
    public final byte[] value;

    public U4(byte[] value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        U4 u4 = (U4) o;
        return Arrays.equals(value, u4.value);
    }

    public long toLong(){
        return ByteBuffer.wrap(value).getLong();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
