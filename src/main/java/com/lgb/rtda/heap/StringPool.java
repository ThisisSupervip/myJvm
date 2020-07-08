package com.lgb.rtda.heap;

import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.methodarea.Object;

import java.util.HashMap;
import java.util.Map;

public class StringPool {
    private static Map<String, Object> internedStrings = new HashMap<>();

    public static Object jString(Classloader loader, String str) {
        Object internedStr = internedStrings.get(str);
        if (null != internedStr) return internedStr;

        char[] chars = str.toCharArray();
        Object jChars = new Object(loader.loadClass("[C"), chars);

        Object jStr = loader.loadClass("java/lang/String").newObject();
        jStr.setRefVal("value", "[C", jChars);

        internedStrings.put(str, jStr);
        return jStr;
    }

    public static String goString(Object jStr) {
        Object charArr = jStr.getRefVar("value", "[C");
        return new String(charArr.chars());
    }

    public static Object internString(Object jStr) {
        String goStr = goString(jStr);
        Object internedStr = internedStrings.get(goStr);
        if (null != internedStr) return internedStr;

        internedStrings.put(goStr, jStr);
        return jStr;
    }
}
