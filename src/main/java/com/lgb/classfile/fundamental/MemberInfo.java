package com.lgb.classfile.fundamental;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class MemberInfo {

    private ConstantInfo[] constantPool;
    private U2 accessFlags;
    private int nameIndex;
    private int descriptorIndex;
    private AttributeInfo[] attributeInfos;


    public AttributeInfoType.CodeAttribute codeAttribute(){
        for (int i = 0; i < attributeInfos.length; i++) {
            if(attributeInfos[i] instanceof AttributeInfoType.CodeAttribute){
                return (AttributeInfoType.CodeAttribute) attributeInfos[i];
            }
        }
        return null;
    }

    public String name() {
        return getConstUTF8(this.nameIndex).val;
    }

    public String descriptor() {
        return getConstUTF8(this.descriptorIndex).val;
    }


    public ConstantInfoType.ConstantUtf8Info getConstUTF8(int index){
        return (ConstantInfoType.ConstantUtf8Info) constantPool[index];
    }

}
