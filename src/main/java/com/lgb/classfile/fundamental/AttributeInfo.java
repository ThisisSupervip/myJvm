package com.lgb.classfile.fundamental;

public abstract class AttributeInfo {

    public final int attributeNameIndex;
    public final int attributeLength;
    public final String attrName;

    public AttributeInfo(int attributeNameIndex, int attributeLength, String attrName) {
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
        this.attrName = attrName;
    }
}
