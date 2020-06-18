package com.lgb.classfile.fundamental;

import com.lgb.classfile.ClassReader;

public class AttributeInfoType {

    public static final String CONSTANT_VALUE = "ConstantValue";
    public static final String CODE = "Code";
    public static final String STACK_MAP_TABLE = "StackMapTable";
    public static final String EXCEPTIONS = "Exceptions";
    public static final String INNER_CLASSES = "InnerClasses";
    public static final String ENCLOSING_METHOD = "EnclosingMethod";
    public static final String SYNTHETIC = "Synthetic";
    public static final String SIGNATURE = "Signature";
    public static final String SOURCE_FILE = "SourceFile";
    public static final String SOURCE_DEBUG_EXTENSION = "SourceDebugExtension";
    public static final String LINE_NUMBER_TABLE = "LineNumberTable";
    public static final String LOCAL_VARIABLE_TABLE = "LocalVariableTable";
    public static final String LOCAL_VARIABLE_TYPE_TABLE = "LocalVariableTypeTable";
    public static final String DEPRECATED = "Deprecated";
    public static final String RUNTIME_VISIBLE_ANNOTATIONS = "RuntimeVisibleAnnotations";
    public static final String RUNTIME_INVISIBLE_ANNOTATIONS = "RuntimeInvisibleAnnotations";
    public static final String RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS = "RuntimeInvisibleParameterAnnotations";
    public static final String RUNTIME_VISIBLE_TYPE_ANNOTATIONS = "RuntimeVisibleTypeAnnotations";
    public static final String RUNTIME_INVISIBLE_TYPE_ANNOTATIONS = "RuntimeInvisibleTypeAnnotations";
    public static final String ANNOTATION_DEFAULT = "AnnotationDefault";
    public static final String BOOTSTRAP_METHODS = "BootstrapMethods";
    public static final String METHOD_PARAMETERS = "MethodParameters";

    public static AttributeInfo readAttribute(ClassReader classReader, ConstantInfo[] constantPool) {
        int attributeNameIndex = classReader.readU2Int();
        String attrName = ((ConstantInfoType.ConstantUtf8Info) constantPool[attributeNameIndex]).val;
        int attributeLength = classReader.readU4().toInt();
        byte[] attrBytes = classReader.readBytes(attributeLength);
        switch (attrName){
            case (CONSTANT_VALUE):
                int constantValueIndex = new U2(attrBytes).intValue;
                return new ConstantValueAttribute(attributeNameIndex, attributeLength, attrName, constantValueIndex);
            case (CODE):
                return new CodeAttribute(attributeNameIndex, attributeLength, attrName, attrBytes, constantPool);
            case (SYNTHETIC):
                return new SyntheticAttribute(attributeNameIndex, attributeLength, attrName);
            case (DEPRECATED):
                return new DeprecatedAttribute(attributeNameIndex, attributeLength, attrName);
            case (SOURCE_FILE):
                int sourceFileIndex = new U2(attrBytes).intValue;
                return new SourceFileAttribute(attributeNameIndex, attributeLength, attrName, sourceFileIndex);
            case (EXCEPTIONS):
                return new ExceptionsAttribute(attributeNameIndex, attributeLength, attrName, attrBytes);
            case (LINE_NUMBER_TABLE):
                return new LineNumberTableAttribute(attributeNameIndex, attributeLength, attrName, attrBytes);
            case (LOCAL_VARIABLE_TABLE):
                return new LocalVariableTableAttribute(attributeNameIndex, attributeLength, attrName, attrBytes);
            default:
                return new UnparsedAttribute(attributeNameIndex, attributeLength, attrName, attrBytes);


        }
    }

    public static AttributeInfo[] readAttributes(ClassReader classReader, ConstantInfo[] constantPool) {
        int attributesCount = classReader.readU2Int();
        AttributeInfo[] attributes = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            attributes[i] = readAttribute(classReader, constantPool);
        }
        return attributes;
    }

    public static class UnparsedAttribute extends AttributeInfo {
        public final byte[] info;

        public UnparsedAttribute(int attributeNameIndex, int attributeLength, String attrName, byte[] info) {
            super(attributeNameIndex, attributeLength, attrName);
            this.info = info;
        }
    }

    public static class SyntheticAttribute extends AttributeInfo {
        public SyntheticAttribute(int attributeNameIndex, int attributeLength, String attrName) {
            super(attributeNameIndex, attributeLength, attrName);
        }
    }

    public static class DeprecatedAttribute extends AttributeInfo {
        public DeprecatedAttribute(int attributeNameIndex, int attributeLength, String attrName) {
            super(attributeNameIndex, attributeLength, attrName);
        }
    }

    public static class ConstantValueAttribute extends AttributeInfo {
        public final int constantValueIndex;

        public ConstantValueAttribute(int attributeNameIndex, int attributeLength, String attrName, int constantValueIndex){
            super(attributeNameIndex, attributeLength, attrName);
            this.constantValueIndex = constantValueIndex;
        }
    }

    public static class CodeAttribute extends AttributeInfo {
        public final U2 maxStack;
        public final U2 maxLocals;
        public final U4 codeLength;
        public final byte code[];
        public final int exceptionTableLength;
        public final ExceptionTableEntry[] exceptionTable;
        public final AttributeInfo[] attributes;

        public CodeAttribute(int attributeNameIndex, int attributeLength, String attrName, byte[] attrBytes, ConstantInfo[] constantPool){
            super(attributeNameIndex, attributeLength, attrName);
            ClassReader classReader = new ClassReader(attrBytes);
            maxStack = classReader.readU2();
            maxLocals = classReader.readU2();
            codeLength = classReader.readU4();
            code = classReader.readBytes(codeLength.toInt());
            exceptionTableLength = classReader.readU2Int();
            exceptionTable = new ExceptionTableEntry[exceptionTableLength];
            //读取exception_table
            for (int i = 0; i < exceptionTableLength; i++) {
                U2 startPc = classReader.readU2();
                U2 endPc = classReader.readU2();
                U2 handlerPc = classReader.readU2();
                U2 catchType = classReader.readU2();
                exceptionTable[i] = new ExceptionTableEntry(startPc, endPc, handlerPc, catchType);
            }
            attributes = readAttributes(classReader, constantPool);
        }

    }

    public static class ExceptionTableEntry {
        public final U2 startPc;
        public final U2 endPc;
        public final U2 handlerPc;
        public final U2 catchType;

        public ExceptionTableEntry(U2 startPc, U2 endPc, U2 handlerPc, U2 catchType) {
            this.startPc = startPc;
            this.endPc = endPc;
            this.handlerPc = handlerPc;
            this.catchType = catchType;
        }
    }


    public static class SourceFileAttribute extends AttributeInfo {
        public final int sourceFileIndex;

        public SourceFileAttribute(int attributeNameIndex, int attributeLength, String attrName, int sourceFileIndex) {
            super(attributeNameIndex, attributeLength, attrName);
            this.sourceFileIndex = sourceFileIndex;
        }
    }

    public static class ExceptionsAttribute extends AttributeInfo {
        public final int numberOfExceptions;
        public final U2[] exceptionIndexTable;

        public ExceptionsAttribute(int attributeNameIndex, int attributeLength, String attrName, byte[] attrBytes){
            super(attributeNameIndex, attributeLength, attrName);
            ClassReader classReader = new ClassReader(attrBytes);
            numberOfExceptions = classReader.readU2Int();
            exceptionIndexTable = new U2[numberOfExceptions];
            for (int i = 0; i < numberOfExceptions; i++) {
                exceptionIndexTable[i] = classReader.readU2();
            }
        }
    }

    public static class LineNumberTableAttribute extends AttributeInfo {
        public final int lineNumberTableLength;
        public final LineNumberTableEntry[] lineNumberTable;

        public LineNumberTableAttribute(int attributeNameIndex, int attributeLength, String attrName, byte[] attrBytes){
            super(attributeNameIndex, attributeLength, attrName);
            ClassReader classReader = new ClassReader(attrBytes);
            lineNumberTableLength = classReader.readU2Int();
            lineNumberTable = new LineNumberTableEntry[lineNumberTableLength];
            for (int i = 0; i < lineNumberTableLength; i++) {
                U2 startPc = classReader.readU2();
                U2 lineNumber = classReader.readU2();
                lineNumberTable[i] = new LineNumberTableEntry(startPc, lineNumber);
            }
        }
    }

    public static class LineNumberTableEntry {
        public final U2 startPc;
        public final U2 lineNumber;

        public LineNumberTableEntry(U2 startPc, U2 lineNumber) {
            this.startPc = startPc;
            this.lineNumber = lineNumber;
        }
    }

    public static class LocalVariableTableAttribute extends AttributeInfo {
        public final int localVariableTableLength;
        public final LocalVariableTableEntry[] localVariableTable;

        public LocalVariableTableAttribute(int attributeNameIndex, int attributeLength, String attrName, byte[] attrBytes){
            super(attributeNameIndex, attributeLength, attrName);
            ClassReader classReader = new ClassReader(attrBytes);
            localVariableTableLength = classReader.readU2Int();
            localVariableTable = new LocalVariableTableEntry[localVariableTableLength];
            for (int i = 0; i < localVariableTableLength; i++) {
                U2 startPc = classReader.readU2();
                U2 length = classReader.readU2();
                U2 nameIndex = classReader.readU2();
                U2 descriptorIndex = classReader.readU2();
                U2 index = classReader.readU2();
                localVariableTable[i] = new LocalVariableTableEntry(startPc, length, nameIndex, descriptorIndex, index);
            }
        }
    }

    public static class LocalVariableTableEntry {
        public final U2 startPc;
        public final U2 length;
        public final U2 nameIndex;
        public final U2 descriptorIndex;
        public final U2 index;

        public LocalVariableTableEntry(U2 startPc, U2 length, U2 nameIndex, U2 descriptorIndex, U2 index) {
            this.startPc = startPc;
            this.length = length;
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
            this.index = index;
        }
    }


}
