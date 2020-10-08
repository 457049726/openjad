package com.openjad.common.util.idgenerator;

import java.io.Serializable;
import java.net.NetworkInterface;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.base.Preconditions;
import com.openjad.common.exception.BizException;
import com.openjad.common.util.constant.UtilLogCode;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

/**
 *
 */
public final class UniqueDistIdGenerator implements Serializable {

    private static Logger log = LoggerFactory.getLogger(UniqueDistIdGenerator.class);

    private static final long serialVersionUID = 8623829702997205284L;

    private static final int LOW_ORDER_FOUR_BYTES = 0xffffffff;
    private static final int LOW_ORDER_THREE_BYTES = 0x00ffffff;

    private static final int MACHINE_IDENTIFIER;
    private static final short PROCESS_IDENTIFIER;
    private final int machineIdentifier;
    private final short processIdentifier;
    private int counter;
    private static Integer FIX_ID_LEN = 32;

    private static final AtomicInteger NEXT_COUNTER = new AtomicInteger(new SecureRandom().nextInt());

    private static final char[] HEX_CHARS = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private UniqueDistIdGenerator() {
        this(MACHINE_IDENTIFIER, PROCESS_IDENTIFIER);
    }
    private UniqueDistIdGenerator(final int machineIdentifier, final short processIdentifier) {
        if ((machineIdentifier & 0xff000000) != 0) {
            throw new IllegalArgumentException("The machine identifier must be between 0 and 16777215 (it must fit in three bytes).");
        }
        this.machineIdentifier = machineIdentifier;
        this.processIdentifier = processIdentifier;

    }

    public static UniqueDistIdGenerator getGenerator(){
        return new UniqueDistIdGenerator();
    }

    public String generate(Object... args){
        this.counter = NEXT_COUNTER.getAndIncrement();
        this.counter = counter & LOW_ORDER_FOUR_BYTES;
        int timestamp = dateToTimestampSeconds(new Date());
        if(args.length == 0){
            int piece  = new SecureRandom().nextInt();
            args = new Object[]{piece & LOW_ORDER_THREE_BYTES};
        }
        return toHexString(timestamp,Objects.hash(args));
    }

    private byte[] toByteArray(int timestamp,int arg) {
        ByteBuffer buffer = ByteBuffer.allocate(FIX_ID_LEN/2);
        putToByteBuffer(buffer,timestamp,arg);
        return buffer.array();  // using .allocate ensures there is a backing array that can be returned
    }

    private String toHexString(int timestamp,int arg) {
        char[] chars = new char[FIX_ID_LEN];
        int i = 0;
        for (byte b : toByteArray(timestamp, arg)) {
            chars[i++] = HEX_CHARS[b >> 4 & 0xF];
            chars[i++] = HEX_CHARS[b & 0xF];
        }
        return new String(chars);
    }

    private void putToByteBuffer(final ByteBuffer buffer, final int timestamp,int arg) {
        Preconditions.checkArgument(buffer != null, "buffer is null");
        Preconditions.checkArgument( buffer.remaining() >= FIX_ID_LEN/2,"buffer.remaining() < ".concat(String.valueOf(FIX_ID_LEN/2)));

        buffer.put(int3(timestamp));
        buffer.put(int2(timestamp));
        buffer.put(int1(timestamp));
        buffer.put(int0(timestamp));
        buffer.put(int2(machineIdentifier));
        buffer.put(int1(machineIdentifier));
        buffer.put(int0(machineIdentifier));
        buffer.put(short1(processIdentifier));
        buffer.put(short0(processIdentifier));
        buffer.put(int3(counter));
        buffer.put(int2(counter));
        buffer.put(int1(counter));
        buffer.put(int0(counter));
        buffer.put(int2(arg));
        buffer.put(int1(arg));
        buffer.put(int0(arg));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UniqueDistIdGenerator objectId = (UniqueDistIdGenerator) o;

        if (counter != objectId.counter) {
            return false;
        }
        if (machineIdentifier != objectId.machineIdentifier) {
            return false;
        }
        return processIdentifier == objectId.processIdentifier;
    }



    @Override
    public int hashCode() {
        int result = machineIdentifier;
        result = 31 * result + (int) processIdentifier;
        result = 31 * result + counter;
        return result;
    }

    static {
        try {
            MACHINE_IDENTIFIER = createMachineIdentifier();
            PROCESS_IDENTIFIER = createProcessIdentifier();
        } catch (Exception e) {
            throw new BizException(UtilLogCode.CODE_00001,"init UniqueDistIdGenerator failed",e);
        }
    }

    private static int createMachineIdentifier() {
        // build a 2-byte machine piece based on NICs info
        int machinePiece;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface ni = e.nextElement();
                sb.append(ni.toString());
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    ByteBuffer bb = ByteBuffer.wrap(mac);
                    try {
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                    } catch (BufferUnderflowException shortHardwareAddressException) { //NOPMD
                        // mac with less than 6 bytes. continue
                    }
                }
            }
            machinePiece = sb.toString().hashCode();
        } catch (Throwable t) {
            // exception sometimes happens with IBM JVM, use SecureRandom instead
            machinePiece = (new SecureRandom().nextInt());
            log.warn(UtilLogCode.CODE_00001,"获取机器码失败，使用一个随机数代替");
        }
        machinePiece = machinePiece & LOW_ORDER_THREE_BYTES;
        return machinePiece;
    }

    // Creates the process identifier.  This does not have to be unique per class loader because
    // NEXT_COUNTER will provide the uniqueness.
    private static short createProcessIdentifier() {
        short processId;
        try {
            String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
            if (processName.contains("@")) {
                processId = (short) Integer.parseInt(processName.substring(0, processName.indexOf('@')));
            } else {
                processId = (short) java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode();
            }

        } catch (Throwable t) {
            // JMX not available on Android, use SecureRandom instead
            processId = (short) new SecureRandom().nextInt();
            log.warn(UtilLogCode.CODE_00002);
        }

        return processId;
    }


    private static int dateToTimestampSeconds(final Date time) {
        return (int) (time.getTime() / 1000);
    }

    private static byte int3(final int x) {
        return (byte) (x >> 24);
    }

    private static byte int2(final int x) {
        return (byte) (x >> 16);
    }

    private static byte int1(final int x) {
        return (byte) (x >> 8);
    }

    private static byte int0(final int x) {
        return (byte) (x);
    }

    private static byte short1(final short x) {
        return (byte) (x >> 8);
    }

    private static byte short0(final short x) {
        return (byte) (x);
    }

}
