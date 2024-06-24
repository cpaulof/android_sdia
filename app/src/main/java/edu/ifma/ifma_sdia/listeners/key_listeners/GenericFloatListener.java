package edu.ifma.ifma_sdia.listeners.key_listeners;

import androidx.annotation.Nullable;

import dji.keysdk.callback.KeyListener;
import edu.ifma.ifma_sdia.controllers.BaseClient;
import edu.ifma.ifma_sdia.handlers.Builders;

public class GenericFloatListener implements KeyListener {
    private final BaseClient client;
    private final byte code;
    public GenericFloatListener(BaseClient client, byte code){
        this.client = client;
        this.code = code;
    }
    @Override
    public void onValueChange(@Nullable Object o, @Nullable Object o1) {
        double v;
        if(o==null && o1==null) return;
        if(o1!=null) v = (double) o1;
        else v = (double) o;
        byte[] data = Builders.genericDoubleData(this.code, v);
        this.client.addSenderData(data);
    }
}
