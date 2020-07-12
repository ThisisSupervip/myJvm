package com.lgb.instructions.references;

import com.lgb.instructions.base.Index16Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.constantPool.ClassRef;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Object;

public class CHECK_CAST extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.operandStack;
        Object ref = operandStack.popRef();
        operandStack.pushRef(ref);
        if (null == ref) return;
        ConstantPool constantPool = frame.method.getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) constantPool.getConstant(this.index);
        Class clazz = classRef.resolvedClass();
        if(!ref.isInstanceOf(clazz)) {
            throw new ClassCastException();
        }
    }
}
