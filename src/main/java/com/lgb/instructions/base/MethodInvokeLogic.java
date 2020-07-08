package com.lgb.instructions.base;

import com.lgb.rtda.Frame;
import com.lgb.rtda.Thread;
import com.lgb.rtda.heap.methodarea.Method;

public class MethodInvokeLogic {

    public static void invokeMethod(Frame invokeFrame, Method method) {
        Thread thread = invokeFrame.thread;
        Frame newFrame = new Frame(thread, method);
        thread.pushFrame(newFrame);

        int argSlotCount = method.argSlotCount;
        if(argSlotCount>0) {
            for (int i = argSlotCount - 1; i >= 0 ; i--) {
               int slot = invokeFrame.operandStack.popInt();
               newFrame.localVariables.setInt(i, slot);
            }
        }

    }
}
