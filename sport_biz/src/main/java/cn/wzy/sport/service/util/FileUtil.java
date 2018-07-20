package cn.wzy.sport.service.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Create by Wzy
 * on 2018/7/20 17:06
 * 不短不长八字刚好
 */
public class FileUtil {

    public static final boolean download(String path, String fileName, String file) {
        File parent = new File(path);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        File target = new File(parent + "/" + fileName);
        if (!target.exists()) {
            try {
                target.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(file);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(target);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
