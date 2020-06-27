package com.lgb.rtda;

import com.lgb.rtda.heap.methodarea.Method;

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

    public Frame newFrame(Method method) {
        return new Frame(this, method);
    }

    public void setPC(int pc) {
        this.pc = pc;
    }
    public int pc() {
        return this.pc;
    }
}
