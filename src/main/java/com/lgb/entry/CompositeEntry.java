package com.lgb.entry;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CompositeEntry extends Entry {
    protected List<Entry> entries;

    public CompositeEntry(String pathList) {
        entries = new LinkedList<>();
        String[] path = pathList.split(";");
        for (int i = 0; i < path.length; i++) {
            entries.add(newEntry(path[i]));
        }

    }

    @Override
    public byte[] readClass(String className) {
        for (Iterator<Entry> iterator = entries.iterator(); iterator.hasNext(); ) {
            Entry next = iterator.next();
            byte[] bytes = next.readClass(className);
            if(bytes!=null){
                return bytes;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "CompositeEntry{" +
                "entries=" + entries +
                '}';
    }
}
