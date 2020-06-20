package com.lgb.rtda;

public class Thread {
    int pc;
    Frame[] stack;
    int stackSize;

    Thread() {
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
}
