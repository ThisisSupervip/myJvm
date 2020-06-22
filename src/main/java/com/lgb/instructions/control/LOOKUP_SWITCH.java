package com.lgb.instructions.control;

import com.lgb.instructions.base.BranchInstruct;
import com.lgb.instructions.base.BytecodeReader;
import com.lgb.rtda.Frame;

public class LOOKUP_SWITCH extends BranchInstruct {
    private int defaultOffset;
    private int npairs;
    private int[] matchOffsets;
    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();
        this.defaultOffset = reader.readInt32();
        this.npairs = reader.readInt32();
        this.matchOffsets = reader.readInt32s(this.npairs * 2);
    }

    @Override
    public void execute(Frame frame) {
        int key = frame.operandStack.popInt();
        for (int i = 0; i < this.npairs * 2; i+=2) {
            if(this.matchOffsets[i] == key) {
                int offset = this.matchOffsets[i+1];
                branch(frame, offset);
                return;
            }
        }
        branch(frame, defaultOffset);
    }
}
