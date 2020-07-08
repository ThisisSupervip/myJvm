package com.lgb.rtda.heap.methodarea;

import com.lgb.rtda.LocalVariables;
import com.lgb.rtda.Variables;
import lombok.Getter;
import lombok.Setter;

public class Object {
    public final Class clazz;
    public java.lang.Object data;
    @Getter
    @Setter
    private java.lang.Object extra;

    public Object(Class clazz) {
        this.clazz = clazz;
        this.data = new LocalVariables(clazz.getInstanceSlotCount());
    }

    public Object(Class clazz, java.lang.Object data) {
        this.clazz = clazz;
        this.data = data;
    }

    public LocalVariables getFields() {
        return (LocalVariables) data;
    }

    /**
     * 为引用类型的实列变量赋值
     *
     * @param name
     * @param descriptor
     * @param ref
     */
    public void setRefVal(String name, String descriptor, Object ref) {
        Field field = this.clazz.getField(name, descriptor, false);
        LocalVariables slots = (LocalVariables) this.data;
        slots.setRef(field.getSlotId(), ref);
    }

    public Object getRefVar(String name, String descriptor) {
        Field field = this.clazz.getField(name, descriptor, false);
        LocalVariables slots = (LocalVariables) this.data;
        return slots.getRef(field.getSlotId());
    }

    public boolean isInstanceOf(Class clazz) {
        return clazz.isAssignableFrom(this.clazz);
    }

    public int arrayLength() {
        if (this.data instanceof byte[]) {
            return ((byte[]) this.data).length;
        }

        if (this.data instanceof short[]) {
            return ((short[]) this.data).length;
        }

        if (this.data instanceof int[]) {
            return ((int[]) this.data).length;
        }

        if (this.data instanceof long[]) {
            return ((long[]) this.data).length;
        }

        if (this.data instanceof char[]) {
            return ((char[]) this.data).length;
        }

        if (this.data instanceof float[]) {
            return ((float[]) this.data).length;
        }

        if (this.data instanceof double[]) {
            return ((double[]) this.data).length;
        }

        if (this.data instanceof Object[]) {
            return ((Object[]) this.data).length;
        }

        throw new RuntimeException("Not array");
    }

    @Override
    public String toString() {
        return "Object{" +
                "clazz=" + clazz +
                '}';
    }

    public Object[] refs() {
        return (Object[]) this.data;
    }

    public byte[] bytes() {
        return (byte[]) this.data;
    }

    public short[] shorts() {
        return (short[]) this.data;
    }

    public int[] ints() {
        return (int[]) this.data;
    }

    public long[] longs() {
        return (long[]) this.data;
    }

    public char[] chars() {
        return (char[]) this.data;
    }

    public float[] floats() {
        return (float[]) this.data;
    }

    public double[] doubles() {
        return (double[]) this.data;
    }


    public Object _clone() {
        Object res = new Object(this.clazz);
        if (this.data instanceof byte[]) {
            res.data = ((byte[]) this.data).clone();
        } else if (this.data instanceof short[]) {
            res.data = ((short[]) this.data).clone();
        } else if (this.data instanceof int[]) {
            res.data = ((int[]) this.data).clone();
        } else if (this.data instanceof long[]) {
            res.data = ((long[]) this.data).clone();
        } else if (this.data instanceof char[]) {
            res.data = ((char[]) this.data).clone();
        } else if (this.data instanceof float[]) {
            res.data = ((float[]) this.data).clone();
        } else if (this.data instanceof double[]) {
            res.data = ((double[]) this.data).clone();
        } else if (this.data instanceof Object[]) {
            res.data = ((java.lang.Object[]) this.data).clone();
        } else {
            Variables slots = (Variables) data;
            res.data = slots.clone();
        }
        return res;
    }
}
