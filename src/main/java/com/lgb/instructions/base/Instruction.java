package com.lgb.instructions.base;

import com.lgb.rtda.Frame;

public interface Instruction {
    void fetchOperands(BytecodeReader reader);
    void execute(Frame frame);
}
