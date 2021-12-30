package com.motadata.kernel.util;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by smit on 29/12/21.
 */
public class CommonUtil
{
    private final static LinkedBlockingQueue<String> pollingQueue = new LinkedBlockingQueue<>();

    public static String takePollingIp()
    {
        try
        {
            return pollingQueue.take();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;
    }

    public static void putPollingIp(String ipName)
    {
        try
        {
            pollingQueue.add(ipName);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}