package com.lgb.instructions.base;

import com.lgb.classfile.fundamental.U1;
import com.lgb.classfile.fundamental.U2;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class BytecodeReader {
    private byte[] code;
    ByteBuffer byteBuffer;
    @Getter
    private int pc;

    public void reset(byte[] code, int pc) {
        this.code = code;
        byteBuffer = ByteBuffer.wrap(code);
        this.pc = pc;
    }

    public byte readByte() {
        byte code = this.code[this.pc];
        this.pc++;
        return code;
    }

    public byte readInt8() {
        byte i = code[pc++];
        return i;
    }

    public U1 readUint8() {
        byte[] i = Arrays.copyOfRange(code, pc, pc + 1);
        pc++;
        return new U1(i);
    }

    public short readInt16() {
        short aShort = byteBuffer.getShort(pc);
        pc += 2;
        return aShort;
    }

    public U2 readUint16() {
        byte[] i = Arrays.copyOfRange(code, pc, pc + 2);
        pc += 2;
        return new U2(i);
    }

    public int readInt32() {
        int anInt = byteBuffer.getInt(pc);
        pc += 4;
        return anInt;
    }

    public int[] readInt32s(int n) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = readInt32();
        }
        return res;
    }

    public void skipPadding() {
        for (; (this.pc % 4) != 0; ) {
            readInt8();
        }
    }

}
