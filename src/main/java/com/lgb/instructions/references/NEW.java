package com.lgb.instructions.references;

import com.lgb.instructions.base.ClassInitLogic;
import com.lgb.instructions.base.Index16Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.heap.constantPool.ClassRef;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Object;

public class NEW extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.method.getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) constantPool.getConstant(this.index);
        Class clazz = classRef.resolvedClass();
        if(!clazz.isInitStarted()) {
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.thread, clazz);
            return;
        }
        if(clazz.isInterface()||clazz.isAbstract()){
            throw new InstantiationError();
        }
        Object object = clazz.newObject();
        frame.operandStack.pushRef(object);
    }
}
