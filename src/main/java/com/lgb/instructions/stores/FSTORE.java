package com.lgb.instructions.stores;

import com.lgb.instructions.base.Index8Instruction;
import com.lgb.rtda.Frame;

public class FSTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        _fstore(frame, this.index);
    }

}
