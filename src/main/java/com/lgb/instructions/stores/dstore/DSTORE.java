package com.lgb.instructions.stores.dstore;

import com.lgb.instructions.base.Index8Instruction;
import com.lgb.rtda.Frame;

public class DSTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        _astore(frame, this.index);
    }

}
