package com.lgb.util;

import java.util.HashMap;
import java.util.Map;

public class ClassNameHelper {
    public static Map<String, String> primitiveTypes = new HashMap<String, String>() {
        {
            put("void", "V");
            put("boolean", "Z");
            put("byte", "B");
            put("short", "S");
            put("int", "I");
            put("long", "J");
            put("char", "C");
            put("float", "F");
            put("double", "D");
        }
    };

    // [XXX -> [[XXX
    // int -> [I
    // XXX -> [LXXX;
    public static String getArrayClassName(String className) {
        return "[" + toDescriptor(className);
    }

    public static String getComponentClassName(String className) {
        if (className.getBytes()[0] == '[') {
            String componentTypeDescriptor = className.substring(1);
            return toClassName(componentTypeDescriptor);
        }
        throw new RuntimeException("Not array " + className);
    }

    private static String toDescriptor(String className) {
        if (className.getBytes()[0] == '[') {
            return className;
        }

        String d = primitiveTypes.get(className);
        if (null != d) return d;

        return "L" + className + ";";
    }

    public static String toClassName(String descriptor) {
        byte descByte = descriptor.getBytes()[0];
        if (descByte == '[') {
            return descriptor;
        }

        if (descByte == 'L') {
            return descriptor.substring(1, descriptor.length() - 1);
        }

        for (Map.Entry<String, String> entry : primitiveTypes.entrySet()) {
            if (entry.getValue().equals(descriptor)) {
                //primitive
                return entry.getKey();
            }
        }

        throw new RuntimeException("Invalid descriptor " + descriptor);

    }

}
