package com.lgb.rtda;

import com.lgb.rtda.heap.methodarea.Object;

import java.util.*;

public class Memory {
    public static List<Object> objects = new LinkedList<>();
    private static int pos = 1;
    static {
        objects.add(null);
    }
    public static int add(Object object) {
        int idx;
        if((idx = objects.indexOf(object))!=-1){
            return idx;
        }
        objects.add(pos, object);
        return pos++;
    }
}
