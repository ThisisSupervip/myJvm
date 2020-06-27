package com.lgb.instructions.references;

import com.lgb.instructions.base.Index16Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.constantPool.ClassRef;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Object;

import java.util.Objects;

public class INSTANCE_OF extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.operandStack;
        Object ref = operandStack.popRef();
        if(Objects.isNull(ref)) {
            operandStack.pushInt(0);
            return;
        }
        ConstantPool constantPool = frame.method.getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) constantPool.getConstant(this.index);
        Class clazz = classRef.resolvedClass();
        if(ref.isInstanceOf(clazz)){
            operandStack.pushInt(1);
        } else {
            operandStack.pushInt(0);
        }
    }
}
