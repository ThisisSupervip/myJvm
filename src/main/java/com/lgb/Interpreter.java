package com.lgb;

import com.lgb.classfile.fundamental.AttributeInfoType;
import com.lgb.classfile.fundamental.MemberInfo;
import com.lgb.instructions.Factory;
import com.lgb.instructions.base.BranchInstruct;
import com.lgb.instructions.base.BranchInstruct1;
import com.lgb.instructions.base.BytecodeReader;
import com.lgb.instructions.base.Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.Thread;
import com.lgb.util.ByteUtil;

public class Interpreter {
    public static void interpret(MemberInfo memberInfo){
        AttributeInfoType.CodeAttribute codeAttr = memberInfo.codeAttribute();
        int maxLocals = codeAttr.maxLocals.intValue;
        int maxStack = codeAttr.maxStack.intValue;
        byte[] bytecode = codeAttr.code;
        Thread thread = new Thread();
        Frame frame = thread.newFrame(maxLocals, maxStack);
        thread.pushFrame(frame);
        loop(thread, bytecode);
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
            System.out.printf("operandStackHax: %s\n", operandStackHax);
            /*try {
                java.lang.Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

    }


}
