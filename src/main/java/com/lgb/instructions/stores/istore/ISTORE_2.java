package com.lgb.instructions.stores.istore;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class ISTORE_2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        _istore(frame, 2);
    }

}

