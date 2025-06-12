package com.qfleaf.cosmosimserver.core.domain.id;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 雪花ID生成器Java实现
 */
public class SnowflakeIdGenerator {
    
    // 起始时间戳，可设置为系统上线时间(一旦确定不能更改)
    private final static long START_TIMESTAMP = 1625097600000L; // 2021-07-01 00:00:00
    
    // 每一部分的位数
    private final static long SEQUENCE_BIT = 12;   // 序列号位数
    private final static long WORKER_BIT = 5;      // 机器标识位数
    private final static long DATACENTER_BIT = 5;  // 数据中心位数
    
    // 每一部分的最大值
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    private final static long MAX_WORKER = ~(-1L << WORKER_BIT);
    private final static long MAX_DATACENTER = ~(-1L << DATACENTER_BIT);
    
    // 每一部分的左移位
    private final static long WORKER_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + WORKER_BIT;
    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;
    
    private final long workerId;      // 机器ID
    private final long datacenterId;  // 数据中心ID
    private long sequence = 0L;       // 序列号
    private long lastTimestamp = -1L; // 上一次时间戳

    /**
     * 构造函数
     * @param workerId 工作机器ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER || workerId < 0) {
            throw new IllegalArgumentException("workerId must be between 0 and " + MAX_WORKER);
        }
        if (datacenterId > MAX_DATACENTER || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId must be between 0 and " + MAX_DATACENTER);
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }
    
    /**
     * 自动根据机器MAC地址生成workerId
     */
    public SnowflakeIdGenerator() {
        this.datacenterId = getDatacenterId();
        this.workerId = getWorkerId(datacenterId);
    }

    /**
     * 生成下一个ID
     */
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        
        // 如果当前时间小于上一次时间戳，说明时钟回拨
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                // 如果时钟回拨在5ms内，等待
                try {
                    wait(offset << 1);
                    timestamp = System.currentTimeMillis();
                    if (timestamp < lastTimestamp) {
                        throw new RuntimeException("Clock moved backwards. Refusing to generate id");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                // 如果时钟回拨超过5ms，直接抛出异常
                throw new RuntimeException("Clock moved backwards. Refusing to generate id");
            }
        }
        
        // 如果是同一毫秒内生成的
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 说明当前毫秒内序列号已用完，等待下一毫秒
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒内，序列号随机开始，提高并发性能
            sequence = ThreadLocalRandom.current().nextLong(1, 3);
        }
        
        lastTimestamp = timestamp;
        
        // 拼接各部分数据
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT)
                | (datacenterId << DATACENTER_LEFT)
                | (workerId << WORKER_LEFT)
                | sequence;
    }
    
    /**
     * 阻塞到下一毫秒
     * @param lastTimestamp 上一次时间戳
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
    
    /**
     * 获取数据中心ID (0~31)
     */
    protected long getDatacenterId() {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (mac != null) {
                    id = ((0x000000FF & (long) mac[mac.length - 1]) 
                            | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                    id = id % (MAX_DATACENTER + 1);
                }
            }
        } catch (Exception e) {
            System.err.println("Get datacenter id error: " + e.getMessage());
            id = ThreadLocalRandom.current().nextLong(MAX_DATACENTER + 1);
        }
        return id;
    }
    
    /**
     * 获取工作机器ID (0~31)
     * @param datacenterId 数据中心ID
     */
    protected long getWorkerId(long datacenterId) {
        StringBuilder pid = new StringBuilder();
        pid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (name != null && !name.isEmpty()) {
            pid.append(name.split("@")[0]);
        }
        
        // 用PID和主机名生成workerId
        return (pid.toString().hashCode() & 0xffff) % (MAX_WORKER + 1);
    }
    

    // ----------------------- 测试方法 -----------------------
    public static void main(String[] args) {
        // 测试1: 指定workerId和datacenterId
        SnowflakeIdGenerator idGenerator1 = new SnowflakeIdGenerator(1, 1);
        for (int i = 0; i < 10; i++) {
            long id = idGenerator1.nextId();
            System.out.println(id + " -> " + Long.toBinaryString(id));
        }
        
        // 测试2: 自动生成workerId
        SnowflakeIdGenerator idGenerator2 = new SnowflakeIdGenerator();
        for (int i = 0; i < 10; i++) {
            long id = idGenerator2.nextId();
            System.out.println(id + " -> " + Long.toBinaryString(id));
        }
        
        // 测试3: 并发测试
        int threadCount = 10;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    System.out.println(Thread.currentThread().getName() + ": " + idGenerator2.nextId());
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
