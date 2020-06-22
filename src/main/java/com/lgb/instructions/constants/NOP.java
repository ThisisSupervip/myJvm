package com.lgb.instructions.constants;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class NOP extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        //nothing to do
    }
}
