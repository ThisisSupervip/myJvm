package com.lgb.instructions.references;

import com.lgb.instructions.base.Index16Instruction;
import com.lgb.instructions.base.MethodInvokeLogic;
import com.lgb.rtda.Frame;
import com.lgb.rtda.heap.MethodLookup;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.constantPool.MethodRef;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;

public class INVOKE_SPECIAL extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        Class currentClass = frame.method.getClazz();
        ConstantPool constantPool = frame.getConstantPool();
        MethodRef methodRef = (MethodRef) constantPool.getConstant(this.index);
        Class resolvedClass = methodRef.resolvedClass();
        Method resolvedMethod = methodRef.resolveMethod();
        if ("<init>".equals(resolvedMethod.getName()) && resolvedMethod.getClazz() != resolvedClass) {
            throw new NoSuchMethodError();
        }
        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }

        Object ref = frame.operandStack.getRefFromTop(resolvedMethod.argSlotCount - 1);
        if (null == ref) {
            throw new NullPointerException();
        }

        if (resolvedMethod.isProtected() &&
                resolvedMethod.getClazz().isSubClassOf(currentClass) &&
                !resolvedMethod.getClazz().getPackageName().equals(currentClass.getPackageName()) &&
                ref.clazz != currentClass &&
                !ref.clazz.isSubClassOf(currentClass)) {
            throw new IllegalAccessError();
        }

        Method methodToBeInvoked = resolvedMethod;
        if (currentClass.isSuper() &&
                resolvedClass.isSubClassOf(currentClass) &&
                !"<init>".equals(resolvedMethod.getName())) {
            MethodLookup.lookupMethodInClass(currentClass.getSuperClass(), methodRef.getName(), methodRef.getDescriptor());
        }

        if (methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError();
        }

        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }
}
