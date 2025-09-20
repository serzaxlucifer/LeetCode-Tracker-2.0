package com.tracker.leetcode.components;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class SystemLoadMonitor {
    private static final OperatingSystemMXBean osBean =
            (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public static double getCpuLoad() {
        return osBean.getSystemCpuLoad();
    }

    public static double getProcessCpuLoad() {
        return osBean.getProcessCpuLoad();
    }

    public static long getFreeMemory() {
        return osBean.getFreePhysicalMemorySize();
    }

    public static long getTotalMemory() {
        return osBean.getTotalPhysicalMemorySize();
    }

    public static long getAvailableMemory() {
        return getFreeMemory()/getTotalMemory();
    }
}
