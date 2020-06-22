package com.lgb.instructions.stores;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class LSTORE_3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        _lstore(frame, 3);
    }

}

