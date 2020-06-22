package com.lgb;

import com.lgb.classfile.fundamental.AttributeInfoType;
import com.lgb.classfile.fundamental.MemberInfo;
import com.lgb.instructions.Factory;
import com.lgb.instructions.base.BytecodeReader;
import com.lgb.instructions.base.Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.Thread;

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
            inst.fetchOperands(reader);
            frame.setNextPC(reader.PC());

            //execute
            System.out.printf("pc:%2d inst:%s\n", pc, inst.getClass().getSimpleName());
            inst.execute(frame);
            /*try {
                java.lang.Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

}
