package com.lgb._native.java.lang;

import com.lgb.rtda.heap.StringPool;
import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Object;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Objects;

public class ClassHelper {

    // []*Class => Class[]
    public static Object toClassArr(Classloader loader, Class[] classes) {
        int arrLen = classes.length;

        Class classArrClass = loader.loadClass("java/lang/Class").arrayClass();
        Object classArr = classArrClass.newArray(arrLen);

        if (arrLen > 0) {
            Object[] classObjs = classArr.refs();
            for (int i = 0; i < classes.length; i++) {
                classObjs[i] = classes[i].getJClass();
            }
        }

        return classArr;
    }

    // []byte => byte[]
    public static Object toByteArr(Classloader loader, byte[] goBytes) {
        if(Objects.nonNull(goBytes)) {
            return newByteArray(loader, goBytes);
        }
        return null;
    }


    public static Object newByteArray(Classloader loader, byte[] bytes) {
        return new Object(loader.loadClass("[B"), bytes);
    }

    public static Object getSignatureStr(Classloader loader, String signature) {
        if (ObjectUtils.isNotEmpty(signature)) {
            return StringPool.jString(loader, signature);
        }
        return null;
    }
}
