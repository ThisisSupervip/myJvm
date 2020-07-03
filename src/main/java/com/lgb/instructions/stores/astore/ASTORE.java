package com.lgb.instructions.stores.astore;

import com.lgb.instructions.base.Index8Instruction;
import com.lgb.rtda.Frame;

//store reference into local variable
public class ASTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        _astore(frame, this.index);
    }

}
