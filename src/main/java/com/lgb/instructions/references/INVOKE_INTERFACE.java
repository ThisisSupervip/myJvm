package com.lgb.instructions.references;

import com.lgb.instructions.base.BytecodeReader;
import com.lgb.instructions.base.Instruction;
import com.lgb.instructions.base.MethodInvokeLogic;
import com.lgb.rtda.Frame;
import com.lgb.rtda.heap.MethodLookup;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.constantPool.InterfaceMethodRef;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;

public class INVOKE_INTERFACE implements Instruction {

    private int index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readUint16().intValue;
        reader.readByte();
        reader.readByte();
    }

    @Override
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.getConstantPool();
        InterfaceMethodRef methodRef = (InterfaceMethodRef) constantPool.getConstant(this.index);
        Method resolvedMethod = methodRef.resolveInterfaceMethod();
        if (resolvedMethod.isStatic() || resolvedMethod.isPrivate()) {
            throw new IncompatibleClassChangeError();
        }
        Object ref = frame.operandStack.getRefFromTop(resolvedMethod.argSlotCount - 1);
        if (null == ref) {
            throw new NullPointerException();
        }
        if (!ref.clazz.isImplements(methodRef.resolvedClass())) {
            throw new IncompatibleClassChangeError();
        }
        Method methodToBeInvoked = MethodLookup.lookupMethodInClass(ref.clazz, methodRef.getName(), methodRef.getDescriptor());
        if (null == methodToBeInvoked || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError();
        }
        if (!methodToBeInvoked.isPublic()) {
            throw new IllegalAccessError();
        }

        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }

    @Override
    public String toString() {
        return "INVOKE_INTERFACE{" +
                "index=" + index +
                '}';
    }
}
