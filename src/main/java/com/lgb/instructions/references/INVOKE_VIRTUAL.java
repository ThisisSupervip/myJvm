package com.lgb.instructions.references;

import com.lgb.instructions.base.Index16Instruction;
import com.lgb.instructions.base.MethodInvokeLogic;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.MethodLookup;
import com.lgb.rtda.heap.StringPool;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.constantPool.MethodRef;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;

public class INVOKE_VIRTUAL extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        Class currentClass = frame.method.getClazz();
        ConstantPool constantPool = currentClass.getConstantPool();
        MethodRef methodRef = (MethodRef) constantPool.getConstant(this.index);
        Method resolvedMethod = methodRef.resolveMethod();
        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }

        Object ref = frame.operandStack.getRefFromTop(resolvedMethod.argSlotCount - 1);

        if (resolvedMethod.isProtected() &&
                resolvedMethod.getClazz().isSuperClassOf(currentClass) &&
                !resolvedMethod.getClazz().getPackageName().equals(currentClass.getPackageName()) &&
                ref.clazz != currentClass &&
                !ref.clazz.isSubClassOf(currentClass)) {
            if(!(ref.clazz.isArray()&&resolvedMethod.getName().equals("clone"))){
                throw new IllegalAccessError();
            }
        }
        Method methodToBeInvoked = null;

        try {

            methodToBeInvoked = MethodLookup.lookupMethodInClass(ref.clazz,
                    methodRef.getName(), methodRef.getDescriptor());
            if (null == methodToBeInvoked || methodToBeInvoked.isAbstract()) {
                throw new AbstractMethodError();
            }
        } catch (Exception e){
            System.out.println(methodRef);
            System.out.println(methodRef.getName());
            throw e;
        }

        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }

}
