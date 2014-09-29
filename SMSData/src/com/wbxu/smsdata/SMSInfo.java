package com.wbxu.smsdata;

public class SMSInfo
{
    private String mstrPhone;
    private String mstrMsgBody;

    public SMSInfo(String strPhone, String strMsgBody)
    {
        this.mstrPhone = strPhone;
        this.mstrMsgBody = strMsgBody;
    }

    public String getPhone()
    {
        return this.mstrPhone;
    }
    
    public String getMsgBody()
    {
        return this.mstrMsgBody;
    }
}
