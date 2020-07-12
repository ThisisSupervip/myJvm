package com.lgb.rtda.heap.methodarea;

import com.lgb.classfile.fundamental.AttributeInfoType;
import com.lgb.classfile.fundamental.MemberInfo;
import com.lgb.util.ClassNameHelper;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Field extends ClassMember {

    @Setter @Getter
    private int slotId;
    @Setter @Getter
    private int constValueIndex;

    private Field(Class clazz, MemberInfo memberInfo) {
        super(clazz, memberInfo);
        AttributeInfoType.ConstantValueAttribute constantValueAttribute = memberInfo.getConstantValueAttribute();
        if (Objects.nonNull(constantValueAttribute)) {
            this.constValueIndex = constantValueAttribute.constantValueIndex;
        }
    }

    public static Field[] newFields(Class clazz, MemberInfo[] fieldInfos){
        Field[] res = new Field[fieldInfos.length];
        for (int i = 0; i < fieldInfos.length; i++) {
            res[i] = new Field(clazz, fieldInfos[i]);
        }
        return res;
    }

    public boolean isLongOrDouble(){
        String descriptor = this.getDescriptor();
        return "J".equals(descriptor) || "D".equals(descriptor);
    }

    // reflection
    public Class getType() {
        String className = ClassNameHelper.toClassName(descriptor);
        return clazz.getClassloader().loadClass(className);
    }

}
