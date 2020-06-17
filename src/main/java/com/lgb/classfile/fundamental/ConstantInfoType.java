package com.lgb.classfile.fundamental;

import com.lgb.classfile.ClassReader;
import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;

import java.util.LinkedList;
import java.util.List;

public class ConstantInfoType {

    //see https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.4

    public static final int CONSTANT_Class = 7;
    public static final int CONSTANT_Fieldref = 9;
    public static final int CONSTANT_Methodref = 10;
    public static final int CONSTANT_InterfaceMethodref = 11;
    public static final int CONSTANT_String = 8;
    public static final int CONSTANT_Integer = 3;
    public static final int CONSTANT_Float = 4;
    public static final int CONSTANT_Long = 5;
    public static final int CONSTANT_Double = 6;
    public static final int CONSTANT_NameAndType = 12;
    public static final int CONSTANT_Utf8 = 1;
    public static final int CONSTANT_MethodHandle = 15;
    public static final int CONSTANT_MethodType = 16;
    public static final int CONSTANT_InvokeDynamic = 18;

    public static class ConstantIntegerInfo implements ConstantInfo{
        public final U4 bytes;

        public ConstantIntegerInfo(U4 bytes) {
            this.bytes = bytes;
        }
    }

    public static class ConstantFloatInfo implements ConstantInfo{

        public final U4 bytes;

        public ConstantFloatInfo(U4 bytes) {
            this.bytes = bytes;
        }
    }

    public static class ConstantLongInfo implements ConstantInfo{

        public final U4 highBytes;
        public final U4 lowBytes;

        public ConstantLongInfo(U4 highBytes, U4 lowBytes) {
            this.highBytes = highBytes;
            this.lowBytes = lowBytes;
        }
    }

    public static class ConstantDoubleInfo implements ConstantInfo{

        public final U4 highBytes;
        public final U4 lowBytes;

        public ConstantDoubleInfo(U4 highBytes, U4 lowBytes) {
            this.highBytes = highBytes;
            this.lowBytes = lowBytes;
        }
    }

    public static class ConstantUtf8Info implements ConstantInfo{

        public final U2 length;
        public final byte[] bytes;
        public String val;

        public ConstantUtf8Info(U2 length, byte[] bytes) {
            this.length = length;
            this.bytes = bytes;
            this.val = new String(bytes);
        }
    }

    public static class ConstantStringInfo implements ConstantInfo{
        public final int stringIndex;

        public ConstantStringInfo(int stringIndex) {
            this.stringIndex = stringIndex;
        }
    }

    public static class ConstantClassInfo implements ConstantInfo{
        public final int nameIndex;

        public ConstantClassInfo(int nameIndex) {
            this.nameIndex = nameIndex;
        }
    }

    public static class ConstantMemberRefInfo implements ConstantInfo{
        public final int classIndex;
        public final int nameAndTypeIndex;

        public ConstantMemberRefInfo(int classIndex, int nameAndTypeIndex) {
            this.classIndex = classIndex;
            this.nameAndTypeIndex = nameAndTypeIndex;
        }
    }

    public static class ConstantFieldRefInfo extends ConstantMemberRefInfo{
        public ConstantFieldRefInfo(int classIndex, int nameAndTypeIndex) {
            super(classIndex, nameAndTypeIndex);
        }
    }

    public static class ConstantMethodRefInfo extends ConstantMemberRefInfo{
        public ConstantMethodRefInfo(int classIndex, int nameAndTypeIndex) {
            super(classIndex, nameAndTypeIndex);
        }
    }

    public static class ConstantInterfaceMethodRefInfo extends ConstantMemberRefInfo{
        public ConstantInterfaceMethodRefInfo(int classIndex, int nameAndTypeIndex) {
            super(classIndex, nameAndTypeIndex);
        }
    }

    public static class ConstantNameAndTypeInfo implements ConstantInfo{
        public final int nameIndex;
        public final int descriptorIndex;

        public ConstantNameAndTypeInfo(int nameIndex, int descriptorIndex) {
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
        }
    }

    public static class ConstantMethodTypeInfo implements ConstantInfo{
        public final int descriptorIndex;

        public ConstantMethodTypeInfo(int descriptorIndex) {
            this.descriptorIndex = descriptorIndex;
        }
    }

    public static class ConstantMethodHandleInfo implements ConstantInfo{
        public final U1 referenceKind;
        public final int referenceIndex;

        public ConstantMethodHandleInfo(U1 referenceKind, int referenceIndex) {
            this.referenceKind = referenceKind;
            this.referenceIndex = referenceIndex;
        }
    }

    public static class ConstantInvokeDynamicInfo implements ConstantInfo{
        public final int bootstrapMethodAttrIndex;
        public final int nameAndTypeIndex;

        public ConstantInvokeDynamicInfo(int bootstrapMethodAttrIndex, int nameAndTypeIndex) {
            this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
            this.nameAndTypeIndex = nameAndTypeIndex;
        }
    }

    private static ConstantInfo processConstantInfo(int tag, ClassReader classReader){
        switch (tag){
            case CONSTANT_Integer:
                return new ConstantIntegerInfo(classReader.readU4());
            case CONSTANT_Float:
                return new ConstantFloatInfo(classReader.readU4());
            case CONSTANT_Long:
                return new ConstantLongInfo(classReader.readU4(), classReader.readU4());
            case CONSTANT_Double:
                return new ConstantDoubleInfo(classReader.readU4(), classReader.readU4());
            case CONSTANT_Utf8:
                U2 lengthByte = classReader.readU2();
                int length = lengthByte.toInt();
                return new ConstantUtf8Info(lengthByte, classReader.readBytes(length));
            case CONSTANT_String:
                return new ConstantStringInfo(classReader.readU2Int());
            case CONSTANT_Class:
                return new ConstantClassInfo(classReader.readU2Int());
            case CONSTANT_Fieldref:
                return new ConstantFieldRefInfo(classReader.readU2Int(), classReader.readU2Int());
            case CONSTANT_Methodref:
                return new ConstantMemberRefInfo(classReader.readU2Int(), classReader.readU2Int());
            case CONSTANT_InterfaceMethodref:
                return new ConstantInterfaceMethodRefInfo(classReader.readU2Int(), classReader.readU2Int());
            case CONSTANT_NameAndType:
                return new ConstantNameAndTypeInfo(classReader.readU2Int(), classReader.readU2Int());
            case CONSTANT_MethodType:
                return new ConstantMethodTypeInfo(classReader.readU2Int());
            case CONSTANT_MethodHandle:
                return new ConstantMethodHandleInfo(classReader.readU1(), classReader.readU2Int());
            case CONSTANT_InvokeDynamic:
                return new ConstantInvokeDynamicInfo(classReader.readU2Int(), classReader.readU2Int());
            default:
                throw new ClassFormatException("constant pool tag!");

        }

    }

    public static List<ConstantInfo> readConstantPool(ClassReader classReader){
        int cpCount = classReader.readU2Int();
        List<ConstantInfo> res = new LinkedList<>();
        int i = 1;
        while (i<cpCount){
            int tag = classReader.readU1().toShort();
            ConstantInfo constantInfo = processConstantInfo(tag, classReader);
            res.add(constantInfo);
            if(CONSTANT_Double == tag || CONSTANT_Long == tag){
                i+=2;
                continue;
            }
            i++;
        }
        return res;
    }

}
