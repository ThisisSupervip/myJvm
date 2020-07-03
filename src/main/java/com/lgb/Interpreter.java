package com.lgb;

import com.lgb.instructions.Factory;
import com.lgb.instructions.base.BytecodeReader;
import com.lgb.instructions.base.Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.Memory;
import com.lgb.rtda.Thread;
import com.lgb.rtda.heap.StringPool;
import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;
import com.lgb.util.ByteUtil;

import java.util.Arrays;
import java.util.Objects;

public class Interpreter {
    static boolean log = false;
    public static void interpret(Method method, String[] args) {
        Thread thread = new Thread();
        Frame frame = thread.newFrame(method);
        thread.pushFrame(frame);

        if (null != args){
            Object jArgs = createArgsArray(method.getClazz().getClassloader(), args);
            frame.localVariables.setRef(0, jArgs);
        }
        loop(thread, log);
    }

    public static void loop(Thread thread, boolean log) {
        BytecodeReader reader = new BytecodeReader();
        Frame lastExeFrame = null;
        while (true) {
            Frame frame = thread.currentFrame();
            if (log) {
                if (Objects.nonNull(lastExeFrame) && !lastExeFrame.equals(frame)) {
                    System.out.println("New frame info:");
                    printFrameInfo(frame);
                }
            }
            int pc = frame.nextPC();
            thread.setPC(pc);

            //decode
            reader.reset(frame.method.getCode(), pc);
            byte opcode = readOpcode(reader);
            Instruction inst = Factory.newInstruction(opcode);
            inst.fetchOperands(reader);
            frame.setNextPC(reader.getPc());

            //execute
            inst.execute(frame);
            lastExeFrame = frame;

            if (log) {
                System.out.printf("pc:%2d inst:%s\n", pc, inst);
                printFrameInfo(frame);
            }
            if (thread.isStackEmpty()) {
                break;
            }
            /*try {
                java.lang.Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

    }

    private static Object createArgsArray(Classloader loader, String[] args) {
        Class stringClass = loader.loadClass("java/lang/String");
        Object argsArr = stringClass.arrayClass().newArray(args.length);
        Object[] jArgs = argsArr.refs();
        for (int i = 0; i < jArgs.length; i++) {
            jArgs[i] = StringPool.jString(loader, args[i]);
        }
        return argsArr;
    }

    private static byte readOpcode(BytecodeReader reader) {
        return reader.readInt8();
    }

    private static void printFrameInfo(Frame frame) {
        System.out.printf("frame: %s\n", frame.method.getClazz().getName() + "/" + frame.method.getName());
        String localVarHax = ByteUtil.hexDump(frame.localVariables.byteArray());
        String operandStackHax = ByteUtil.hexDump(frame.operandStack.byteArray());
        System.out.printf("localVarHax: %s", localVarHax);
        System.out.printf("operandStackHax: %s", operandStackHax);
        System.out.printf("Objects: %s\n\n", Arrays.toString(Memory.objects.toArray()));
    }

}
