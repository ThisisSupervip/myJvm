package com.lgb.classpath;

import com.lgb.entry.Entry;

public class Classpath {
    private Entry bootClasspath;
    private Entry extClasspath;
    private Entry userClasspath;
    private static final String BOOT_CLASSPATH= "E:\\SDKs\\jdk1.8.0_181\\jre\\lib\\*";
    private static final String EXT_CLASSPATH = "E:\\SDKs\\jdk1.8.0_181\\jre\\lib\\ext\\*";

    public Classpath(String userClasspath) {
        this.bootClasspath = Entry.newEntry(BOOT_CLASSPATH);
        this.extClasspath = Entry.newEntry(EXT_CLASSPATH);
        this.userClasspath = Entry.newEntry(userClasspath);
    }

    public byte[] readClass(String className){
        byte[] bytes = bootClasspath.readClass(className);
        if(bytes==null){
            bytes = extClasspath.readClass(className);
        }
        if(bytes==null){
            bytes = userClasspath.readClass(className);
        }
        return bytes;
    }
}
