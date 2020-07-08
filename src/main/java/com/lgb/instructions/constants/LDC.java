package com.lgb.instructions.constants;

import com.lgb.instructions.base.Index8Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.StringPool;
import com.lgb.rtda.heap.constantPool.ClassRef;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Object;

public class LDC extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
        ldc(frame, this.index);
    }

    public static void ldc(Frame frame, int index) {
        OperandStack stack = frame.operandStack;
        Class clazz = frame.method.getClazz();
        ConstantPool runTimeConstantPool = clazz.getConstantPool();
        java.lang.Object c = runTimeConstantPool.getConstant(index);

        if (c instanceof Integer) {
            stack.pushInt((Integer) c);
            return;
        }

        if (c instanceof Float) {
            stack.pushFloat((Float) c);
            return;
        }

        if (c instanceof String) {
            Object internedStr = StringPool.jString(clazz.getClassloader(), (String) c);
            stack.pushRef(internedStr);
            return;
        }

        if (c instanceof ClassRef){
            ClassRef classRef = (ClassRef) c;
            Object classObj = classRef.resolvedClass().getJClass();
            stack.pushRef(classObj);
            return;
        }

        throw new RuntimeException("todo ldc");
    }
}
