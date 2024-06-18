package edu.ifma.ifma_sdia.handlers;

import java.nio.ByteBuffer;

public class Builders {
    public static byte[] heartBeat(byte[] check){
        ByteBuffer data = ByteBuffer.allocate(5);
        Code code = BuildCodes.HEART_BEAT;
        data.put(code.value);
        data.put(check);
        return data.array();
    }

    public static byte[] telemetryData(){
        return new byte[4];
    }

}
