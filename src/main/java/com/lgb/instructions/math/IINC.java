package com.lgb.instructions.math;

import com.lgb.instructions.base.BytecodeReader;
import com.lgb.instructions.base.Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.LocalVariables;

public class IINC implements Instruction {
    private int index;
    private int constVal;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readUint8().toShort();
        this.constVal = reader.readInt8();
    }

    @Override
    public void execute(Frame frame) {
        LocalVariables localVars = frame.localVariables;
        int val = localVars.getInt(this.index);
        val += this.constVal;
        localVars.setInt(this.index, val);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setConst(int constVal) {
        this.constVal = constVal;
    }

    @Override
    public String toString() {
        return "IINC{" +
                "index=" + index +
                ", constVal=" + constVal +
                '}';
    }
}
