package com.lgb.instructions.references;


import com.lgb.instructions.base.BytecodeReader;
import com.lgb.instructions.base.Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.constantPool.ClassRef;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Object;

import java.util.Arrays;

public class MULTI_ANEW_ARRAY implements Instruction {

    private int index;
    private byte dimensions;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readUint16().intValue;
        this.dimensions = reader.readByte();
    }

    @Override
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.method.getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) constantPool.getConstant(this.index);
        Class arrClass = classRef.resolvedClass();

        OperandStack stack = frame.operandStack;
        int[] counts = popAndCheckCounts(stack, this.dimensions);
        Object arr = newMultiDimensionalArray(counts, arrClass);
        stack.pushRef(arr);

    }

    private int[] popAndCheckCounts(OperandStack stack, int dimensions) {
        int[] counts = new int[dimensions];
        for (int i = dimensions - 1; i >= 0; i--) {
            counts[i] = stack.popInt();
            if (counts[i] < 0) {
                throw new NegativeArraySizeException();
            }
        }

        return counts;
    }

    private Object newMultiDimensionalArray(int[] counts, Class arrClass) {
        int count = counts[0];
        Object arr = arrClass.newArray(count);
        if (counts.length > 1) {
            Object[] refs = arr.refs();
            for (int i = 0; i < refs.length; i++) {
                refs[i] = newMultiDimensionalArray(
                        Arrays.copyOfRange(counts, 1, counts.length - 1),
                        arrClass.componentClass());
            }
        }

        return arr;
    }

    @Override
    public String toString() {
        return "MULTI_ANEW_ARRAY{" +
                "index=" + index +
                ", dimensions=" + dimensions +
                '}';
    }
}
