package com.yjh.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyFileUtil {

    /**
     * 文件上传
     * @param foldPath 文件架路径
     * @param file 文件资源
     * @return
     * @throws IOException
     */
    public static String uploadFile(String foldPath, MultipartFile file) throws IOException {
        return uploadFile(foldPath, IdUtil.simpleUUID(),file);
    }

    /**
     * 上传文件
     * @param foldPath 文件夹路径
     * @param fileName 文件名称
     * @param file 资源文件
     * @return
     * @throws IOException
     */
    public static String uploadFile(String foldPath,String fileName, MultipartFile file) throws IOException {
        File path = new File(foldPath);
        if (!path.exists() && !path.isDirectory()) {
            path.mkdirs();
        }
        String suffix = file.getOriginalFilename();
        suffix = suffix.substring(suffix.lastIndexOf("."));
        foldPath += fileName + suffix;
        Files.write(Paths.get(foldPath), file.getBytes());
        return foldPath;
    }


    public static Boolean del(String path){
        File file = new File(path);
        return del(file);
    }

    public static Boolean del(File file){
        return file.delete();
    }

    /**
     * 下载文件
     * @param path
     * @param response
     * @throws IOException
     */
    public static void downLoadFile(String path,HttpServletResponse response) throws IOException {
        File file = new File(path);
        downLoadFile(new FileInputStream(file),file.getName(),response);
    }

    /**
     * 下载文件
     * @param file
     * @param response
     * @throws IOException
     */
    public static void downLoadFile(File file,HttpServletResponse response) throws IOException {
         downLoadFile(new FileInputStream(file),file.getName(),response);
    }

    /**
     *下载文件
     * @param input
     * @param fileName
     * @param response
     * @throws IOException
     */
    public static void downLoadFile(FileInputStream input,String fileName,HttpServletResponse response) throws IOException {
        // 清空响应
        response.reset();
        // 用浏览器下载
        String name =  new String(fileName.getBytes(),"ISO8859-1");
        response.addHeader("Content-Disposition", "attachment;filename=" +name);
        response.setContentType("application/octet-stream");
        // 复制流
        OutputStream out =  response.getOutputStream();
        IoUtil.copy(input,out);
        input.close();
        out.close();
    }




}
