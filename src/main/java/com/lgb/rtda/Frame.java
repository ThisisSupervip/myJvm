package com.lgb.rtda;

import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.methodarea.Method;

import java.util.Arrays;
import java.util.List;

public class Frame {

    public final LocalVariables localVariables;
    public final OperandStack operandStack;
    private int nextPC;
    public final Method method;
    public final Thread thread;

    public Frame(Thread thread, Method method) {
        this.thread = thread;
        this.method = method;
        this.localVariables = new LocalVariables(method.getMaxLocals());
        this.operandStack = new OperandStack(method.getMaxStack());
    }

    public Frame(OperandStack operandStack, Method method, Thread thread) {
        this.operandStack = operandStack;
        this.localVariables = new LocalVariables(method.getMaxLocals());
        this.method = method;
        this.thread = thread;
    }

    public void setNextPC(int nextPC) {
        this.nextPC = nextPC;
    }

    public void revertNextPC() {
        this.nextPC = this.thread.pc();
    }
    public int nextPC() {
        return nextPC;
    }

    public ConstantPool getConstantPool() {
        return this.method.getClazz().getConstantPool();
    }



}
