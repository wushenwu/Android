package com.wbxu.smsdata;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class MainActivity extends ListActivity 
{
    //used for displaying data
    public static List<SMSInfo> mListSMS = new ArrayList<SMSInfo>();
    private SMSArrayAdapter mAdapter = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mAdapter = new SMSArrayAdapter(this, mListSMS);
        setListAdapter(mAdapter); 
        
        //just for fun
        mListSMS.clear();
        mListSMS.add(new SMSInfo("911", "U r being watched!"));
        
        //load sms from Preferences, File or SQLite
        SMSStorage store = new SMSStorage(this);
        //store.loadFromPreference(this);
        //store.loadFromFile(this);
        store.loadFromDB(this);
        
        //for refresh
        new SMSReceiver(this);
    }
    
    public void Refresh()
    {
        mAdapter.notifyDataSetChanged();
    }    
}
