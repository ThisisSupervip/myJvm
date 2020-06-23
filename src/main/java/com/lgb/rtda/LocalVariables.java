package com.lgb.rtda;

import java.nio.ByteBuffer;

public class LocalVariables {

    private ByteBuffer byteBuffer;
    private int maxLocals;

    public LocalVariables(int maxLocals) {
        this.maxLocals = maxLocals;
        this.byteBuffer = byteBuffer.allocate(4 * maxLocals);
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

    }

    public Object getRef(int index) {
        return null;
    }


    public byte[] byteArray(){
        return byteBuffer.array();
    }
}
