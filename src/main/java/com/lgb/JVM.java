package com.lgb;

import com.lgb.classfile.ClassFile;
import com.lgb.classfile.fundamental.MemberInfo;
import com.lgb.classpath.Classpath;

public class JVM {

    public static ClassFile loadClass(String className, Classpath cp) {
        byte[] bytes = cp.readClass(className);
        ClassFile classFile = new ClassFile(bytes);
        return classFile;
    }

    public static MemberInfo getMainMethod(ClassFile classFile) {
        MemberInfo[] methods = classFile.getMethods();
        for (int i = 0; i < methods.length; i++) {
            String name = methods[i].name();
            String descriptor = methods[i].descriptor();
            if("main".equals(name)&&"([Ljava/lang/String;)V".equals(descriptor)){
                return methods[i];
            }
        }
        return null;
    }
}
