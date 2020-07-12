package com.lgb._native.sun.misc;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.rtda.LocalVariables;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.methodarea.Object;

import java.util.Objects;

public class _Unsafe {

    private static final String miscUnsafe = "sun/misc/Unsafe";

    public _Unsafe() {
        Registry.register(miscUnsafe, "arrayBaseOffset", "(Ljava/lang/Class;)I", arrayBaseOffset);
        Registry.register(miscUnsafe, "arrayIndexScale", "(Ljava/lang/Class;)I", arrayIndexScale);
        Registry.register(miscUnsafe, "addressSize", "()I", addressSize);
        Registry.register(miscUnsafe, "objectFieldOffset", "(Ljava/lang/reflect/Field;)J", objectFieldOffset);
        Registry.register(miscUnsafe, "compareAndSwapObject", "(Ljava/lang/Object;JLjava/lang/Object;Ljava/lang/Object;)Z", compareAndSwapObject);
        Registry.register(miscUnsafe, "getIntVolatile", "(Ljava/lang/Object;J)I", getInt);
        Registry.register(miscUnsafe, "compareAndSwapInt", "(Ljava/lang/Object;JII)Z", compareAndSwapInt);
        Registry.register(miscUnsafe, "getObjectVolatile", "(Ljava/lang/Object;J)Ljava/lang/Object;", getObject);
        Registry.register(miscUnsafe, "compareAndSwapLong", "(Ljava/lang/Object;JJJ)Z", compareAndSwapLong);
    }

    // public native int arrayBaseOffset(Class<?> type);
    // (Ljava/lang/Class;)I
    private NativeMethod arrayBaseOffset = (frame) -> {
        OperandStack stack = frame.operandStack;
        stack.pushInt(0);
    };

    // public native int arrayIndexScale(Class<?> type);
    // (Ljava/lang/Class;)I
    private NativeMethod arrayIndexScale = (frame) -> {
        OperandStack stack = frame.operandStack;
        stack.pushInt(1);
    };

    // public native int addressSize();
    // ()I
    private NativeMethod addressSize = (frame) -> {
        OperandStack stack = frame.operandStack;
        stack.pushInt(8); // todo unsafe.Sizeof(int)
    };

    // public native long objectFieldOffset(Field field);
    // (Ljava/lang/reflect/Field;)J
    private NativeMethod objectFieldOffset = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object jField = vars.getRef(1);

        int offset = jField.getIntVar("slot", "I");

        OperandStack stack = frame.operandStack;
        stack.pushLong(offset);
    };

    // public final native boolean compareAndSwapObject(Object o, long offset, Object expected, Object x)
    // (Ljava/lang/Object;JLjava/lang/Object;Ljava/lang/Object;)Z
    private NativeMethod compareAndSwapObject = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object obj = vars.getRef(1);
        java.lang.Object fields = obj.data;
        long offset = vars.getLong(2);
        Object expected = vars.getRef(4);
        Object newVal = vars.getRef(5);

        // todo
        if (fields instanceof LocalVariables) {
            // object
            LocalVariables anys = (LocalVariables) fields;
            boolean swapped = _casObj(obj, anys, offset, expected, newVal);
            frame.operandStack.pushBoolean(swapped);
        } else if (fields.getClass().equals(Object[].class)) {
            // ref[]
            Object[] objs = (Object[]) fields;
            boolean swapped = _casArr(objs, offset, expected, newVal);
            frame.operandStack.pushBoolean(swapped);
        } else {
            // todo
            throw new RuntimeException("todo: compareAndSwapObject!");
        }
        ;
    };

    private boolean _casObj(Object obj, LocalVariables fields, long offset, Object expected, Object newVal) {
        Object current = fields.getRef((int) offset);
        if (current == expected) {
            fields.setRef((int) offset, newVal);
            return true;
        } else {
            return false;
        }
    }

    private boolean _casArr(Object[] objs, long offset, Object expected, Object newVal) {
        Object current = objs[(int) offset];
        if (current == expected) {
            objs[(int) offset] = newVal;
            return true;
        } else {
            return false;
        }
    }

    // public native boolean getInt(Object o, long offset);
    // (Ljava/lang/Object;J)I
    private NativeMethod getInt = (frame) -> {
        LocalVariables vars = frame.localVariables;
        java.lang.Object fields = vars.getRef(1).data;
        long offset = vars.getLong(2);

        OperandStack stack = frame.operandStack;
        if (fields instanceof LocalVariables) {
            LocalVariables slots = (LocalVariables) fields;
            // object
            stack.pushInt(slots.getInt((int) offset));
        } else if (fields.getClass().equals(int[].class)) {
            // int[]
            int[] shorts = (int[]) fields;
            stack.pushInt(shorts[(int) offset]);
        } else {
            throw new RuntimeException("getInt!");
        }
        ;
    };

    // public final native boolean compareAndSwapInt(Object o, long offset, int expected, int x);
    // (Ljava/lang/Object;JII)Z
    private NativeMethod compareAndSwapInt = (frame) -> {
        LocalVariables vars = frame.localVariables;
        java.lang.Object fields = vars.getRef(1).data;
        long offset = vars.getLong(2);
        int expected = vars.getInt(4);
        int newVal = vars.getInt(5);

        if (fields instanceof LocalVariables) {
            LocalVariables slots = (LocalVariables) fields;
            // object
            int oldVal = slots.getInt((int) offset);
            if (oldVal == expected) {
                slots.setInt((int) offset, newVal);
                frame.operandStack.pushBoolean(true);
            } else {
                frame.operandStack.pushBoolean(false);
            }
        } else if (fields.getClass().equals(int[].class)) {
            int[] ints = (int[]) fields;
            // int[]
            int oldVal = ints[(int) offset];
            if (oldVal == expected) {
                ints[(int) offset] = newVal;
                frame.operandStack.pushBoolean(true);
            } else {
                frame.operandStack.pushBoolean(false);
            }
            ;
        } else {
            // todo
            throw new RuntimeException("todo: compareAndSwapInt!");
        }
        ;
    };

    // public native Object getObject(Object o, long offset);
    // (Ljava/lang/Object;J)Ljava/lang/Object;
    private NativeMethod getObject = (frame) -> {
        LocalVariables vars = frame.localVariables;
        java.lang.Object fields = vars.getRef(1).data;
        long offset = vars.getLong(2);

        if (fields instanceof LocalVariables) {
            // object
            LocalVariables anys = (LocalVariables) fields;
            // object
            Object x = anys.getRef((int) offset);
            frame.operandStack.pushRef(x);
        } else if (fields.getClass().equals(Object[].class)) {
            // ref[]
            Object[] objs = (Object[]) fields;
            Object x = objs[(int) offset];
            frame.operandStack.pushRef(x);
        } else {
            throw new RuntimeException("getObject!");
        }
        ;
    };

    // public final native boolean compareAndSwapLong(Object o, long offset, long expected, long x);
// (Ljava/lang/Object;JJJ)Z
    private NativeMethod compareAndSwapLong = (frame) -> {
        LocalVariables vars = frame.localVariables;
        java.lang.Object fields = vars.getRef(1).data;
        long offset = vars.getLong(2);
        long expected = vars.getLong(4);
        long newVal = vars.getLong(6);

        if (fields instanceof LocalVariables) {
            LocalVariables slots = (LocalVariables) fields;
            // object
            long oldVal = slots.getLong((int) offset);
            if (oldVal == expected) {
                slots.setLong((int) offset, newVal);
                frame.operandStack.pushBoolean(true);
            } else {
                frame.operandStack.pushBoolean(false);
            }
        } else if (fields.getClass().equals(Object[].class)) {
            long[] longs = (long[]) fields;
            // long[]
            long oldVal = longs[(int) offset];
            if (oldVal == expected) {
                longs[(int) offset] = newVal;
                frame.operandStack.pushBoolean(true);
            } else {
                frame.operandStack.pushBoolean(false);
            }
        } else {
            // todo
            throw new RuntimeException("todo: compareAndSwapLong!");
        }
        ;
    };

}
