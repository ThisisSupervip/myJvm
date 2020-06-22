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
        byteBuffer.putInt(size*4, val);
        size++;
    }

    public int popInt() {
        size--;
        return byteBuffer.getInt(size*4);
    }

    public void pushFloat(float val) {
        byteBuffer.putFloat(size*4, val);
        size++;
    }

    public float popFloat() {
        size--;
        return byteBuffer.getFloat(size*4);
    }

    public void pushLong(long val) {
        byteBuffer.putLong(size*8, val);
        size+=2;
    }

    public long popLong() {
        size-=2;
        return byteBuffer.getLong(size*4);
    }

    public void pushDouble(double val) {
        byteBuffer.putDouble(size*8, val);
        size+=2;
    }

    public double popDouble() {
        size-=2;
        return byteBuffer.getDouble(size*4);
    }

    public void pushRef(Object ref) {

    }

    public Object popRef(){
        return null;
    }

    public void pushSlot(byte slot) {
        this.byteBuffer.putInt(slot);
        this.size++;
    }

    public byte popSlot(){
        this.size--;
        return this.byteBuffer.get(size);
    }
}
