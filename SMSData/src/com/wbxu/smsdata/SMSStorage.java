package com.wbxu.smsdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.R.integer;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SMSStorage extends ContextWrapper
{
    public static final String SMS_DATA_PRE = "SMSData_pre";
    public static final String SMS_DATA_FILE = "SMSData_file";
    public static final String  SMS_DATA_DB   = "SMSData_DB";
    private final SharedPreferences mPreferences = getSharedPreferences(SMS_DATA_PRE, 
                                                                        Context.MODE_PRIVATE);
    
    private SQLiteDatabase mDB;
    
    public SMSStorage(Context base)
    {
        super(base);

        mDB = openOrCreateDatabase(SMS_DATA_DB, Context.MODE_PRIVATE, null);
        mDB.execSQL("create table if not exists SMSDB(strPhone varchar(20), strMsgBody varchar(50))");
    }
    
    public void storeIntoPreference(String strPhone, String strMsgBody)
    {
        long nCount = mPreferences.getLong("SMSCount", 0) + 1; 
        Editor editor = mPreferences.edit();
        
        //SMSCount ---strPhone -- strMsgBody
        String strIndex = String.valueOf(nCount);
        editor.putString(strIndex + "_phone", strPhone);
        editor.putString(strIndex + "_msg", strMsgBody);
        
        editor.putLong("SMSCount", nCount);
        
        editor.commit();
    }
    
    public void storeIntoFile(String strPhone, String strMsgBody)
    {
        //Internal Storage
        try
        {
            FileOutputStream output = openFileOutput(SMS_DATA_FILE,
                                                    Context.MODE_APPEND);
            
            //lenPhone, Phone, lenMsg, Msg
            output.write(strPhone.length());
            output.write(strPhone.getBytes());
            output.write(strMsgBody.length());
            output.write(strMsgBody.getBytes());
            
        } catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void storeIntoDB(String strPhone, String strMsgBody)
    {
        mDB.execSQL("INSERT INTO SMSDB VALUES(?, ?);", new Object[]{strPhone, strMsgBody});
    }
    
    public void loadFromPreference(Context context)
    {
        String strPhone;
        String strMsgBody;
        String strIndex;
        long nCount = mPreferences.getLong("SMSCount", 0);        
        for (long i = 1; i <= nCount; i++)
        {
            strIndex = String.valueOf(i);
            strPhone = mPreferences.getString(strIndex + "_phone", "None");
            strMsgBody = mPreferences.getString(strIndex + "_msg", "None");
            
            if (strPhone.equals("None"))
            {
                continue;
            }
            
            MainActivity.mListSMS.add(new SMSInfo(strPhone, strMsgBody));
        }
        ((MainActivity)context).Refresh();
    }
    
    public void loadFromFile(Context context)
    {
        try
        {
            int len;
            String strPhone;
            String strMsgBody;
            byte[] info = null;

            
            FileInputStream input = openFileInput(SMS_DATA_FILE);
            int nTotal = input.available();
            int nCur = 0;
            while (nCur < nTotal)
            {
                //lenPhone, phone, lenMSG, msg
                len = input.read();
                info = new byte[len];
                input.read(info, 0, len);
                strPhone = new String(info);
                nCur += (1 + len);
                
                len = input.read();
                info = new byte[len];
                input.read(info, 0, len);
                strMsgBody = new String(info);
                nCur += (1 + len);
                
                MainActivity.mListSMS.add(new SMSInfo(strPhone, strMsgBody));
            }
            ((MainActivity)context).Refresh();
            
        } catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void loadFromDB(Context context)
    {
        Cursor c = mDB.rawQuery("SELECT * FROM SMSDB", null);
        if(0 == c.getCount())
        {
            return;
        }
        
        String strPhone;
        String strMsgBody;
        while(c.moveToNext())
        {
            strPhone = c.getString(0);
            strMsgBody = c.getString(1);
            
            MainActivity.mListSMS.add(new SMSInfo(strPhone, strMsgBody));
        }
        ((MainActivity)context).Refresh();
    }
}
