package com.lgb.instructions.stores.fstore;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class FSTORE_2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        _fstore(frame, 2);
    }

}
