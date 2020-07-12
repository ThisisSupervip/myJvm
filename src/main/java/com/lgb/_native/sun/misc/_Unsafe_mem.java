package com.lgb._native.sun.misc;

import com.lgb._native.Registry;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class _Unsafe_mem {

    private static Unsafe unsafe;
    public _Unsafe_mem() {
        Class unsafeClass = Unsafe.class;
        try {
            Field theUnsafe = unsafeClass.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (Exception e){
            e.printStackTrace();
        }
        Registry.register(unsafe, "sun/misc/Unsafe", "allocateMemory", "(J)J");
        Registry.register(unsafe, "sun/misc/Unsafe", "reallocateMemory", "(JJ)J");
        Registry.register(unsafe, "sun/misc/Unsafe", "freeMemory", "(J)V");
        Registry.register(unsafe, "sun/misc/Unsafe", "addressSize", "()I");
        Registry.register(unsafe, "sun/misc/Unsafe", "getByte", "(J)B");
        Registry.register(unsafe, "sun/misc/Unsafe", "putLong", "(JJ)V");
    }

    // public native long allocateMemory(long bytes);
    // (J)J
    /*public static NativeMethod allocateMemory = (frame) -> {
        LocalVariables vars = frame.localVariables;
        // vars.GetRef(0) // this
        long bytes = vars.getLong(1);


        //long address = allocate(bytes);
        OperandStack stack=frame.operandStack;
        //stack.pushLong(address);
    };*/

    /*// public native long reallocateMemory(long address, long bytes);
    // (JJ)J
    public static NativeMethod reallocateMemory = (frame) -> {
        LocalVariables vars = frame.localVariables;
        // vars.GetRef(0) // this
        long address = vars.getLong(1);
        long bytes = vars.getLong(3);

        long newAddress = reallocate(address, bytes);
        OperandStack stack=frame.operandStack;
        stack.pushLong(newAddress);
    };

    // public native void freeMemory(long address);
    // (J)V
    public static NativeMethod freeMemory = (frame) -> {
        LocalVariables vars = frame.localVariables;
        // vars.GetRef(0) // this
        long address = vars.getLong(1);
        free(address);
    };

    public static NativeMethod mem_getByte = (frame) -> {
        stack, mem = _get(frame);
        OperandStack stack;
        int mem;
        stack.pushInt(mem);
    };


    // public native void putLong(long address, long x);
    // (JJ)V
    public static NativeMethod mem_putLong = (frame) -> {
        LocalVariables vars = frame.localVariables;
        // vars.GetRef(0) // this
        long address = vars.getLong(1);
        long value = vars.getLong(3);

        int mem = memoryAt(address);

        PutInt64(mem, value);
    };*/

}
