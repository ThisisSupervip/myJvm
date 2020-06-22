package com.lgb.instructions.stores;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class FSTORE_0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        _fstore(frame, 0);
    }

}