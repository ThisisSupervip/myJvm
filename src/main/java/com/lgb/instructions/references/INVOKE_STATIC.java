package com.lgb.instructions.references;

import com.lgb.instructions.base.ClassInitLogic;
import com.lgb.instructions.base.Index16Instruction;
import com.lgb.instructions.base.MethodInvokeLogic;
import com.lgb.rtda.Frame;
import com.lgb.rtda.heap.constantPool.ConstantPool;
import com.lgb.rtda.heap.constantPool.MethodRef;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;

public class INVOKE_STATIC extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.getConstantPool();
        MethodRef methodRef = (MethodRef) constantPool.getConstant(this.index);
        Method resolvedMethod = methodRef.resolveMethod();

        //hack!
        //initializeSystemClass执行完setIn0、setOut0、setErr0忽略剩余指令，跳转到pc:140对应的return
        if(frame.method.getName().equals("initializeSystemClass")
                &&resolvedMethod.getClazz().getName().contains("System")
                &&resolvedMethod.getName().equals("loadLibrary")){
            frame.setNextPC(140);
            return;
        }
        if(!resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        Class clazz = resolvedMethod.getClazz();
        if (!clazz.isInitStarted()) {
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.thread, clazz);
            return;
        }
        MethodInvokeLogic.invokeMethod(frame, resolvedMethod);
    }
}
