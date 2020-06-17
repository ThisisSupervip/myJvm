package com.lgb.classfile;

import com.lgb.classfile.fundamental.U1;
import com.lgb.classfile.fundamental.U2;
import com.lgb.classfile.fundamental.U4;

import java.nio.ByteBuffer;

public class ClassReader {
    private byte[] bytes;
    int pos = 0;
    public ClassReader(byte[] bytes) {
        this.bytes = bytes;
    }

    public U1 readU1(){ //uint8
        return new U1(readBytes(1));
    }

    public U2 readU2(){ //uint16
        return new U2(readBytes(2));
    }

    public int readU2Int(){
        byte[] v = new byte[4];
        byte[] bytes = readBytes(2);
        System.arraycopy(bytes, 0, v, 2, 2);
        return ByteBuffer.wrap(v).getInt();
    }

    public U4 readU4(){ //uint32
        return new U4(readBytes(4));
    }

    public byte[] readBytes(int size){
        byte[] res = new byte[size];
        if(pos+1 <= bytes.length) {
            System.arraycopy(bytes, pos, res, 0, size);
            pos += size;
        } else {
            return null;
        }
        return res;
    }

}
