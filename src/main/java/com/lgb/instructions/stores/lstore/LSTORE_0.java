package com.lgb.instructions.stores.lstore;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class LSTORE_0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        _lstore(frame, 0);
    }

}
