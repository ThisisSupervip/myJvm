package com.lgb.classfile.fundamental;

import com.lgb.classfile.AttributeInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class MemberInfo {
    private ConstantInfo constantPool;
    private U2 accessFlags;
    private U2 nameIndex;
    private U2 descriptorIndex;
    private List<AttributeInfo> attributeInfos;

}
