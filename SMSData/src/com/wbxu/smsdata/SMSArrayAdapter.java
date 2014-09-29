package com.wbxu.smsdata;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SMSArrayAdapter extends ArrayAdapter
{
    private Activity mActivity;
    private List mListSMS;
 
    protected static class SMSView 
    {
        protected TextView tvPhone;
        protected TextView tvMSGBody;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = convertView;
        SMSView smsView = null;

        if(rowView == null)
        {
            // Get a new instance of the row layout view
            LayoutInflater inflater = mActivity.getLayoutInflater();
            //rowView = inflater.inflate(R.layout.activity_sms, null);
            rowView = inflater.inflate(R.layout.list_row, null);

            smsView = new SMSView();
            smsView.tvPhone = (TextView) rowView.findViewById(R.id.tvPhone);
            smsView.tvMSGBody = (TextView) rowView.findViewById(R.id.tvMsg);

            // Cache the view objects in the tag, so they can be re-accessed later
            rowView.setTag(smsView);
        } 
        else 
        {
            smsView = (SMSView)rowView.getTag();
        }

        // Transfer the stock data from the data object
        // to the view objects
        SMSInfo curSMS = (SMSInfo) mListSMS.get(position);
        smsView.tvPhone.setText(curSMS.getPhone());
        smsView.tvMSGBody.setText(curSMS.getMsgBody());

        return rowView;
    }
    
    public SMSArrayAdapter(Activity activity, List objects) 
    {
        //super(activity, R.layout.activity_sms , objects);
        super(activity, R.layout.list_row , objects);
        this.mActivity = activity;
        this.mListSMS = objects;
    }
   
    
    /**
     * constructors 
     */
    public SMSArrayAdapter(Context context, int resource,
            int textViewResourceId, List objects)
    {
        super(context, resource, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
    }

    public SMSArrayAdapter(Context context, int resource,
            int textViewResourceId, Object[] objects)
    {
        super(context, resource, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
    }

    public SMSArrayAdapter(Context context, int resource, int textViewResourceId)
    {
        super(context, resource, textViewResourceId);
        // TODO Auto-generated constructor stub
    }

    public SMSArrayAdapter(Context context, int resource, List objects)
    {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
    }

    public SMSArrayAdapter(Context context, int resource, Object[] objects)
    {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
    }

    public SMSArrayAdapter(Context context, int resource)
    {
        super(context, resource);
        // TODO Auto-generated constructor stub
    }

}
