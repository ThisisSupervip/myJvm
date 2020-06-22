package com.lgb.rtda;

public class Frame {

    public final LocalVariables localVariables;
    public final OperandStack operandStack;
    public final Thread thread;
    private int nextPC;

    public Frame(int maxLocals, int maxStack, Thread thread) {
        this.localVariables = new LocalVariables(maxLocals);
        this.operandStack = new OperandStack(maxStack);
        this.thread = thread;
    }

    public void setNextPC(int nextPC) {
        this.nextPC = nextPC;
    }

    public int nextPC() {
        return nextPC;
    }
}
