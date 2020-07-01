package com.lgb.rtda.heap.methodarea;

import com.lgb.classfile.fundamental.MemberInfo;
import lombok.Getter;

import static com.sun.org.apache.bcel.internal.Constants.*;

public class ClassMember {
    protected int accessFlags;
    @Getter
    protected String name;
    @Getter
    protected String descriptor;
    @Getter
    protected Class clazz;

    public ClassMember(Class clazz, MemberInfo memberInfo) {
        this.clazz = clazz;
        this.accessFlags = memberInfo.getAccessFlags().intValue;
        this.name = memberInfo.name();
        this.descriptor = memberInfo.descriptor();
    }

    public boolean isPublic() {
        return 0 != (this.accessFlags & ACC_PUBLIC);
    }

    public boolean isPrivate() {
        return 0 != (this.accessFlags & ACC_PRIVATE);
    }

    public boolean isProtected() {
        return 0 != (this.accessFlags & ACC_PROTECTED);
    }

    public boolean isStatic() {
        return 0 != (this.accessFlags & ACC_STATIC);
    }

    public boolean isFinal() {
        return 0 != (this.accessFlags & ACC_FINAL);
    }

    public boolean isAccessibleTo(Class d) {
        if (this.isPublic()) {
            return true;
        }
        Class c = this.clazz;
        if (this.isProtected()) {
            return d == c || c.getPackageName().equals(d.getPackageName());
        }
        if (!this.isPrivate()) {
            return c.getPackageName().equals(d.getPackageName());
        }
        return d == c;

    }
}
