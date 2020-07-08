package com.lgb.classfile.fundamental;

import com.lgb.classfile.ClassReader;
import com.lgb.rtda.heap.methodarea.Object;
import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConstantInfoType {

    //see https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4

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

    public static class ConstantIntegerInfo implements ConstantNumberInfo {
        public final U4 bytes;

        public ConstantIntegerInfo(U4 bytes) {
            this.bytes = bytes;
        }

        @Override
        public Number getValue() {
            return bytes.toInt();
        }
    }

    public static class ConstantFloatInfo implements ConstantNumberInfo {

        public final U4 bytes;

        public ConstantFloatInfo(U4 bytes) {
            this.bytes = bytes;
        }

        @Override
        public Number getValue() {
            return bytes.toFloat();
        }
    }

    public static class ConstantLongInfo implements ConstantNumberInfo {

        public final U4 highBytes;
        public final U4 lowBytes;

        public ConstantLongInfo(U4 highBytes, U4 lowBytes) {
            this.highBytes = highBytes;
            this.lowBytes = lowBytes;
        }

        @Override
        public Number getValue() {
            byte[] bytes = new byte[8];
            System.arraycopy(highBytes.value, 0, bytes, 0, 4);
            System.arraycopy(lowBytes.value, 0, bytes, 4, 4);
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            return byteBuffer.getLong(0);
        }
    }

    public static class ConstantDoubleInfo implements ConstantNumberInfo {

        public final U4 highBytes;
        public final U4 lowBytes;

        public ConstantDoubleInfo(U4 highBytes, U4 lowBytes) {
            this.highBytes = highBytes;
            this.lowBytes = lowBytes;
        }

        @Override
        public Number getValue() {
            byte[] bytes = new byte[8];
            System.arraycopy(highBytes.value, 0, bytes, 0, 4);
            System.arraycopy(lowBytes.value, 0, bytes, 4, 4);
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            return byteBuffer.getDouble(0);
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
        private final ConstantInfo[] constantPool;
        private String stringVal;

        public ConstantStringInfo(int stringIndex, ConstantInfo[] constantPool) {
            this.stringIndex = stringIndex;
            this.constantPool = constantPool;
        }

        public String getStringVal(){
            if(Objects.isNull(stringVal)) {
                this.stringVal = ((ConstantUtf8Info) this.constantPool[this.stringIndex]).val;
            }
            return stringVal;
        }
    }

    public static class ConstantClassInfo implements ConstantInfo{
        public final int nameIndex;
        private final ConstantInfo[] constantPool;
        private String name;

        public ConstantClassInfo(int nameIndex, ConstantInfo[] constantPool) {
            this.nameIndex = nameIndex;
            this.constantPool = constantPool;
        }

        public String getName(){
            if(Objects.isNull(this.name)) {
                this.name = ((ConstantUtf8Info) this.constantPool[this.nameIndex]).val;
            }
            return this.name;
        }
    }

    public static class ConstantMemberRefInfo implements ConstantInfo{
        public final int classIndex;
        public final int nameAndTypeIndex;
        protected final ConstantInfo[] constantPool;
        private String name;
        private String descriptor;

        public ConstantMemberRefInfo(int classIndex, int nameAndTypeIndex, ConstantInfo[] constantPool) {
            this.classIndex = classIndex;
            this.nameAndTypeIndex = nameAndTypeIndex;
            this.constantPool = constantPool;
        }

        public String className() {
            ConstantClassInfo constantClassInfo = (ConstantClassInfo) constantPool[classIndex];
            return getUTF8String(constantClassInfo.nameIndex);
        }

        private void setNameAndDescriptor() {
            ConstantNameAndTypeInfo constantInfo = (ConstantNameAndTypeInfo) this.constantPool[nameAndTypeIndex];
            Map<String, String> res = new HashMap<>();
            this.name = this.getUTF8String(constantInfo.nameIndex);
            this.descriptor = this.getUTF8String(constantInfo.descriptorIndex);
        }

        private String getUTF8String(int index){
            return ((ConstantUtf8Info)this.constantPool[index]).val;
        }

        public String getName() {
            if(Objects.isNull(this.name)) {
                setNameAndDescriptor();
            }
            return name;
        }

        public String getDescriptor() {
            if(Objects.isNull(this.descriptor)) {
                setNameAndDescriptor();
            }
            return descriptor;
        }
    }

    public static class ConstantFieldRefInfo extends ConstantMemberRefInfo{
        public ConstantFieldRefInfo(int classIndex, int nameAndTypeIndex, ConstantInfo[] constantPool) {
            super(classIndex, nameAndTypeIndex, constantPool);
        }
    }

    public static class ConstantMethodRefInfo extends ConstantMemberRefInfo{
        public ConstantMethodRefInfo(int classIndex, int nameAndTypeIndex, ConstantInfo[] constantPool) {
            super(classIndex, nameAndTypeIndex, constantPool);
        }
    }

    public static class ConstantInterfaceMethodRefInfo extends ConstantMemberRefInfo{
        public ConstantInterfaceMethodRefInfo(int classIndex, int nameAndTypeIndex, ConstantInfo[] constantPool) {
            super(classIndex, nameAndTypeIndex, constantPool);
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

    private static ConstantInfo processConstantInfo(int tag, ClassReader classReader, ConstantInfo[] constantPool){
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
                int length = lengthByte.intValue;
                return new ConstantUtf8Info(lengthByte, classReader.readBytes(length));
            case CONSTANT_String:
                return new ConstantStringInfo(classReader.readU2Int(), constantPool);
            case CONSTANT_Class:
                return new ConstantClassInfo(classReader.readU2Int(), constantPool);
            case CONSTANT_Fieldref:
                return new ConstantFieldRefInfo(classReader.readU2Int(), classReader.readU2Int(), constantPool);
            case CONSTANT_Methodref:
                return new ConstantMethodRefInfo(classReader.readU2Int(), classReader.readU2Int(), constantPool);
            case CONSTANT_InterfaceMethodref:
                return new ConstantInterfaceMethodRefInfo(classReader.readU2Int(), classReader.readU2Int(), constantPool);
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

    public static ConstantInfo[] readConstantPool(ClassReader classReader){
        int cpCount = classReader.readU2Int();
        ConstantInfo[] res = new ConstantInfo[cpCount];
        int i = 1;
        while (i<cpCount){
            int tag = classReader.readU1().toShort();
            ConstantInfo constantInfo = processConstantInfo(tag, classReader, res);
            res[i] = constantInfo;
            if(CONSTANT_Double == tag || CONSTANT_Long == tag){
                i+=2;
                continue;
            }
            i++;
        }
        return res;
    }

}
