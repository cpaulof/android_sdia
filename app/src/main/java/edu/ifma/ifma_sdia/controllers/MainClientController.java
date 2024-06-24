package edu.ifma.ifma_sdia.controllers;

import android.util.Log;

import java.util.Arrays;

import dji.keysdk.DJIKey;
import dji.keysdk.FlightControllerKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.KeyListener;
import dji.sdk.sdkmanager.DJISDKManager;
import edu.ifma.ifma_sdia.handlers.BuildCodes;
import edu.ifma.ifma_sdia.handlers.Builders;
import edu.ifma.ifma_sdia.listeners.DataReceiverListener;
import edu.ifma.ifma_sdia.listeners.key_listeners.AircraftLocationListener;
import edu.ifma.ifma_sdia.listeners.key_listeners.GenericBoolListener;
import edu.ifma.ifma_sdia.listeners.key_listeners.GenericDoubleListener;
import edu.ifma.ifma_sdia.listeners.key_listeners.GenericFloatListener;
import edu.ifma.ifma_sdia.listeners.key_listeners.GenericIntListener;

public class MainClientController {
    private BaseClient client;
    private DataReceiverListener listener;
    public MainClientController(String host, int port){
        listener = new DataReceiverListener(this);
        client = new BaseClient(host, port, listener);
        client.connect(true, true);


        // add key listeners
        KeyListener locationListener = new AircraftLocationListener(client);
        KeyListener velocityXListener = new GenericFloatListener(client, BuildCodes.VELOCITY_X);
        KeyListener velocityYListener = new GenericFloatListener(client, BuildCodes.VELOCITY_Y);
        KeyListener velocityZListener = new GenericFloatListener(client, BuildCodes.VELOCITY_Z);

        KeyListener pitchListener = new GenericDoubleListener(client, BuildCodes.ATTITUDE_PITCH);
        KeyListener yawListener = new GenericDoubleListener(client, BuildCodes.ATTITUDE_YAW);
        KeyListener rollListener = new GenericDoubleListener(client, BuildCodes.ATTITUDE_ROLL);

        KeyListener flyingListener = new GenericBoolListener(client, BuildCodes.IS_FLYING);
        KeyListener flyTimeListener = new GenericIntListener(client, BuildCodes.FLY_TIME_IN_SECONDS);

        //

        KeyManager.getInstance().addListener(FlightControllerKey.create(FlightControllerKey.AIRCRAFT_LOCATION), locationListener);
        KeyManager.getInstance().addListener(FlightControllerKey.create(FlightControllerKey.VELOCITY_X), velocityXListener);
        KeyManager.getInstance().addListener(FlightControllerKey.create(FlightControllerKey.VELOCITY_Y), velocityYListener);
        KeyManager.getInstance().addListener(FlightControllerKey.create(FlightControllerKey.VELOCITY_Z), velocityZListener);

        KeyManager.getInstance().addListener(FlightControllerKey.create(FlightControllerKey.ATTITUDE_PITCH), pitchListener);
        KeyManager.getInstance().addListener(FlightControllerKey.create(FlightControllerKey.ATTITUDE_YAW), yawListener);
        KeyManager.getInstance().addListener(FlightControllerKey.create(FlightControllerKey.ATTITUDE_ROLL), rollListener);

        KeyManager.getInstance().addListener(FlightControllerKey.create(FlightControllerKey.IS_FLYING), flyingListener);
        KeyManager.getInstance().addListener(FlightControllerKey.create(FlightControllerKey.FLY_TIME_IN_SECONDS), flyTimeListener);

        // end add key listeners
    }
    public BaseClient getClient(){return client;}
    public void sendHeartBeat(byte[] check){
        byte[] data = Builders.heartBeat(check);
        client.addSenderData(data);
    }

    public void handleData(DataStruct data){
        byte codeValue = data.data[0];
        switch (codeValue){
            case 0x01: // heart beat
                Log.i("HANDLE_DATA", "HEART_BEAT: "+ Arrays.toString(data.data));
                sendHeartBeat(Arrays.copyOfRange(data.data, 1, 5));
                break;
            case 0x40: // waypoint mission
                break;
        }
    }
}
