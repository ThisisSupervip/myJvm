package com.lgb.entry;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipInputStream;

public class ZipEntry extends Entry {

    protected final Path absPath;

    public ZipEntry(String path) {
        this.absPath = Paths.get(path);
    }

    @Override
    public byte[] readClass(String className) {
        String path = className.replace(".","/") + ".class";
        try (FileInputStream fileInputStream = new FileInputStream(absPath.toFile())) {
            try (BufferedInputStream buffer = IOUtils.buffer(fileInputStream)) {
                try(ZipInputStream zipInputStream = new ZipInputStream(buffer)) {
                    java.util.zip.ZipEntry ze = null;
                    while ((ze = zipInputStream.getNextEntry()) != null) {
                        if (ze.isDirectory()) {
                            continue;
                        }
                        if (ze.getName().equals(path)) {
                            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                                int len = 0;
                                byte[] bytes = new byte[1024];
                                while ((len = zipInputStream.read(bytes)) != -1) {
                                    baos.write(bytes, 0, len);
                                }
                                return baos.toByteArray();
                            }
                        }
                    }
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static void main(String[] args) {
        ZipEntry zipEntry = new ZipEntry("D://fastjson.jar");
        byte[] bytes = zipEntry.readClass("com.alibaba.fastjson.JSON");
        System.out.println();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
