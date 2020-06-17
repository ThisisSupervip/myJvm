package com.lgb.classfile;

import com.lgb.classfile.fundamental.*;
import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;

import java.util.LinkedList;
import java.util.List;

public class ClassFile {
    private static final U4 CLASS_FILE_MAGIC = new U4(new byte[] {-54, -2, -70, -66});
    private ClassReader classReader;
    private U4 magic;                   //u4
    private U2 minorVersion;            //u2
    private U2 majorVersion;            //u2
    private List<ConstantInfo> constantPool;
    private U2 accessFlags;             //u2
    private U2 thisClass;               //u2
    private U2 superClass;              //u2
    private U2[] interfaces;            //u2[]
    //fields
    //methods
    //attributes
    public ClassFile(byte[] classData){
        classReader = new ClassReader(classData);
        readAndCheckMagic();
        readAndCheckVersion();
        constantPool = ConstantInfoType.readConstantPool(classReader);

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
        int anInt = majorVersion.toInt();
        if(anInt >= 45 && anInt <= 52){
            return;
        }
        throw new UnsupportedClassVersionError();
    }

    private void readMembers(){
        int memberCount = classReader.readU2().toInt();
        List<MemberInfo> memberInfos = new LinkedList<>();
        for (int i = 0; i < memberCount; i++) {
            memberInfos.add(readMember());
        }
        //memberInfos;
    }

    private MemberInfo readMember(){
        U2 accessFlags = classReader.readU2();
        U2 nameIndex = classReader.readU2();
        U2 descriptorIndex = classReader.readU2();
        List<AttributeInfo> attributeInfos = readAttributes();
        return MemberInfo.builder()
                .accessFlags(accessFlags)
                .nameIndex(nameIndex)
                .descriptorIndex(descriptorIndex)
                .attributeInfos(attributeInfos).build();
    }

    private List<AttributeInfo> readAttributes(){
        return null;
    }


   /* public String getSuperClassName(){
        if(superClass.toSort() > 0){
            return constantPool.getClassName(superClass);
        }
        return "";
    }*/

    public String[] getInterfaceNames(){
        String[] res = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            res[i] = new String(interfaces[i].value);
        }
        return res;
    }

}
