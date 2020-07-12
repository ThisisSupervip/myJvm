package com.lgb.rtda;

import com.lgb.rtda.heap.methodarea.Object;

import java.util.Arrays;
import java.util.List;

public class LocalVariables extends Variables {

    public LocalVariables(int maxLocals) {
        super(maxLocals);
    }

    public Object getThis() {
        return getRef(0);
    }

    @Override
    public java.lang.Object clone() {
        LocalVariables res = new LocalVariables(this.size);
        res.byteBuffer = byteBuffer.wrap(this.byteBuffer.array().clone());
        return res;
    }

    public java.lang.Object[] getJavaParams(java.lang.Class[] classes, boolean isStatic){
        java.lang.Object[] res = new java.lang.Object[classes.length];
        int index = 1;
        if(isStatic){
            index = 0;
        }
        for (int i = 0; i < classes.length; i++) {
            java.lang.Object type = classes[i];
            if(type == byte.class){
                res[i] = (byte)getInt(index);
                index++;
            }
            else if(type == char.class){
                res[i] = (char)getInt(index);
                index++;
            }
            else if(type == double.class){
                res[i] = getDouble(index);
                index+=2;
            }
            else if(type == float.class){
                res[i] = getFloat(index);
                index++;
            }
            else if(type == int.class){
                res[i] = getInt(index);
                index++;
            }
            else if(type == long.class){
                res[i] = getLong(index);
                index+=2;
            }
            else if(type == short.class){
                res[i] = (short)getInt(index);
                index++;
            }
            else if(type == boolean.class){
                res[i] = getInt(index) == 1;
                index++;
            }
            else {
                res[i] = getRef(index).parseToJavaOrgObj();
                index++;
            }
        }
        return res;
    }
}
