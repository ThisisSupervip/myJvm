package com.lgb.util;

import java.util.Formatter;

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


    public static String hexDump(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        for (int j = 1; j < bytes.length+1; j++) {
            if (j % 8 == 1 || j == 0) {
                if( j != 0){
                    sb.append("\n");
                }
                formatter.format("0%d\t|\t", j / 8);
            }
            formatter.format("%02X", bytes[j-1]);
            if (j % 4 == 0) {
                sb.append(" ");
            }
        }
        sb.append("\n");
        return sb.toString();
    }
}
