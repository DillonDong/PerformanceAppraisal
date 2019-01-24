package cn.edu.fjnu.towide.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import cn.edu.fjnu.towide.constant.FilePathNameTemplate;

public class ToZip {
	
	public static boolean packageZip(String fileName,Map<String, String> fileNameMap){
    	
    	File zipFile = new File(fileName);
    	
        //图片打包操作
        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        try {
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));// 用这个构造最终压缩包的输出流

    		for(Map.Entry<String, String> entry : fileNameMap.entrySet()) {
                
                File file = new File(FilePathNameTemplate.uploadFileLocalPath+entry.getValue());
                zipSource = new FileInputStream(file);
                
                byte[] bufferArea = new byte[1024 * 10];// 读写缓冲区
                
                // 压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样
                ZipEntry zipEntry = new ZipEntry(entry.getKey()+"."+entry.getValue().substring(entry.getValue().lastIndexOf(".") + 1));//附件模板名
                zipStream.putNextEntry(zipEntry);// 定位到该压缩条目位置，开始写入文件到压缩包中
                
                bufferStream = new BufferedInputStream(zipSource, 1024 * 10);// 输入缓冲流
                int read = 0;

                // 在任何情况下，b[0] 到 b[off] 的元素以及 b[off+len] 到 b[b.length-1]
                // 的元素都不会受到影响。这个是官方API给出的read方法说明，经典！
                while ((read = bufferStream.read(bufferArea, 0, 1024 * 10)) != -1) {
                    zipStream.write(bufferArea, 0, read);
                }
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (null != bufferStream)
                    bufferStream.close();
                if (null != zipStream)
                    zipStream.close();
                if (null != zipSource)
                    zipSource.close();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
