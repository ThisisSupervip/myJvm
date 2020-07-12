package com.lgb.instructions.references;

import com.lgb._native.java.lang._Throwable;
import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.Thread;
import com.lgb.rtda.heap.StringPool;
import com.lgb.rtda.heap.methodarea.Object;

public class ATHROW extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        Object ex = frame.operandStack.popRef();
        if (ex == null) {
            throw new NullPointerException();
        }

        Thread thread = frame.thread;
        //hack 绕过AtomicReferenceFieldUpdaterImpl调用ensureMemberAccess抛出异常
        if(frame.method.getName().equals("ensureMemberAccess")&&frame.method.getClazz().getName().equals("sun/reflect/Reflection")){
            //return
            frame.setNextPC(85);
            return;
        }
        if (!findAndGotoExceptionHandler(thread, ex)) {
            handleUncaughtException(thread, ex);
        }
    }

    private boolean findAndGotoExceptionHandler(Thread thread, Object ex) {
        do {
            Frame frame = thread.currentFrame();
            int pc = frame.nextPC() - 1;

            int handlerPc = frame.method.findExceptionHandler(ex.clazz, pc);
            if (handlerPc > 0) {
                OperandStack stack = frame.operandStack;
                stack.clear();
                stack.pushRef(ex);
                frame.setNextPC(handlerPc);
                return true;
            }

            thread.popFrame();
        } while (!thread.isStackEmpty());
        return false;
    }

    private void handleUncaughtException(Thread thread, Object ex) {
        thread.clearStack();

        Object jMsg = ex.getRefVar("detailMessage", "Ljava/lang/String;");

        String msg = StringPool.goString(jMsg);
        //if(!msg.contains("java.lang.System")) {
            System.out.println("\u001B[31m" + "MyJVM execute Exception in thread \"main\" " + ex.clazz.javaName() + ": " + msg);
            java.lang.Object extra = ex.getExtra();

            _Throwable[] throwables = (_Throwable[]) extra;
            for (_Throwable t : throwables) {
                System.out.println("\u001B[31m" + "\tat " + t);
            }
        //}

    }

}
