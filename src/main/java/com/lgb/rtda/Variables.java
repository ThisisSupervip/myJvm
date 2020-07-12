package com.lgb.rtda;

import com.lgb.rtda.heap.methodarea.Object;

import java.nio.ByteBuffer;

public class Variables implements Cloneable {
    protected ByteBuffer byteBuffer;
    protected int size;

    public Variables(int size) {
        this.size = size;
        this.byteBuffer = byteBuffer.allocate(4 * size);
    }

    public void setInt(int index, int val) {
        int bufferIdx = index * 4;
        byteBuffer.putInt(bufferIdx, val);
    }

    public int getInt(int index) {
        int bufferIdx = index * 4;
        return byteBuffer.getInt(bufferIdx);
    }

    public void setFloat(int index, float val) {
        int bufferIdx = index * 4;
        byteBuffer.putFloat(bufferIdx, val);
    }

    public float getFloat(int index) {
        int bufferIdx = index * 4;
        return byteBuffer.getFloat(bufferIdx);
    }

    public void setLong(int index, long val) {
        int bufferIdx = index * 4;
        byteBuffer.putLong(bufferIdx, val);
    }

    public long getLong(int index) {
        int bufferIdx = index * 4;
        return byteBuffer.getLong(bufferIdx);
    }

    public void setDouble(int index, double val) {
        int bufferIdx = index * 4;
        byteBuffer.putDouble(bufferIdx, val);
    }

    public double getDouble(int index) {
        int bufferIdx = index * 4;
        return byteBuffer.getDouble(bufferIdx);
    }

    public void setRef(int index, Object ref) {
        int add = Memory.add(ref);
        byteBuffer.putInt(index * 4, add);
    }

    public Object getRef(int index) {
        int anInt = byteBuffer.getInt(index * 4);
        return Memory.objects.get(anInt);
    }

    public void setSlot(int index, byte slot) {

    }


    public byte[] byteArray() {
        return byteBuffer.array();
    }

    public boolean getBoolean(int index) {
        return getInt(index) == 1;
    }

    @Override
    public java.lang.Object clone() {
        Variables res = new Variables(this.size);
        res.byteBuffer = byteBuffer.wrap(this.byteBuffer.array().clone());
        return res;
    }


}
