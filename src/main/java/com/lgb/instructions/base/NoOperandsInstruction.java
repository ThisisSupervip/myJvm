package com.lgb.instructions.base;

import com.lgb.rtda.Frame;
import com.lgb.rtda.heap.methodarea.Object;

public abstract class NoOperandsInstruction implements Instruction {
    @Override
    public void fetchOperands(BytecodeReader reader) {
        //nothing to do
    }

    protected void _astore(Frame frame, int idx) {
        Object ref = frame.operandStack.popRef();
        frame.localVariables.setRef(idx, ref);
    }

    protected void _dstore(Frame frame, int idx) {
        double val = frame.operandStack.popDouble();
        frame.localVariables.setDouble(idx, val);
    }

    protected void _fstore(Frame frame, int idx) {
        float val = frame.operandStack.popFloat();
        frame.localVariables.setFloat(idx, val);
    }

    protected void _istore(Frame frame, int idx) {
        int val = frame.operandStack.popInt();
        frame.localVariables.setInt(idx, val);
    }

    protected void _lstore(Frame frame, int idx) {
        long val = frame.operandStack.popLong();
        frame.localVariables.setLong(idx, val);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
