package com.lgb.instructions;

import com.lgb.instructions.control.*;
import com.lgb.instructions.base.Instruction;
import com.lgb.instructions.comparisons.if_icmp.*;
import com.lgb.instructions.comparisons.if_acmp.*;
import com.lgb.instructions.comparisons.fcmpg.*;
import com.lgb.instructions.comparisons.dcmp.*;
import com.lgb.instructions.comparisons.ifcond.*;
import com.lgb.instructions.constants.*;
import com.lgb.instructions.control.returns.*;
import com.lgb.instructions.loads.*;
import com.lgb.instructions.loads.DLOAD_1;
import com.lgb.instructions.loads.DLOAD_2;
import com.lgb.instructions.math.or.*;
import com.lgb.instructions.math.and.*;
import com.lgb.instructions.math.xor.*;
import com.lgb.instructions.math.sh.*;
import com.lgb.instructions.math.add.*;
import com.lgb.instructions.math.div.*;
import com.lgb.instructions.math.mul.*;
import com.lgb.instructions.math.neg.*;
import com.lgb.instructions.math.rem.*;
import com.lgb.instructions.math.sub.*;
import com.lgb.instructions.math.IINC;
import com.lgb.instructions.references.*;
import com.lgb.instructions.stack.*;
import com.lgb.instructions.conversions.d2x.*;
import com.lgb.instructions.conversions.f2x.*;
import com.lgb.instructions.conversions.i2x.*;
import com.lgb.instructions.conversions.l2x.*;
import com.lgb.instructions.comparisons.LCMP;
import com.lgb.instructions.extended.*;
import com.lgb.instructions.stores.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Factory {
    private static Map<Byte,Instruction> opcodeMap = new HashMap<>();
    static {
        opcodeMap.put((byte) 0x00, new NOP());
        opcodeMap.put((byte) 0x01, new ACONST_NULL());
        opcodeMap.put((byte) 0x02, new ICONST_M1());
        opcodeMap.put((byte) 0x03, new ICONST_0());
        opcodeMap.put((byte) 0x04, new ICONST_1());
        opcodeMap.put((byte) 0x05, new ICONST_2());
        opcodeMap.put((byte) 0x06, new ICONST_3());
        opcodeMap.put((byte) 0x07, new ICONST_4());
        opcodeMap.put((byte) 0x08, new ICONST_5());
        opcodeMap.put((byte) 0x09, new LCONST_0());
        opcodeMap.put((byte) 0x0a, new LCONST_1());
        opcodeMap.put((byte) 0x0b, new FCONST_0());
        opcodeMap.put((byte) 0x0c, new FCONST_1());
        opcodeMap.put((byte) 0x0d, new FCONST_2());
        opcodeMap.put((byte) 0x0e, new DCONST_0());
        opcodeMap.put((byte) 0x0f, new DCONST_1());
        opcodeMap.put((byte) 0x10, new BIPUSH());
        opcodeMap.put((byte) 0x11, new SIPUSH());
        opcodeMap.put((byte) 0x12, new LDC());
        opcodeMap.put((byte) 0x13, new LDC_W());
        opcodeMap.put((byte) 0x14, new LDC2_W());
        opcodeMap.put((byte) 0x15, new ILOAD());
        opcodeMap.put((byte) 0x16, new LLOAD());
        opcodeMap.put((byte) 0x17, new FLOAD());
        opcodeMap.put((byte) 0x18, new DLOAD());
        opcodeMap.put((byte) 0x19, new ALOAD());
        opcodeMap.put((byte) 0x1a, new ILOAD_0());
        opcodeMap.put((byte) 0x1b, new ILOAD_1());
        opcodeMap.put((byte) 0x1c, new ILOAD_2());
        opcodeMap.put((byte) 0x1d, new ILOAD_3());
        opcodeMap.put((byte) 0x1e, new LLOAD_0());
        opcodeMap.put((byte) 0x1f, new LLOAD_1());
        opcodeMap.put((byte) 0x20, new LLOAD_2());
        opcodeMap.put((byte) 0x21, new LLOAD_3());
        opcodeMap.put((byte) 0x22, new FLOAD_0());
        opcodeMap.put((byte) 0x23, new FLOAD_1());
        opcodeMap.put((byte) 0x24, new FLOAD_2());
        opcodeMap.put((byte) 0x25, new FLOAD_3());
        opcodeMap.put((byte) 0x26, new DLOAD_0());
        opcodeMap.put((byte) 0x27, new DLOAD_1());
        opcodeMap.put((byte) 0x28, new DLOAD_2());
        opcodeMap.put((byte) 0x29, new DLOAD_3());
        opcodeMap.put((byte) 0x2a, new ALOAD_0());
        opcodeMap.put((byte) 0x2b, new ALOAD_1());
        opcodeMap.put((byte) 0x2c, new ALOAD_2());
        opcodeMap.put((byte) 0x2d, new ALOAD_3());
        //opcodeMap.put((byte) 0x2e:
        // 	return iaload
        //opcodeMap.put((byte) 0x2f:
        // 	return laload
        //opcodeMap.put((byte) 0x30:
        // 	return faload
        //opcodeMap.put((byte) 0x31:
        // 	return daload
        //opcodeMap.put((byte) 0x32:
        // 	return aaload
        //opcodeMap.put((byte) 0x33:
        // 	return baload
        //opcodeMap.put((byte) 0x34:
        // 	return caload
        //opcodeMap.put((byte) 0x35:
        // 	return saload
        opcodeMap.put((byte) 0x36, new ISTORE());
        opcodeMap.put((byte) 0x37, new LSTORE());
        opcodeMap.put((byte) 0x38, new FSTORE());
        opcodeMap.put((byte) 0x39, new DSTORE());
        opcodeMap.put((byte) 0x3a, new ASTORE());
        opcodeMap.put((byte) 0x3b, new ISTORE_0());
        opcodeMap.put((byte) 0x3c, new ISTORE_1());
        opcodeMap.put((byte) 0x3d, new ISTORE_2());
        opcodeMap.put((byte) 0x3e, new ISTORE_3());
        opcodeMap.put((byte) 0x3f, new LSTORE_0());
        opcodeMap.put((byte) 0x40, new LSTORE_1());
        opcodeMap.put((byte) 0x41, new LSTORE_2());
        opcodeMap.put((byte) 0x42, new LSTORE_3());
        opcodeMap.put((byte) 0x43, new FSTORE_0());
        opcodeMap.put((byte) 0x44, new FSTORE_1());
        opcodeMap.put((byte) 0x45, new FSTORE_2());
        opcodeMap.put((byte) 0x46, new FSTORE_3());
        opcodeMap.put((byte) 0x47, new DSTORE_0());
        opcodeMap.put((byte) 0x48, new DSTORE_1());
        opcodeMap.put((byte) 0x49, new DSTORE_2());
        opcodeMap.put((byte) 0x4a, new DSTORE_3());
        opcodeMap.put((byte) 0x4b, new ASTORE_0());
        opcodeMap.put((byte) 0x4c, new ASTORE_1());
        opcodeMap.put((byte) 0x4d, new ASTORE_2());
        opcodeMap.put((byte) 0x4e, new ASTORE_3());
        //opcodeMap.put((byte) 0x4f:
        // 	return iastore
        //opcodeMap.put((byte) 0x50:
        // 	return lastore
        //opcodeMap.put((byte) 0x51:
        // 	return fastore
        //opcodeMap.put((byte) 0x52:
        // 	return dastore
        //opcodeMap.put((byte) 0x53:
        // 	return aastore
        //opcodeMap.put((byte) 0x54:
        // 	return bastore
        //opcodeMap.put((byte) 0x55:
        // 	return castore
        //opcodeMap.put((byte) 0x56:
        // 	return sastore
        opcodeMap.put((byte) 0x57, new POP());
        opcodeMap.put((byte) 0x58, new POP2());
        opcodeMap.put((byte) 0x59, new DUP());
        opcodeMap.put((byte) 0x5a, new DUP_X1());
        opcodeMap.put((byte) 0x5b, new DUP_X2());
        opcodeMap.put((byte) 0x5c, new DUP2());
        opcodeMap.put((byte) 0x5d, new DUP2_X1());
        opcodeMap.put((byte) 0x5e, new DUP2_X2());
        opcodeMap.put((byte) 0x5f, new SWAP());
        opcodeMap.put((byte) 0x60, new IADD());
        opcodeMap.put((byte) 0x61, new LADD());
        opcodeMap.put((byte) 0x62, new FADD());
        opcodeMap.put((byte) 0x63, new DADD());
        opcodeMap.put((byte) 0x64, new ISUB());
        opcodeMap.put((byte) 0x65, new LSUB());
        opcodeMap.put((byte) 0x66, new FSUB());
        opcodeMap.put((byte) 0x67, new DSUB());
        opcodeMap.put((byte) 0x68, new IMUL());
        opcodeMap.put((byte) 0x69, new LMUL());
        opcodeMap.put((byte) 0x6a, new FMUL());
        opcodeMap.put((byte) 0x6b, new DMUL());
        opcodeMap.put((byte) 0x6c, new IDIV());
        opcodeMap.put((byte) 0x6d, new LDIV());
        opcodeMap.put((byte) 0x6e, new FDIV());
        opcodeMap.put((byte) 0x6f, new DDIV());
        opcodeMap.put((byte) 0x70, new IREM());
        opcodeMap.put((byte) 0x71, new LREM());
        opcodeMap.put((byte) 0x72, new FREM());
        opcodeMap.put((byte) 0x73, new DREM());
        opcodeMap.put((byte) 0x74, new INEG());
        opcodeMap.put((byte) 0x75, new LNEG());
        opcodeMap.put((byte) 0x76, new FNEG());
        opcodeMap.put((byte) 0x77, new DNEG());
        opcodeMap.put((byte) 0x78, new ISHL());
        opcodeMap.put((byte) 0x79, new LSHL());
        opcodeMap.put((byte) 0x7a, new ISHR());
        opcodeMap.put((byte) 0x7b, new LSHR());
        opcodeMap.put((byte) 0x7c, new IUSHR());
        opcodeMap.put((byte) 0x7d, new LUSHR());
        opcodeMap.put((byte) 0x7e, new IAND());
        opcodeMap.put((byte) 0x7f, new LAND());
        opcodeMap.put((byte) 0x80, new IOR());
        opcodeMap.put((byte) 0x81, new LOR());
        opcodeMap.put((byte) 0x82, new IXOR());
        opcodeMap.put((byte) 0x83, new LXOR());
        opcodeMap.put((byte) 0x84, new IINC());
        opcodeMap.put((byte) 0x85, new I2L());
        opcodeMap.put((byte) 0x86, new I2F());
        opcodeMap.put((byte) 0x87, new I2D());
        opcodeMap.put((byte) 0x88, new L2I());
        opcodeMap.put((byte) 0x89, new L2F());
        opcodeMap.put((byte) 0x8a, new L2D());
        opcodeMap.put((byte) 0x8b, new F2I());
        opcodeMap.put((byte) 0x8c, new F2L());
        opcodeMap.put((byte) 0x8d, new F2D());
        opcodeMap.put((byte) 0x8e, new D2I());
        opcodeMap.put((byte) 0x8f, new D2L());
        opcodeMap.put((byte) 0x90, new D2F());
        opcodeMap.put((byte) 0x91, new I2B());
        opcodeMap.put((byte) 0x92, new I2C());
        opcodeMap.put((byte) 0x93, new I2S());
        opcodeMap.put((byte) 0x94, new LCMP());
        opcodeMap.put((byte) 0x95, new FCMPL());
        opcodeMap.put((byte) 0x96, new FCMPG());
        opcodeMap.put((byte) 0x97, new DCMPL());
        opcodeMap.put((byte) 0x98, new DCMPG());
        opcodeMap.put((byte) 0x99, new IFEQ());
        opcodeMap.put((byte) 0x9a, new IFNE());
        opcodeMap.put((byte) 0x9b, new IFLT());
        opcodeMap.put((byte) 0x9c, new IFGE());
        opcodeMap.put((byte) 0x9d, new IFGT());
        opcodeMap.put((byte) 0x9e, new IFLE());
        opcodeMap.put((byte) 0x9f, new IF_ICMPEQ());
        opcodeMap.put((byte) 0xa0, new IF_ICMPNE());
        opcodeMap.put((byte) 0xa1, new IF_ICMPLT());
        opcodeMap.put((byte) 0xa2, new IF_ICMPGE());
        opcodeMap.put((byte) 0xa3, new IF_ICMPGT());
        opcodeMap.put((byte) 0xa4, new IF_ICMPLE());
        opcodeMap.put((byte) 0xa5, new IF_ACMPEQ());
        opcodeMap.put((byte) 0xa6, new IF_ACMPNE());
        opcodeMap.put((byte) 0xa7, new GOTO());
        //opcodeMap.put((byte) 0xa8:
        // 	return &JSR{}
        //opcodeMap.put((byte) 0xa9:
        // 	return &RET{}
        opcodeMap.put((byte) 0xaa, new TABLE_SWITCH());
        opcodeMap.put((byte) 0xab, new LOOKUP_SWITCH());
        opcodeMap.put((byte) 0xac, new IRETURN());
        opcodeMap.put((byte) 0xad, new LRETURN());
        opcodeMap.put((byte) 0xae, new FRETURN());
        opcodeMap.put((byte) 0xaf, new DRETURN());
        opcodeMap.put((byte) 0xb0, new ARETURN());
        opcodeMap.put((byte) 0xb1, new RETURN());
        opcodeMap.put((byte) 0xb2, new GET_STATIC());
        opcodeMap.put((byte) 0xb3, new PUT_STATIC());
        opcodeMap.put((byte) 0xb4, new GET_FIELD());
        opcodeMap.put((byte) 0xb5, new PUT_FIELD());
        opcodeMap.put((byte) 0xb6, new INVOKE_VIRTUAL());
        opcodeMap.put((byte) 0xb7, new INVOKE_SPECIAL());
        opcodeMap.put((byte) 0xb8, new INVOKE_STATIC());
        opcodeMap.put((byte) 0xb9, new INVOKE_INTERFACE());
        //opcodeMap.put((byte) 0xba:
        // 	return &INVOKE_DYNAMIC{}
        opcodeMap.put((byte) 0xbb, new NEW());
        //opcodeMap.put((byte) 0xbc:
        // 	return &NEW_ARRAY{}
        //opcodeMap.put((byte) 0xbd:
        // 	return &ANEW_ARRAY{}
        //opcodeMap.put((byte) 0xbe:
        // 	return arraylength
        //opcodeMap.put((byte) 0xbf:
        // 	return athrow
        opcodeMap.put((byte) 0xc0, new CHECK_CAST());
        opcodeMap.put((byte) 0xc1, new INSTANCE_OF());
        //opcodeMap.put((byte) 0xc2:
        // 	return monitorenter
        //opcodeMap.put((byte) 0xc3:
        // 	return monitorexit
        opcodeMap.put((byte) 0xc4, new WIDE());
        //opcodeMap.put((byte) 0xc5:
        // 	return &MULTI_ANEW_ARRAY{}
        opcodeMap.put((byte) 0xc6, new IFNULL());
        opcodeMap.put((byte) 0xc7, new IFNONNULL());
        opcodeMap.put((byte) 0xc8, new GOTO_W());
        //opcodeMap.put((byte) 0xc9:
        // 	return &JSR_W{}
        //opcodeMap.put((byte) 0xca: breakpoint
        //opcodeMap.put((byte) 0xfe: impdep1
        //opcodeMap.put((byte) 0xff: impdep2
    }
    public static Instruction newInstruction(byte opcode) {
        Instruction inst = null;
        if(Objects.nonNull(inst = opcodeMap.get(opcode))){
            return inst;
        }
        throw new RuntimeException(String.format("Unsupported opcode: %s", Integer.toHexString(opcode)));
    }
}
