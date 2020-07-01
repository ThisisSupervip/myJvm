package com.lgb.instructions.references;

import com.lgb.instructions.base.ClassInitLogic;
import com.lgb.instructions.base.Index16Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.Variables;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.constantPool.FieldRef;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Field;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Slots;

public class PUT_STATIC extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        Method method = frame.method;
        Class currentClazz = method.getClazz();
        ConstantPool constantPool = currentClazz.getConstantPool();
        FieldRef fieldRef = (FieldRef) constantPool.getConstant(index);
        Field field = fieldRef.resolvedField();
        Class clazz = field.getClazz();
        if (!clazz.isInitStarted()) {
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.thread, clazz);
            return;
        }
        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        Variables slots = clazz.getStaticVars();
        OperandStack stack = frame.operandStack;
        switch (descriptor.substring(0, 1)) {
            case "Z":
            case "B":
            case "C":
            case "S":
            case "I":
                slots.setInt(slotId, stack.popInt());
                break;
            case "F":
                slots.setFloat(slotId, stack.popFloat());
                break;
            case "J":
                slots.setLong(slotId, stack.popLong());
                break;
            case "D":
                slots.setDouble(slotId, stack.popDouble());
                break;
            case "L":
            case "[":
                slots.setRef(slotId, stack.popRef());
                break;
            default:
                break;
        }
    }
}
