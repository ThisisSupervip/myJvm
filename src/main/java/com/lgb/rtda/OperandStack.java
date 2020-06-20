package com.lgb.rtda;

import java.nio.ByteBuffer;

public class OperandStack {
    private ByteBuffer byteBuffer;
    private int size;
    private int maxSize;

    public OperandStack(int maxStack) {
        this.size = 0;
        this.maxSize = maxStack;
        byteBuffer = byteBuffer.allocate(maxStack*4);
    }

    public void pushInt(int val) {
        if(size>maxSize) {
            //todo throw
        }
        byteBuffer.putInt(val);
        size++;
    }

    public int popInt() {
        size--;
        return byteBuffer.getInt(size*4);
    }

    public void pushFloat(float val) {
        byteBuffer.putFloat(val);
        size++;
    }

    public float popFloat() {
        size--;
        return byteBuffer.getFloat(size*4);
    }

    public void pushLong(long val) {
        byteBuffer.putLong(val);
        size+=2;
    }

    public long popLong() {
        size-=2;
        return byteBuffer.getLong(size*4);
    }

    public void pushDouble(double val) {
        byteBuffer.putDouble(val);
        size+=2;
    }

    public double popDouble() {
        size-=2;
        return byteBuffer.getDouble(size*4);
    }

}
