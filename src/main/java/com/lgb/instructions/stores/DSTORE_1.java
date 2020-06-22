package com.lgb.instructions.stores;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class DSTORE_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        _dstore(frame, 1);
    }

}
