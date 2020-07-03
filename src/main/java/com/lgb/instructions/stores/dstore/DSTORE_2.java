package com.lgb.instructions.stores.dstore;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class DSTORE_2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        _dstore(frame, 2);
    }

}

