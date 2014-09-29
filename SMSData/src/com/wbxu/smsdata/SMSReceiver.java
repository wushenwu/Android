package com.wbxu.smsdata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver
{
    private static Context mContext = null;
        
    public SMSReceiver()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public SMSReceiver(Context context)
    {
        super();
        mContext = context;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        Object[] objs = (Object[])bundle.get("pdus");
        SmsMessage[] msgs = new SmsMessage[objs.length];
        
        msgs[0] = SmsMessage.createFromPdu((byte[])objs[0]);
        String strPhone = msgs[0].getOriginatingAddress();
        String strMsgBody =  msgs[0].getMessageBody(); 

        Toast.makeText(context, 
                "From:" + strPhone + "  Says:"+ strMsgBody, 
                Toast.LENGTH_LONG)
             .show();
        
        //DataStorage
        SMSStorage storage = new SMSStorage(context);
        storage.storeIntoPreference(strPhone, strMsgBody);
        storage.storeIntoFile(strPhone, strMsgBody);
        storage.storeIntoDB(strPhone, strMsgBody);
        
        //update display
        MainActivity.mListSMS.add(new SMSInfo(strPhone, strMsgBody)); 
        if (mContext != null)
        {
            ((MainActivity)mContext).Refresh();
        }
    }
}
