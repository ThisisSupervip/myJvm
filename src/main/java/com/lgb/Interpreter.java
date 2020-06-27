package com.lgb;

import com.lgb.classfile.fundamental.AttributeInfoType;
import com.lgb.classfile.fundamental.MemberInfo;
import com.lgb.instructions.Factory;
import com.lgb.instructions.base.BranchInstruct;
import com.lgb.instructions.base.BranchInstruct1;
import com.lgb.instructions.base.BytecodeReader;
import com.lgb.instructions.base.Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.Memory;
import com.lgb.rtda.Thread;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;
import com.lgb.util.ByteUtil;

import java.util.Arrays;
import java.util.Objects;

public class Interpreter {
    public static void interpret(Method method){
        Thread thread = new Thread();
        Frame frame = thread.newFrame(method);
        thread.pushFrame(frame);
        loop(thread, method.code);
    }

    public static void loop(Thread thread, byte[] bytecode) {
        Frame frame = thread.popFrame();
        BytecodeReader reader = new BytecodeReader();
        while (true){
            int pc = frame.nextPC();
            thread.setPC(pc);

            //decode
            reader.reset(bytecode, pc);
            byte opcode = reader.readInt8();
            Instruction inst = Factory.newInstruction(opcode);
            if(inst instanceof BranchInstruct){
                inst = new BranchInstruct1((BranchInstruct) inst);
            }
            inst.fetchOperands(reader);
            frame.setNextPC(reader.PC());

            //execute
            inst.execute(frame);
            System.out.printf("pc:%2d inst:%s\n", pc, inst);
            String localVarHax = ByteUtil.hexDump(frame.localVariables.byteArray());
            String operandStackHax = ByteUtil.hexDump(frame.operandStack.byteArray());
            System.out.printf("localVarHax: %s", localVarHax);
            System.out.printf("operandStackHax: %s", operandStackHax);
            System.out.printf("Objects: %s\n", Arrays.toString(Memory.objects.toArray()));
            /*try {
                java.lang.Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

    }

}