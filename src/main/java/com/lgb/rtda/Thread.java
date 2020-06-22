package com.lgb.rtda;

public class Thread {
    private int pc;
    public final Frame[] stack;
    private int stackSize;

    public Thread() {
        stackSize = 0;
        stack = new Frame[1024];
    }

    public void pushFrame(Frame frame) {
        if(stackSize==stack.length){
            throw new StackOverflowError();
        }
        stack[stackSize++] = frame;
    }
    public Frame popFrame() {
        if(stackSize==0){
            throw new RuntimeException("jvm stack is empty");
        }
        return stack[--stackSize];
    }

    public Frame newFrame(int maxLocals, int maxStack) {
        return new Frame(maxLocals, maxStack, this);
    }

    public void setPC(int pc) {
        this.pc = pc;
    }
    public int pc() {
        return this.pc;
    }
}
