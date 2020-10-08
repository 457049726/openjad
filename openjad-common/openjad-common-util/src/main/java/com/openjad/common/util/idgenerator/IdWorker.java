package com.openjad.common.util.idgenerator;

import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

/**
 * 可跟据不同的workerid生成id
 * 
 * 代码来源：https://github.com/sumory/uc/blob/master/src/com/sumory/uc/id/IdWorker.java
 *
 */
@Deprecated
public class IdWorker {
	private final static Logger logger = LoggerFactory.getLogger(IdWorker.class);
	 
    private final long workerId;
    private final long snsEpoch = 1330328109047L;// 起始标记点，作为基准
    
    private long sequence = 0L;// 0，并发控制
    private final long workerIdBits = 10L;// 只允许workerid的范围为：0-1023
    private final long maxWorkerId = -1L ^ -1L << this.workerIdBits;// 1023,1111111111,10位
    private final long sequenceBits = 12L;// sequence值控制在0-4095
 
    private final long workerIdShift = this.sequenceBits;// 12
    private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;// 22
    private final long sequenceMask = -1L ^ -1L << this.sequenceBits;// 4095,111111111111,12位
 
    private long lastTimestamp = -1L;
 
    public IdWorker(long workerId) {
        super();
        if (workerId > this.maxWorkerId || workerId < 0) {// workerid < 1024[10位：2的10次方]
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.workerId = workerId;
    }
 
    public synchronized long nextId() throws Exception {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {// 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环)，下次再使用时sequence是新值
            //System.out.println("lastTimeStamp:" + lastTimestamp);
            this.sequence = this.sequence + 1 & this.sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);// 重新生成timestamp
            }
        }
        else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            logger.error(String.format("Clock moved backwards.Refusing to generate id for %d milliseconds", (this.lastTimestamp - timestamp)));
            throw new Exception(String.format("Clock moved backwards.Refusing to generate id for %d milliseconds", (this.lastTimestamp - timestamp)));
        }
 
        this.lastTimestamp = timestamp;
        // 生成的timestamp
        return timestamp - this.snsEpoch << this.timestampLeftShift | this.workerId << this.workerIdShift | this.sequence;
    }
 
    /**
     * 保证返回的毫秒数在参数之后
     * 
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }
 
    /**
     * 获得系统当前毫秒数
     * 
     * @return
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }
 
    
    
}
