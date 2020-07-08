package com.lgb.rtda;

import com.lgb.rtda.heap.methodarea.Object;

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
        int add = Memory.add(ref);
        this.byteBuffer.putInt(size*4, add);
        size++;
    }

    public Object popRef(){
        size--;
        int anInt = this.byteBuffer.getInt(size * 4);
        Object res = Memory.objects.get(anInt);
        this.byteBuffer.putInt(size*4, 0);
        return res;
    }

    public void pushSlot(int slot) {
        this.byteBuffer.putInt(size*4, slot);
        this.size++;
    }

    public int popSlot(){
        this.size--;
        return this.byteBuffer.getInt(size*4);
    }

    public void pushBoolean(boolean val) {
        if (val) {
            this.pushInt(1);
        } else {
            this.pushInt(0);
        }
    }


    public byte[] byteArray(){
        return byteBuffer.array();
    }

    public Object getRefFromTop(int n) {
        int anInt = this.byteBuffer.getInt((this.size - 1 - n) * 4);

        return Memory.objects.get(anInt);
    }
}
