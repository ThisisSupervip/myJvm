package com.lgb.instructions.stores.astore;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class ASTORE_2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        _astore(frame, 2);
    }

}

