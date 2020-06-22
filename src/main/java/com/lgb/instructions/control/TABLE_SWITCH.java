package com.lgb.instructions.control;

import com.lgb.instructions.base.BranchInstruct;
import com.lgb.instructions.base.BytecodeReader;
import com.lgb.rtda.Frame;

public class TABLE_SWITCH extends BranchInstruct {
    private int defaultOffset;
    private int low;
    private int high;
    private int[] jumpOffsets;
    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();
        this.defaultOffset = reader.readInt32();
        this.low = reader.readInt32();
        this.high = reader.readInt32();
        int jumpOffsetsCount = this.high - this.low + 1;
        this.jumpOffsets = reader.readInt32s(jumpOffsetsCount);
    }

    @Override
    public void execute(Frame frame) {
        int index = frame.operandStack.popInt();
        int offset;
        if(index>=this.low&&index<=this.high){
            offset = this.jumpOffsets[index - this.low];
        } else {
            offset = this.defaultOffset;
        }
        branch(frame, offset);
    }
}
