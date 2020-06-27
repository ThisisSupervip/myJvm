package com.lgb.instructions.references;

import com.lgb.instructions.base.Index16Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.Variables;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.constantPool.FieldRef;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Field;
import com.lgb.rtda.heap.methodarea.Slots;

public class GET_STATIC extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.method.getClazz().getConstantPool();
        FieldRef ref = (FieldRef) constantPool.getConstant(this.index);
        Field field = ref.resolvedField();
        if (!field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        Class clazz = field.getClazz();
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
                stack.pushInt(slots.getInt(slotId));
                break;
            case "F":
                stack.pushFloat(slots.getFloat(slotId));
                break;
            case "J":
                stack.pushLong(slots.getLong(slotId));
                break;
            case "D":
                stack.pushDouble(slots.getDouble(slotId));
                break;
            case "L":
            case "[":
                stack.pushRef(slots.getRef(slotId));
                break;
            default:
                break;
        }
    }
}
