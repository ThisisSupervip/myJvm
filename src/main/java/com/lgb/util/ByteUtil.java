package com.lgb.util;

public class ByteUtil {
    public static int bytes2Int(byte[] bytes, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int n = ((int)bytes[i]) & 0xff;
            n <<= (--len) * 8;
            sum += n;
        }
        return sum;
    }

    public static int bytes2Int(byte[] bytes) {
        return bytes2Int(bytes, 0, bytes.length);
    }
}
