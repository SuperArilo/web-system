package com.example.backgroundsystem.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtil {
    public static String getIpAdder(HttpServletRequest request)
    {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
            {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
            {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
            {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1"))
                {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try
                    {
                        inet = InetAddress.getLocalHost();
                    }
                    catch (UnknownHostException e)
                    {
                        e.printStackTrace();
                    }
                    assert inet != null;
                    ipAddress = inet.getHostAddress();
                }
            }
            if (ipAddress != null && ipAddress.length() > 15)
            { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0)
                {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        }
        catch (Exception e)
        {
            ipAddress="";
        }
        return ipAddress;
    }
}
