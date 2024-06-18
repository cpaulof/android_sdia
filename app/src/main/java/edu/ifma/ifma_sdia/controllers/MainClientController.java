package edu.ifma.ifma_sdia.controllers;

import android.util.Log;

import java.util.Arrays;

import edu.ifma.ifma_sdia.handlers.Builders;
import edu.ifma.ifma_sdia.listeners.DataReceiverListener;

public class MainClientController {
    private BaseClient client;
    private DataReceiverListener listener;
    public MainClientController(String host, int port){
        listener = new DataReceiverListener(this);
        client = new BaseClient(host, port, listener);
        client.connect(true, true);
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
            case 0x71: // upload mission
                break;
        }
    }
}
