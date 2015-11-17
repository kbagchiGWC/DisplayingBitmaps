package com.twilio.ipmessaging.demo.util;

import android.content.Context;

import com.twilio.ipmessaging.Channel;
import com.twilio.ipmessaging.Constants.InitListener;
import com.twilio.ipmessaging.IPMessagingClientListener;
import com.twilio.ipmessaging.TwilioIPMessagingClient;
import com.twilio.ipmessaging.TwilioIPMessagingSDK;

import java.util.Arrays;
import java.util.List;

public class BasicIPMessagingClient implements IPMessagingClientListener {

    private static final Logger logger = Logger.getLogger(BasicIPMessagingClient.class);
    private String capabilityToken;
    private long nativeClientParam;
    private TwilioIPMessagingClient ipMessagingClient;
    private Channel[] channels;
    private Context context;

    public BasicIPMessagingClient(Context context) {
        super();
        this.context = context;
    }

    public String getCapabilityToken() {
        return capabilityToken;
    }

    public void setCapabilityToken(String capabilityToken) {
        this.capabilityToken = capabilityToken;
    }


    public void doLogin(final String capabilityToken, final ILoginListener listener) {
        /*TwilioIPMessagingSDK.initializeSDK(context, new InitListener()
        {
            @Override
            public void onInitialized()
            {
                ipMessagingClient = TwilioIPMessagingSDK.createIPMessagingClientWithToken(capabilityToken, BasicIPMessagingClient.this);
                if(listener != null) {
                    listener.onLoginFinished();
                }
                else {
                    listener.onLoginError("ipMessagingClient is null");
                }
            }

            @Override
            public void onError(Exception error)
            {
                logger.d(error.getMessage());
            }
        });*/

        if(!TwilioIPMessagingSDK.isInitialized()) {
            TwilioIPMessagingSDK.initializeSDK(this.context, new InitListener() {
                @Override
                public void onInitialized() {
                    createClientWithToken(listener);
                }

                @Override
                public void onError(Exception error) {
                    logger.d(error.getMessage());
                }
            });
        } else {
            createClientWithToken(listener);
        }
    }

    public BasicIPMessagingClient() {
        super();
    }

    public void cleanupTest() {
        // TODO
    }

    public List<Channel> getChannelList() {
        List<Channel> list = Arrays.asList(this.channels);
        return list;
    }

    public long getNativeClientParam() {
        return nativeClientParam;
    }

    public void setNativeClientParam(long nativeClientParam) {
        this.nativeClientParam = nativeClientParam;
    }

    @Override
    public void onChannelAdd(Channel channel) {
        if(channel != null) {
            logger.d("A Channel :"+ channel.getFriendlyName() + " got added");
        } else {
            logger.d("Received onChannelAdd event.");
        }
    }

    @Override
    public void onChannelChange(Channel channel) {
        if(channel != null) {
            logger.d("Channel Name : "+ channel.getFriendlyName() + " got Changed");
        } else {
            logger.d("received onChannelChange event.");
        }
    }

    @Override
    public void onChannelDelete(Channel channel) {
        if(channel != null) {
            logger.d("A Channel :"+ channel.getFriendlyName() + " got deleted");
        } else {
            logger.d("received onChannelDelete event.");
        }
    }

    @Override
    public void onError(int errorCode, String errorText) {
        logger.d("Received onError event.");
    }

    @Override
    public void onAttributesChange(String attributes) {
        logger.d("Received onAttributesChange event.");
    }

    public TwilioIPMessagingClient getIpMessagingClient() {
        return ipMessagingClient;
    }

    private void createClientWithToken(ILoginListener listener) {
        ipMessagingClient = TwilioIPMessagingSDK.createIPMessagingClientWithToken(capabilityToken, BasicIPMessagingClient.this);
        if(ipMessagingClient != null) {
            /*Intent intent = new Intent(context,ChatActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            ipMessagingClient.setIncomingIntent(pendingIntent);*/
            if(listener != null) {
                listener.onLoginFinished();
            }
        } else {
            listener.onLoginError("ipMessagingClient is null");
        }
    }
}
