package com.springboot.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class FileToByte {
    public static byte[] File2byte(String filePath)
    {
        byte[] buffer = null;
        try
        {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return buffer;
    }
}
