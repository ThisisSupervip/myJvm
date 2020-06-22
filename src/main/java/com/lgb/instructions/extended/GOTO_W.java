package com.lgb.instructions.extended;

import com.lgb.instructions.base.BranchInstruct;
import com.lgb.instructions.base.BytecodeReader;
import com.lgb.rtda.Frame;

public class GOTO_W extends BranchInstruct {

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.offset = reader.readInt32();
    }

    @Override
    public void execute(Frame frame) {
        branch(frame, this.offset);
    }
}
