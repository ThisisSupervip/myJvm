package com.lgb.instructions.base;

import com.lgb.rtda.Frame;
import com.lgb.rtda.Thread;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;

import java.util.Objects;

public class ClassInitLogic {

    public static void initClass(Thread thread, Class clazz) {
        clazz.startInit();
        scheduleClinit(thread, clazz);
        System.out.println("initClass: "+clazz.getName());
        initSuperClass(thread, clazz);
    }

    public static void scheduleClinit(Thread thread, Class clazz) {
        Method clinit= clazz.getClinitMethod();
        if(Objects.nonNull(clinit)) {
            Frame newFrame = thread.newFrame(clinit);
            thread.pushFrame(newFrame);
        }
    }

    public static void initSuperClass(Thread thread, Class clazz) {
        if(!clazz.isInterface()) {
            Class superClass = clazz.getSuperClass();
            if(Objects.nonNull(superClass) && !superClass.isInitStarted()) {
                initClass(thread, superClass);
            }
        }
    }

}
