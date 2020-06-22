package com.lgb.classfile;

import com.lgb.classfile.fundamental.*;
import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;


public class ClassFile {
    private static final U4 CLASS_FILE_MAGIC = new U4(new byte[] {-54, -2, -70, -66});
    private ClassReader classReader;
    private U4 magic;
    private U2 minorVersion;
    private U2 majorVersion;
    private ConstantInfo[] constantPool;
    private U2 accessFlags;
    private U2 thisClass;
    private U2 superClass;
    private U2[] interfaces;
    private MemberInfo[] fields;
    private MemberInfo[] methods;
    private AttributeInfo[] attributes;

    public ClassFile(byte[] classData){
        classReader = new ClassReader(classData);
        readAndCheckMagic();
        readAndCheckVersion();
        constantPool = ConstantInfoType.readConstantPool(classReader);
        accessFlags = classReader.readU2();
        thisClass = classReader.readU2();
        superClass = classReader.readU2();
        readInterfaces();
        fields = readMembers();
        methods = readMembers();
        attributes = AttributeInfoType.readAttributes(classReader, constantPool);
    }

    private void readAndCheckMagic(){
        magic = classReader.readU4();
        if(!CLASS_FILE_MAGIC.equals(magic)){
            throw new ClassFormatException("class file format error");
        }
    }

    private void readAndCheckVersion(){
        minorVersion = classReader.readU2();
        majorVersion = classReader.readU2();
        int anInt = majorVersion.intValue;
        if(anInt >= 45 && anInt <= 52){
            return;
        }
        throw new UnsupportedClassVersionError();
    }

    private void readInterfaces(){
        int n = classReader.readU2Int();
        interfaces = new U2[n];
        for (int i = 0; i < n; i++) {
            interfaces[i] = classReader.readU2();
        }
    }

    private MemberInfo[] readMembers(){
        int memberCount = classReader.readU2().intValue;
        MemberInfo[] memberInfos = new MemberInfo[memberCount];
        for (int i = 0; i < memberCount; i++) {
            memberInfos[i] = readMember();
        }
        return memberInfos;
    }

    private MemberInfo readMember(){
        U2 accessFlags = classReader.readU2();
        int nameIndex = classReader.readU2().intValue;
        int descriptorIndex = classReader.readU2().intValue;
        AttributeInfo[] attributeInfos = AttributeInfoType.readAttributes(classReader, constantPool);
        return MemberInfo.builder()
                .accessFlags(accessFlags)
                .nameIndex(nameIndex)
                .constantPool(constantPool)
                .descriptorIndex(descriptorIndex)
                .attributeInfos(attributeInfos).build();
    }


    public MemberInfo[] getFields() {
        return fields;
    }

    public MemberInfo[] getMethods() {
        return methods;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public ConstantInfo[] getConstantPool() {
        return constantPool;
    }
}
