package com.lgb;

import com.lgb.classpath.Classpath;
import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;

public class JVM {

    public static Class loadClass(String className, Classpath cp) {
        Classloader classloader = new Classloader(cp);
        return classloader.loadClass(className);
    }

    public static Method getMainMethod(Class clazz) {
       return clazz.getStaticMethod("main", "([Ljava/lang/String;)V");
    }

}
