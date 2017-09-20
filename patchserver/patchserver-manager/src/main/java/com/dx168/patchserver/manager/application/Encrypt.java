package com.dx168.patchserver.manager.application;

import org.springframework.util.DigestUtils;

import java.io.*;
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
     *
     * @param strFile 源文件绝对路径
     * @return
     */
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

    public static byte[] decrypt(byte[] bytes) {
        try {
            int count = bytes.length;
            for (int i = 0; i < count; ++i) {
                byte rawByte = bytes[i];
                bytes[i] = (byte) (rawByte ^ i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static void main(String[] args) throws Exception {
//        String srcHash = DigestUtils.md5DigestAsHex(getByte(new File(dir)));
//        String srcHash = DigestUtils.md5DigestAsHex(getByte(new File(dir)));
//        long t = System.currentTimeMillis();
//        encrypt(dir);
//        System.out.println("time " + (System.currentTimeMillis() - t));
//        File file = new File(dir);
//
//        byte[] bytes = decrypt(getByte(file));
//        String hash = DigestUtils.md5DigestAsHex(bytes);
//
//        System.out.println("new hash " + hash);
//        System.out.println("old hash " + srcHash);

//        String dir = "/Users/dongyuangui/yyy/patch-manager-static/20170907163813557-7443/1.3.5/17/fd738bef2faeb4784bad7a65a2f3e9b4_patch.zip";
//        String appId = "20170907163813557-7443";
//        String appSecret = "a94728234232444db6f65b6d18971536";
//        String oldHas = "fd738bef2faeb4784bad7a65a2f3e9b4";
//
//        byte[] bytes = decrypt(getByte(new File(dir)));
//        String newHash = DigestUtils.md5DigestAsHex(bytes);
//        System.out.println("newHash_s" + newHash);
//        System.out.println("oldhash" + DigestUtils.md5DigestAsHex((appId + "_" + appSecret + "_" + oldHas).getBytes()));
//        System.out.println("newHash" + DigestUtils.md5DigestAsHex((appId + "_" + appSecret + "_" + newHash).getBytes()));

//        String dir = "/Users/dongyuangui/yyy/patch-manager-static/20170907163813557-7443/1.3.5/17/fd738bef2faeb4784bad7a65a2f3e9b4_patch.zip";
////        byte[] bytes = getByte(new File(dir));
////        decrypt(bytes);
////        String newfile = "/Users/dongyuangui/yyy/patch-manager-static/20170907163813557-7443/1.3.5/17/fd";
////        write2file(bytes, newfile);
//        encrypt(dir);
//        String hashFile = DigestUtils.md5DigestAsHex(getByte(new File(dir)));
//
////        String hash = DigestUtils.md5DigestAsHex(bytes);
////        System.out.println(hash);
//        System.out.println(hashFile);
//        String dir = "/Users/dongyuangui/yyy/patch-manager-static/20170907163813557-7443/1.3.5/18/fd738bef2faeb4784bad7a65a2f3e9b4_patch.zip";
//        String rawHash = DigestUtils.md5DigestAsHex(getByte(new File(dir)));
//        System.out.println(rawHash);
////        encrypt(dir);
//        byte[] rawByte = getByte(new File(dir));
//        System.out.println("raw hash:" + DigestUtils.md5DigestAsHex(rawByte));
//        byte[] bytes = decrypt(getByte(new File(dir)));
//        System.out.println("after hash:" + DigestUtils.md5DigestAsHex(bytes));
        String dir = "/Users/dongyuangui/yyy/patch-manager-static/20170907163813557-7443/1.3.5/20/fd738bef2faeb4784bad7a65a2f3e9b4_patch.zip";
        String rawHash = DigestUtils.md5DigestAsHex(getByte(new File(dir)));
        System.out.println(rawHash);
//        9aa7abde39766f95bba94f5d61a91c15
//        encrypt(dir);
        byte[] rawByte = getByte(new File(dir));
        System.out.println("raw hash:" + DigestUtils.md5DigestAsHex(rawByte));
        byte[] bytes = decrypt(getByte(new File(dir)));
        System.out.println("after hash:" + DigestUtils.md5DigestAsHex(bytes));

    }

    private static void write2file(byte[] bytes, String s) throws IOException {
        File file = new File(s);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }

    /**
     * 把一个文件转化为字节
     *
     * @param file
     * @return byte[]
     * @throws Exception
     */
    public static byte[] getByte(File file) throws Exception {
        byte[] bytes = null;
        if (file != null) {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if (length > Integer.MAX_VALUE)   //当文件的长度超过了int的最大值
            {
                System.out.println("this file is max ");
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            //如果得到的字节长度和file实际的长度不一致就可能出错了
            if (offset < bytes.length) {
                System.out.println("file length is error");
                return null;
            }
            is.close();
        }
        return bytes;
    }
}
