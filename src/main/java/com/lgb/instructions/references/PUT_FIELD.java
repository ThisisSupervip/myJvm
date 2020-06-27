package com.lgb.instructions.references;

import com.lgb.instructions.base.Index16Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.constantPool.FieldRef;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Field;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;

public class PUT_FIELD extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        Method currentMethod = frame.method;
        Class currentClazz = currentMethod.getClazz();
        ConstantPool runTimeConstantPool = currentClazz.getConstantPool();
        FieldRef fieldRef = (FieldRef) runTimeConstantPool.getConstant(this.index);
        Field field = fieldRef.resolvedField();
        if (field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }

        if (field.isFinal()) {
            throw new IllegalAccessError();
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        OperandStack stack = frame.operandStack;
        switch (descriptor.substring(0, 1)) {
            case "Z":
            case "B":
            case "C":
            case "S":
            case "I": {
                int val = stack.popInt();
                Object ref = stack.popRef();
                if (null == ref) {
                    throw new NullPointerException();
                }
                ref.fields.setInt(slotId, val);
                break;
            }
            case "F": {
                float val = stack.popFloat();
                Object ref = stack.popRef();
                if (null == ref) {
                    throw new NullPointerException();
                }
                ref.fields.setFloat(slotId, val);
                break;
            }
            case "J": {
                long val = stack.popLong();
                Object ref = stack.popRef();
                if (null == ref) {
                    throw new NullPointerException();
                }
                ref.fields.setLong(slotId, val);
                break;
            }
            case "D": {
                double val = stack.popDouble();
                Object ref = stack.popRef();
                if (null == ref) {
                    throw new NullPointerException();
                }
                ref.fields.setDouble(slotId, val);
                break;
            }
            case "L":
            case "[": {
                Object val = stack.popRef();
                Object ref = stack.popRef();
                if (null == ref) {
                    throw new NullPointerException();
                }
                ref.fields.setRef(slotId, val);
                break;
            }
            default:
                break;
        }
    }
}
