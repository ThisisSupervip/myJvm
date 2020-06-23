package com.lgb.instructions.base;

import com.lgb.rtda.Frame;

public abstract class Index8Instruction implements Instruction {

    protected int index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readUint8().toShort();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    protected void _astore(Frame frame, int idx) {
        Object ref = frame.operandStack.popRef();
        frame.localVariables.setRef(idx, ref);
    }

    protected void _fstore(Frame frame, int idx) {
        float val = frame.operandStack.popFloat();
        frame.localVariables.setFloat(idx, val);
    }

    protected void _istore(Frame frame, int idx) {
        int val = frame.operandStack.popInt();
        frame.localVariables.setInt(idx, val);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "index=" + index +
                '}';
    }
}
