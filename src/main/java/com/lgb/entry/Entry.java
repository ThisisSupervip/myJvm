package com.lgb.entry;

public abstract class Entry {
    public abstract byte[] readClass(String className);

    public static Entry newEntry(String path){
        if(path.contains(";")){
            return new CompositeEntry(path);
        }
        if(path.contains("*")){
            return new WildcardEntry(path);
        }
        if(isZipFilePath(path)){
            return new ZipEntry(path);
        }
        return new DirEntry(path);
    }
    protected static boolean isZipFilePath(String path){
        return path.endsWith(".jar")||path.endsWith(".JAR")||path.endsWith("zip")||path.endsWith("ZIP");
    }
}
