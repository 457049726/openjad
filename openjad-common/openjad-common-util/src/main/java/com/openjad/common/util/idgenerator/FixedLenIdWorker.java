package com.openjad.common.util.idgenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import com.openjad.common.util.StringUtils;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

/**
 * 定长id生成器
 * 
 * 生成id结构是 [时间戳][workerid][sequence]
 * 
 * @author hechuan
 *
 */
public class FixedLenIdWorker {

	private final static Logger logger = LoggerFactory.getLogger(FixedLenIdWorker.class);

	private final static ConcurrentHashMap<String, FixedLenIdWorker> ALL_WORKER = new ConcurrentHashMap<String, FixedLenIdWorker>();

	private final static long DEF_WORKER_ID_BITS = 3L;
	
	private final static long DEF_SEQUENCE_BITS = 6L;
	
	private final static  String DEF_TIME_PREFIX_PATTERN="MMddHHmmssSSS";

	/**
	 * 允许workerid 二进制位数
	 * 用于限定worider长度（在集群规模比较小时，长度可以短一点，集群规模较大时，需要长一点）
	 */
	private long workerIdBits = DEF_WORKER_ID_BITS;//

	/**
	 * sequence 二进制位数
	 * 它决定了同一毫秒内并发id生成数量
	 */
	private long sequenceBits = DEF_SEQUENCE_BITS;//
	
	/**
	 * 工作id
	 * 用于分布式环境下的的机器标识
	 */
	private long workerId;

	/**
	 * 当前毫秒内的并发序列
	 * 用于同一毫秒内多次请求的并发控制
	 */
	private long sequence = 0L;

	/**
	 * 上一次时间戳
	 */
	private long lastTimestamp = -1L;

	/**
	 * 最大的 workerid
	 * 通过(workerIdBits自动计算出来)
	 */
	private long maxWorkerId ;// 

	/**
	 * workerid 左移位位数
	 */
	private long workerIdShift ;// 

	/**
	 * 时间戳左移位位数
	 */
	private long timestampLeftShift;// 

	/**
	 * 最大 sequence
	 */
	private long sequenceMask;// 

	/**
	 * id后缀长度
	 */
	private String maxIdSuffixLen ;

	/**
	 * id后缀格式化
	 */
	private String idSuffixPattern ;
	
	private SimpleDateFormat timePrefixSdf = null;

	private FixedLenIdWorker(long workerId, long workerIdBits, long sequenceBits, String timePrefixPattern) {

		if (workerId < 0) {
			throw new IllegalArgumentException(String.format("workerId can't be less than 0"));
		}
		if (workerIdBits < 0) {
			throw new IllegalArgumentException(String.format("workerIdBits can't be less than 0"));
		}
		if (sequenceBits < 0) {
			throw new IllegalArgumentException(String.format("sequenceBits can't be less than 0"));
		}
		
		this.workerIdBits = workerIdBits;
		
		this.sequenceBits = sequenceBits;
		
		this.maxWorkerId = -1L ^ -1L << this.workerIdBits;

		long maxSeq = -1L ^ -1L << this.sequenceBits;

		this.workerId = workerId;
		
		this.workerIdShift = this.sequenceBits;
		this.timestampLeftShift = this.sequenceBits + this.workerIdBits;
		
		this.sequenceMask = -1L ^ -1L << this.sequenceBits;
		
		this.maxIdSuffixLen = ((-1L ^ (-1L << this.timestampLeftShift)) + "").length() + "";
		
		this.idSuffixPattern = "%0" + maxIdSuffixLen + "d";
		
		if (StringUtils.isNotBlank(timePrefixPattern)) {
			this.timePrefixSdf = new SimpleDateFormat(timePrefixPattern);
		}
		logger.debug("生成IdWorder,workerId：" +workerId+ ",maxWorkerId:"+maxWorkerId+",maxIdSuffixLen:" + maxIdSuffixLen+",maxSeq:"+maxSeq);

		
	}
	

	public static void main(String[] args) {

		for (int i = 0; i < 30; i++) {
			new Thread() {
				public void run() {
					FixedLenIdWorker flw = FixedLenIdWorker.getInstance(0);
					String nextId = flw.nextId();
					if(nextId.endsWith("0")){
						System.out.println("线程号:" + Thread.currentThread().getId() + ",生成id:" + nextId);
					}
					
				}
			}.start();
		}
		
	}

	public static FixedLenIdWorker getInstance(long workerId) {
		if (ALL_WORKER.get(String.valueOf(workerId)) == null) {
			ALL_WORKER.putIfAbsent(String.valueOf(workerId)
					,new FixedLenIdWorker(workerId, DEF_WORKER_ID_BITS, DEF_SEQUENCE_BITS,DEF_TIME_PREFIX_PATTERN));
		}
		return ALL_WORKER.get(String.valueOf(workerId));
	}

	public static FixedLenIdWorker getInstance(long workerId, long workerIdShift, long sequenceBits, String timePrefixPattern) {
		if (ALL_WORKER.get(String.valueOf(workerId)) == null) {
			ALL_WORKER.putIfAbsent(String.valueOf(workerId), new FixedLenIdWorker(workerId, workerIdShift, sequenceBits,
					timePrefixPattern));
		}
		return ALL_WORKER.get(String.valueOf(workerId));
	}

	public synchronized String nextId() {

		long timestamp = this.timeGen();
		if (this.lastTimestamp == timestamp) {// 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环)，下次再使用时sequence是新值
			this.sequence = this.sequence + 1 & this.sequenceMask;
			if (this.sequence == 0) {
				timestamp = this.tilNextMillis(this.lastTimestamp);// 重新生成timestamp
			}

		} else {
			this.sequence = 0;
		}
		if (timestamp < this.lastTimestamp) {
			String err = String.format("Clock moved backwards.Refusing to generate id for %d milliseconds",
					(this.lastTimestamp - timestamp));
			logger.error(err);
			throw new RuntimeException(err);
		}

		this.lastTimestamp = timestamp;

		long idsuffic = this.workerId << this.workerIdShift | this.sequence;

		if (timePrefixSdf != null) {
		
			return new StringBuffer().append(timePrefixSdf.format(new Date(timestamp)))
					.append(String.format(idSuffixPattern, idsuffic)).toString();
			
		} else {
			return new StringBuffer().append(timestamp).append(String.format(idSuffixPattern, idsuffic)).toString();
		}

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
