package com.lgb.instructions.constants;

import com.lgb.instructions.base.Index16Instruction;
import com.lgb.rtda.Frame;

public class LDC_W extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        LDC.ldc(frame, this.index);
    }
}
