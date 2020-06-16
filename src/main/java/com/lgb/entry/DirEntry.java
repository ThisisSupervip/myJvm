package com.lgb.entry;

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
        File classFile = absPath.resolve(className).toFile();
        try (FileInputStream fileInputStream = new FileInputStream(classFile)){
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = IOUtils.read(fileInputStream, buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return baos.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String toString() {
        return "DirEntry{" +
                "absPath=" + absPath +
                '}';
    }
}
