package com.lgb.entry;

import com.google.common.base.Joiner;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WildcardEntry extends Entry {

    protected CompositeEntry compositeEntry;

    public WildcardEntry(String path) {
        File file = new File(path.replace("*",""));
        File[] files = file.listFiles();
        List<String> pathList = Arrays.asList(files).stream()
                .filter(f -> isZipFilePath(f.getName()))
                .map(File::getAbsolutePath).collect(Collectors.toList());

        String wildPath = Joiner.on(";").join(pathList);
        compositeEntry = new CompositeEntry(wildPath);
    }

    @Override
    public byte[] readClass(String className) {
        return compositeEntry.readClass(className);
    }

}
