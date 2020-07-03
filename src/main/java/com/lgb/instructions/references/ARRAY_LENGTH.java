package com.lgb.instructions.references;


import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.methodarea.Object;

public class ARRAY_LENGTH extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {

        OperandStack stack = frame.operandStack;
        Object arrRef = stack.popRef();
        if (null == arrRef){
            throw new NullPointerException();
        }

        int arrLen = arrRef.arrayLength();
        stack.pushInt(arrLen);
    }

}
