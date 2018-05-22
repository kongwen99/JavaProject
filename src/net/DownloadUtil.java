package net;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 多线程下载
 */
public class DownloadUtil {
    //指定下载资源的路径
    private String path;
    //指定下载文件的保存位置
    private String targetFile;
    //定义下载该资源需要的线程数量
    private int threadNum;
    //下载的线程对象
    private DownThread[] threads;
    //下载文件总大小
    private int fileSize;

    public DownloadUtil(String path, String targetFile, int threadNum) {
        this.path = path;
        this.targetFile = targetFile;
        this.threadNum = threadNum;
        //初始化threads数组
        threads = new DownThread[threadNum];
    }

    public void download() throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        //设置
        conn.setRequestProperty(
                "Accept",
                "image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
                        + "application/x-shockwave-flash, application/xaml+xml, "
                        + "application/vnd.ms-xpsdocument, application/x-ms-xbap, "
                        + "application/x-ms-application, application/vnd.ms-excel, "
                        + "application/vnd.ms-powerpoint, application/msword, */*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        //设置文字字符集
        conn.setRequestProperty("Charset", "UTF-8");
        //保持长连接
        conn.setRequestProperty("Connection", "Keep-Alive");
        //得到文件大小
        fileSize = conn.getContentLength();
        conn.disconnect();
        int currentPartSize = fileSize/threadNum + 1;
        RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
        //设置本地文件的大小
        file.setLength(fileSize);
        file.close();
        for (int i = 0; i < threadNum; i++) {
            //计算每个线程下载开始的位置
            int startPos = i * currentPartSize;
            //每个线程使用一个RandomAccessFile进行下载
            RandomAccessFile currentPart = new RandomAccessFile(targetFile, "rw");
            //定位该线程的下载位置
            currentPart.seek(startPos);
            //创建下载线程
            threads[i] = new DownThread(startPos, currentPartSize, currentPart);
            //启动下载线程
            threads[i].start();
        }
    }

    //获取下载完成的百分比
    public double getCompleteRate(){
        //统计多个线程已经下载的总大小
        int sumSize = 0;
        for (int i = 0; i < threadNum; i++) {
            sumSize += threads[i].length;
        }
        //返回已经完成的百分比
        return sumSize * 1.0 / fileSize;
    }

    /**
     * 下载文件的线程类
     */
    private class DownThread extends Thread {
        //当前线程的下载位置
        private int startPos;
        //当前线程负责下载的文件大小
        private int curPartSize;
        //当前线程需要下载的文件块
        private RandomAccessFile curPart;
        //已下载字节数
        private int length;

        public DownThread(int startPos, int curPartSize, RandomAccessFile curPart) {
            this.startPos = startPos;
            this.curPartSize = curPartSize;
            this.curPart = curPart;
        }
        public void run(){
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
                //设置
                conn.setRequestProperty(
                        "Accept",
                        "image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
                                + "application/x-shockwave-flash, application/xaml+xml, "
                                + "application/vnd.ms-xpsdocument, application/x-ms-xbap, "
                                + "application/x-ms-application, application/vnd.ms-excel, "
                                + "application/vnd.ms-powerpoint, application/msword, */*");
                conn.setRequestProperty("Accept-Language", "zh-CN");
                //设置文字字符集
                conn.setRequestProperty("Charset", "UTF-8");
                //下面这行代码可以省略
                conn.connect();
                InputStream inStream = conn.getInputStream();
                //跳过startPos个字节，表明该线程只下载自己负责的那部分文件
                inStream.skip(this.startPos);
                byte[] buffer = new byte[1024];
                int hasRead = 0;
                //读取网络数据，并写入本地文件
                while (length < curPartSize && (hasRead = inStream.read(buffer)) != -1){
                    curPart.write(buffer, 0, hasRead);
                    //累计该线程下载的总大小
                    length += hasRead;
                }
                curPart.close();
                inStream.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }
}