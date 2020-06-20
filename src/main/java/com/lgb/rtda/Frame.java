package com.lgb.rtda;

public class Frame {

    LocalVariables localVariables;
    OperandStack operandStack;

    public Frame(int maxLocals, int maxStack) {
        this.localVariables = new LocalVariables(maxLocals);
        this.operandStack = new OperandStack(maxStack);
    }
}
