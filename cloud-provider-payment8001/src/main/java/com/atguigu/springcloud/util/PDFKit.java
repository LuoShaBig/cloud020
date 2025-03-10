package com.atguigu.springcloud.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;

@Slf4j
@Component
public class PDFKit {

    /**
     * @param response 客户端响应
     * @throws IOException io异常
     */
    public void downLoad(HttpServletResponse response, String downloadUrl) throws Throwable {
        if (Objects.isNull(downloadUrl)) {
            // 如果接收参数为空则抛出异常，由全局异常处理类去处理。
            throw new NullPointerException("下载地址为空");
        }
        // 读文件
        File file = new File(downloadUrl);
        if (!file.exists()) {
            log.error("下载文件的地址不存在:{}", file.getPath());
            // 如果不存在则抛出异常，由全局异常处理类去处理。
            throw new HttpMediaTypeNotAcceptableException("文件不存在");
        }
        // 获取用户名
        String fileName = file.getName();
        // 重置response
        response.reset();
        // ContentType，即告诉客户端所发送的数据属于什么类型
        response.setContentType("application/octet-stream; charset=UTF-8");
        // 获得文件的长度
        response.setHeader("Content-Length", String.valueOf(file.length()));
        // 设置编码格式
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        // 发送给客户端的数据
        OutputStream outputStream = response.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        // 读取文件
        bis = new BufferedInputStream(new FileInputStream(new File(downloadUrl)));
        int i = bis.read(buff);
        // 只要能读到，则一直读取
        while (i != -1) {
            // 将文件写出
            outputStream.write(buff, 0, buff.length);
            // 刷出
            outputStream.flush();
            i = bis.read(buff);
        }
    }

    public static String readFileByUrl(String urlStr) {
        String res=null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            res = readInputStream(inputStream);
        } catch (Exception e) {
            log.error("通过url地址获取文本内容失败 Exception：" + e);
        }
        return res;
    }

    /**
     * 从输入流中获取字符串
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        //System.out.println(new String(bos.toByteArray(),"utf-8"));
        return new String(bos.toByteArray(),"utf-8");
    }



}
