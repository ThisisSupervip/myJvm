package com.lgb.instructions.stores;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class DSTORE_0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        _dstore(frame, 0);
    }

}