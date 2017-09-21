package com.dx168.patchsdk.utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 加密解密管理类
 *
 * @author Administrator
 */
public class Encrypt {
    /**
     * 加解密
     * @return
     */
    public static boolean encrypt(byte[] bytes) {
        try {
            int count = bytes.length;
            for (int i = 0; i < count; ++i) {
                byte rawByte = bytes[i];
                bytes[i] = (byte) (rawByte ^ i);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean encrypt(String strFile) {
        try {
            File f = new File(strFile);
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            long totalLen = raf.length();


            FileChannel channel = raf.getChannel();
            MappedByteBuffer buffer = channel.map(
                    FileChannel.MapMode.READ_WRITE, 0, channel.size());
            byte tmp;
            for (int i = 0; i < totalLen; ++i) {
                byte rawByte = buffer.get(i);
                tmp = (byte) (rawByte ^ i);
                buffer.put(i, tmp);
            }
            buffer.force();
            buffer.clear();
            channel.close();
            raf.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) {
//        String dir = "/Users/dongyuangui/DeskTop/activity_full_update.xml";
//        String dir = "/Users/dongyuangui/DeskTop/aatest.apk";
//        long t = System.currentTimeMillis();
//        encrypt(dir);
//        System.out.println("time " + (System.currentTimeMillis() - t));
    }
}
