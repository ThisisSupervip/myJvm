package com.lgb.entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirEntry extends Entry {
    protected final Path absPath;

    public DirEntry(String path) {
        this.absPath = Paths.get(path);
    }

    @Override
    public byte[] readClass(String className){
        File classFile = absPath.resolve(className.replace(".","/")+".class").toFile();
        byte[] bytes = null;
        try {
            bytes = FileUtils.readFileToByteArray(classFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }


    @Override
    public String toString() {
        return "DirEntry{" +
                "absPath=" + absPath +
                '}';
    }
}
