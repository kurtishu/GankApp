package com.github.kurtishu.gank.util;

import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    final static int BUFFER_SIZE = 4096;

    /**
     * 将InputStream转换成String
     * @param in InputStream
     * @return String
     * @throws Exception
     *
     */
    public static String InputStreamTOString(InputStream in) throws Exception{

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(),"UTF-8");
    }

    /**
     * 将InputStream转换成某种字符编码的String
     * @param in
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String InputStreamTOString(InputStream in,String encoding) throws Exception{

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(),encoding);
    }

    /**
     * 将String转换成InputStream
     * @param in
     * @return
     * @throws Exception
     */
    public static InputStream StringTOInputStream(String in) throws Exception{

        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes("UTF-8"));
        return is;
    }

    /**
     * 将InputStream转换成byte数组
     * @param in InputStream
     * @return byte[]
     * @throws IOException
     */
    public static byte[] InputStreamTOByte(InputStream in) throws IOException {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return outStream.toByteArray();
    }

    /**
     * 将byte数组转换成InputStream
     * @param in
     * @return
     * @throws Exception
     */
    public static InputStream byteTOInputStream(byte[] in) throws Exception{

        ByteArrayInputStream is = new ByteArrayInputStream(in);
        return is;
    }

    /**
     * 将byte数组转换成String
     * @param in
     * @return
     * @throws Exception
     */
    public static String byteTOString(byte[] in) throws Exception{

        InputStream is = byteTOInputStream(in);
        return InputStreamTOString(is);
    }

    public static void writeToFile(InputStream in, String filePath) {
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(filePath);
            byte[] data = new byte[BUFFER_SIZE];
            int count = -1;
            while((count = in.read(data,0,BUFFER_SIZE)) != -1)
                outStream.write(data, 0, count);

        } catch (Exception e) {
            //ignore
        } finally {
            closeSteam(outStream);
        }
    }

    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if   (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    public static void copyFile(File source, File target) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(source);
            writeToFile(inputStream, target.getAbsolutePath());
        } catch (FileNotFoundException e) {

        } finally {
            closeSteam(inputStream);
        }
    }

    public static void closeSteam(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                //ignore
            }
        }
    }
}
