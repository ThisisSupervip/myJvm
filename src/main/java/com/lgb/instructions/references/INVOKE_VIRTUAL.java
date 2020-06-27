package com.lgb.instructions.references;

import com.lgb.instructions.base.Index16Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.constantPool.MethodRef;

public class INVOKE_VIRTUAL extends Index16Instruction {

    // hack!
    @Override
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.getConstantPool();
        MethodRef methodRef = (MethodRef) constantPool.getConstant(this.index);
        if(methodRef.getName().equals("println")){
            OperandStack stack = frame.operandStack;
            switch (methodRef.getDescriptor()){
                case "(Z)V":
                    System.out.println(stack.popInt() != 0);
                    break;
                case "(C)V":
                    System.out.println(stack.popInt());
                    break;
                case "(I)V":
                case "(B)V":
                case "(S)V":
                    System.out.println(stack.popInt());
                    break;
                case "(F)V":
                    System.out.println(stack.popFloat());
                    break;
                case "(J)V":
                    System.out.println(stack.popLong());
                    break;
                case "(D)V":
                    System.out.println(stack.popDouble());
                    break;
                default:
                    System.out.println(methodRef.getDescriptor());
                    break;
            }
        }
    }
}
