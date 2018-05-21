package net;

import java.io.IOException;
import java.net.InetAddress;

/**
 * InetAddress类的简单用法
 */
public class InetAddressTest {
    public static void main(String[] args) throws IOException {
        //根据主机名来获取对应的InetAddress实例
        InetAddress ip = InetAddress.getByName("www.baidu.com");

        //判断baidu是否可达（防火墙和服务器配置可能会阻塞请求）
        System.out.println("baidu是否可达：" + ip.isReachable(5000));

        //获取该InetAddress实例的IP字符串
        String ipStr = ip.getHostAddress();

        //根据原始IP地址来获取InetAddress实例
        InetAddress local = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});

        //判断本机是否可达
        System.out.println("本机是否可达：" + local.isReachable(5000));

        //获取百度InetAddress实例对应的全限定域名
        String cHostName = ip.getCanonicalHostName();
        System.out.println(cHostName);
    }
}
