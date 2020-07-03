package com.lgb.instructions.references;

import com.lgb.instructions.base.BytecodeReader;
import com.lgb.instructions.base.Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Object;

public class NEW_ARRAY implements Instruction {

    private byte atype;
    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.atype = reader.readInt8();
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int count = stack.popInt();
        if(count<0){
            throw new NegativeArraySizeException();
        }
        Classloader classloader = frame.method.getClazz().getClassloader();
        Class arrClass = getPrimitiveArrayClass(classloader, atype);
        Object arr = arrClass.newArray(count);
        stack.pushRef(arr);
    }

    private Class getPrimitiveArrayClass(Classloader classloader, byte atype) {
        switch (atype) {
            case ArrayType.AT_BOOLEAN:
                return classloader.loadClass("[Z");
            case ArrayType.AT_BYTE:
                return classloader.loadClass("[B");
            case ArrayType.AT_CHAR:
                return classloader.loadClass("[C");
            case ArrayType.AT_SHORT:
                return classloader.loadClass("[S");
            case ArrayType.AT_INT:
                return classloader.loadClass("[I");
            case ArrayType.AT_LONG:
                return classloader.loadClass("[J");
            case ArrayType.AT_FLOAT:
                return classloader.loadClass("[F");
            case ArrayType.AT_DOUBLE:
                return classloader.loadClass("[D");
            default:
                throw new RuntimeException("Invalid atype!");
        }
    }

    static class ArrayType {
        static final byte AT_BOOLEAN = 4;
        static final byte AT_CHAR = 5;
        static final byte AT_FLOAT = 6;
        static final byte AT_DOUBLE = 7;
        static final byte AT_BYTE = 8;
        static final byte AT_SHORT = 9;
        static final byte AT_INT = 10;
        static final byte AT_LONG = 11;
    }

    @Override
    public String toString() {
        return "NEW_ARRAY{" +
                "atype=" + atype +
                '}';
    }
}
