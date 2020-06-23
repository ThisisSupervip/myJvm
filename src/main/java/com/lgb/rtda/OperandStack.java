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
        int val = byteBuffer.getInt(size * 4);
        byteBuffer.putInt(size * 4, 0);
        return val;
    }

    public void pushFloat(float val) {
        byteBuffer.putFloat(size*4, val);
        size++;
    }

    public float popFloat() {
        size--;
        float val = byteBuffer.getFloat(size*4);
        byteBuffer.putInt(size * 4, 0);
        return val;
    }

    public void pushLong(long val) {
        byteBuffer.putLong(size*4, val);
        size+=2;
    }

    public long popLong() {
        size-=2;
        long val = byteBuffer.getLong(size*4);
        byteBuffer.putLong(size*4, 0L);
        return val;
    }

    public void pushDouble(double val) {
        byteBuffer.putDouble(size*4, val);
        size+=2;
    }

    public double popDouble() {
        size-=2;
        double val = byteBuffer.getDouble(size*4);
        byteBuffer.putDouble(size*4, 0);
        return val;
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

    public byte[] byteArray(){
        return byteBuffer.array();
    }
}
