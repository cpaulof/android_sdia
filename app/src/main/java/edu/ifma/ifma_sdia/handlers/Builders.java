package edu.ifma.ifma_sdia.handlers;

import java.nio.ByteBuffer;

import dji.common.flightcontroller.LocationCoordinate3D;

public class Builders {
    public static byte[] heartBeat(byte[] check){
        ByteBuffer data = ByteBuffer.allocate(5);
        Code code = BuildCodes.HEART_BEAT;
        data.put(code.value);
        data.put(check);
        return data.array();
    }

    public static byte[] aircraftLocation(LocationCoordinate3D loc){
        ByteBuffer data = ByteBuffer.allocate(21); // byte double double float = 21 bytes
        Code code = BuildCodes.AIRCRAFT_LOCATION;
        data.put(code.value);
        data.putDouble(loc.getLatitude());
        data.putDouble(loc.getLongitude());
        data.putFloat(loc.getAltitude());
        return data.array();
    }
    public static byte[] genericDoubleData(byte code, double value){
        ByteBuffer data = ByteBuffer.allocate(9);
        data.put(code);
        data.putDouble(value);
        return data.array();
    }
    public static byte[] genericFloatData(byte code, float value){
        ByteBuffer data = ByteBuffer.allocate(5);
        data.put(code);
        data.putFloat(value);
        return data.array();
    }
    public static byte[] genericIntData(byte code, int value){
        ByteBuffer data = ByteBuffer.allocate(5);
        data.put(code);
        data.putInt(value);
        return data.array();
    }
    public static byte[] genericBoolData(byte code, boolean value){
        ByteBuffer data = ByteBuffer.allocate(2);
        data.put(code);
        data.put(value?(byte)1 : (byte) 0);
        return data.array();
    }

}
