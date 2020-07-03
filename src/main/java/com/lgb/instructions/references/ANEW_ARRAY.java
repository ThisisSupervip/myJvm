package com.lgb.instructions.references;

import com.lgb.instructions.base.Index16Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.constantPool.ClassRef;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Object;

public class ANEW_ARRAY extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        
        ConstantPool constantPool = frame.method.getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) constantPool.getConstant(this.index);
        Class componentClass = classRef.resolvedClass();

        OperandStack stack = frame.operandStack;
        int count = stack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException();
        }

        Class arrClass = componentClass.arrayClass();
        Object arr = arrClass.newArray(count);
        stack.pushRef(arr);

    }

}
