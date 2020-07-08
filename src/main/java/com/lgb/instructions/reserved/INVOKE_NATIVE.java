package com.lgb.instructions.reserved;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.heap.methodarea.Method;

import java.util.Objects;

public class INVOKE_NATIVE extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        Method method = frame.method;
        String className = method.getClazz().getName();
        String methodName = method.getName();
        String methodDescriptor = method.getDescriptor();
        NativeMethod nativeMethod = Registry.findNativeMethod(className, methodName, methodDescriptor);
        if(Objects.isNull(nativeMethod)) {
            String methodInfo = className + "." + methodName + methodDescriptor;
            throw new UnsatisfiedLinkError(methodInfo);
        }

        nativeMethod.accept(frame);
    }
}
