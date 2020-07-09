package com.lgb.rtda.heap.methodarea;

import com.lgb.classfile.fundamental.AttributeInfoType;
import com.lgb.rtda.heap.constantPool.ClassRef;
import com.lgb.rtda.heap.constantPool.ConstantPool;

public class ExceptionTable {

    private ExceptionHandler[] exceptionTable;

    public ExceptionTable(AttributeInfoType.ExceptionTableEntry[] entries, ConstantPool constantPool) {
        exceptionTable = new ExceptionHandler[entries.length];
        for (int i = 0; i < entries.length; i++) {
            AttributeInfoType.ExceptionTableEntry entry = entries[i];
            exceptionTable[i] = new ExceptionHandler(entry, constantPool);
        }
    }

    public ExceptionHandler findExceptionHandler(Class exClass, int pc) {
        for (ExceptionHandler handler : exceptionTable) {
            if (pc >= handler.startPC && pc < handler.endPC) {
                if (null == handler.catchType) {
                    return handler;
                }
                Class catchClass = handler.catchType.resolvedClass();
                if (catchClass == exClass || catchClass.isSuperClassOf(exClass)) {
                    return handler;
                }
            }
        }
        return null;
    }

    class ExceptionHandler {
        protected int startPC;
        protected int endPC;
        protected int handlerPC;
        protected ClassRef catchType;

        public ExceptionHandler(AttributeInfoType.ExceptionTableEntry entry, ConstantPool constantPool) {
            this.startPC = entry.startPc.intValue;
            this.endPC = entry.endPc.intValue;
            this.handlerPC = entry.handlerPc.intValue;
            int eTypeIndex = entry.catchType.intValue;
            this.catchType = getCatchType(eTypeIndex, constantPool);
        }

        public ClassRef getCatchType(int index, ConstantPool constantPool) {
            if (index == 0) return null;
            return (ClassRef) constantPool.getConstant(index);
        }
    }
}
