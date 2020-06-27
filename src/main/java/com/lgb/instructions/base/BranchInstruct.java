package com.lgb.instructions.base;

import com.lgb.rtda.Frame;
import com.lgb.rtda.Thread;

public abstract class BranchInstruct implements Instruction {
    protected int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        offset = reader.readInt16();
    }

    public static void branch(Frame frame, int offset) {
        int pc = frame.thread.pc();
        int nextPc = pc + offset;
        frame.setNextPC(nextPc);
    }


    @Override
    public String toString() {
        return "BranchInstruct{" +
                "offset=" + offset +
                '}';
    }
}
