package com.lgb.instructions.constants;

import com.lgb.instructions.base.Index16Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.methodarea.Class;

public class LDC2_W extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        Class clazz = frame.method.getClazz();
        ConstantPool constantPool = clazz.getConstantPool();
        Object o = constantPool.getConstant(this.index);
        if (o instanceof Long) {
            stack.pushLong((Long) o);
            return;
        }
        if (o instanceof Double){
            stack.pushDouble((Double) o);
            return;
        }
        throw new ClassFormatError();
    }
}
