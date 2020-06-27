package com.lgb.instructions.references;

import com.lgb.instructions.base.Index16Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.Variables;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.constantPool.FieldRef;
import com.lgb.rtda.heap.methodarea.Field;
import com.lgb.rtda.heap.methodarea.Object;
import com.lgb.rtda.heap.methodarea.Slots;

public class GET_FIELD extends Index16Instruction {
    private Object ref;

    @Override
    public void execute(Frame frame) {
        ConstantPool runTimeConstantPool = frame.method.getClazz().getConstantPool();
        FieldRef fieldRef = (FieldRef) runTimeConstantPool.getConstant(this.index);
        Field field = fieldRef.resolvedField();

        OperandStack stack = frame.operandStack;
        Object ref = stack.popRef();
        if (null == ref) {
            throw new NullPointerException();
        }
        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        Variables slots = ref.fields;

        switch (descriptor.substring(0, 1)) {
            case "Z":
            case "B":
            case "C":
            case "S":
            case "I":
                stack.pushInt(ref.fields.getInt(slotId));
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
