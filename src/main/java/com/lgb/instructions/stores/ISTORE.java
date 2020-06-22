package com.lgb.instructions.stores;

import com.lgb.instructions.base.Index8Instruction;
import com.lgb.rtda.Frame;

public class ISTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        _istore(frame, this.index);
    }

}