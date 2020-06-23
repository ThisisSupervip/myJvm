package com.lgb.instructions.extended;

import com.lgb.instructions.base.BytecodeReader;
import com.lgb.instructions.base.Instruction;
import com.lgb.instructions.loads.*;
import com.lgb.instructions.math.IINC;
import com.lgb.instructions.stores.*;
import com.lgb.rtda.Frame;

public class WIDE implements Instruction {

    Instruction modifiedInstruction;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        int opcode = reader.readUint8().toShort();
        switch (opcode) {
            case 0x15: {
                ILOAD inst = new ILOAD();
                int idx = reader.readUint16().intValue;
                inst.setIndex(idx);
                this.modifiedInstruction = inst;
            }
            case 0x16: {
                LLOAD inst = new LLOAD();
                int idx = reader.readUint16().intValue;
                inst.setIndex(idx);
                this.modifiedInstruction = inst;
            }
            case 0x17: {
                FLOAD inst = new FLOAD();
                int idx = reader.readUint16().intValue;
                inst.setIndex(idx);
                this.modifiedInstruction = inst;
            }
            case 0x18: {
                DLOAD inst = new DLOAD();
                int idx = reader.readUint16().intValue;
                inst.setIndex(idx);
                this.modifiedInstruction = inst;
            }
            case 0x19: {
                ALOAD inst = new ALOAD();
                int idx = reader.readUint16().intValue;
                inst.setIndex(idx);
                this.modifiedInstruction = inst;
            }
            case 0x36: {
                ISTORE inst = new ISTORE();
                int idx = reader.readUint16().intValue;
                inst.setIndex(idx);
                this.modifiedInstruction = inst;
            }
            case 0x37: {
                LSTORE inst = new LSTORE();
                int idx = reader.readUint16().intValue;
                inst.setIndex(idx);
                this.modifiedInstruction = inst;
            }
            case 0x38: {
                FSTORE inst = new FSTORE();
                int idx = reader.readUint16().intValue;
                inst.setIndex(idx);
                this.modifiedInstruction = inst;
            }
            case 0x39: {
                DSTORE inst = new DSTORE();
                int idx = reader.readUint16().intValue;
                inst.setIndex(idx);
                this.modifiedInstruction = inst;
            }
            case 0x3a: {
                ASTORE inst = new ASTORE();
                int idx = reader.readUint16().intValue;
                inst.setIndex(idx);
                this.modifiedInstruction = inst;
            }
            case 0x84: {
                IINC inst = new IINC();
                int idx = reader.readUint16().intValue;
                int constVal = reader.readInt16();
                inst.setIndex(idx);
                inst.setConst(constVal);
                this.modifiedInstruction = inst;
            }
        }
    }

    @Override
    public void execute(Frame frame) {
        this.modifiedInstruction.execute(frame);
    }

    @Override
    public String toString() {
        return "WIDE{" +
                "modifiedInstruction=" + modifiedInstruction +
                '}';
    }
}
