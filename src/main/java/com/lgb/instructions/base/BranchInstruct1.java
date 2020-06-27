package com.lgb.instructions.base;

import com.lgb.rtda.Frame;
import com.lgb.rtda.Thread;

public class BranchInstruct1 implements Instruction {
    private int offset;
    private int nextPC;

    private BranchInstruct branchInstruct;

    public BranchInstruct1(BranchInstruct branchInstruct) {
        this.branchInstruct = branchInstruct;
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        branchInstruct.fetchOperands(reader);
        this.offset = branchInstruct.offset;
    }

    @Override
    public void execute(Frame frame) {
        int pc = frame.thread.pc();
        this.nextPC = pc + offset;
        branchInstruct.execute(frame);
    }

    @Override
    public String toString() {
        return branchInstruct.getClass().getSimpleName()+"{" +
                "offset=" + offset +
                ", nextPC=" + nextPC +
                '}';
    }
}
