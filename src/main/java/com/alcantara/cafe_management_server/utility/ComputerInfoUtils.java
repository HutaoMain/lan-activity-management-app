package com.alcantara.cafe_management_server.utility;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ComputerInfoUtils {
    public static String getCurrentIpAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unable to retrieve current IpAddress", e);
        }
    }
}
