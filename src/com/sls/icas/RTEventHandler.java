package com.sls.icas;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.jacob.com.Variant;

public class RTEventHandler extends EventObject{

	private static final long serialVersionUID = -8083928206056899035L;
	
	private static String PHPURL = "";
	private static String NeedResponse = "";

	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public RTEventHandler(Object source, String ip) {

		super(source);
		this.ip = ip;
	}

	public void OnConnected(Variant[] arge){
		System.out.println("Connect with Finger Machine with IP: "+this.ip + " has been established successfully.");
	}
	public void OnDisConnected(Variant[] arge){
		System.out.println("Attention!!Disconnected with Finger Machine with IP "+this.ip + " !!");
		System.out.println("Please double check the link and reboot the Finger Machine Server!!");
	}
	public void OnAttTransactionEx(Variant[] arge){

		System.out.println("Emplyee NO. ("+arge[0].toString()+ ") has been verified successfully.");
		
		Properties ps = new Properties();
		String conffilename = "HttpConf.properties";
		
		try {
			new Readconf(ps, conffilename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PHPURL = ps.getProperty("PHPURL");
		NeedResponse = ps.getProperty("NeedResponse");
		
		boolean blnoResponse = false;
		if (NeedResponse.equals("true")){
			blnoResponse = true;
		}
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("staff_id", arge[0].toString()));

		new HttpClientUtils().postForm(PHPURL, formparams, blnoResponse);
	}
	public void OnNewUser(Variant[] arge){
		System.out.println("New user enrolleed successfully, employee NO. is: "+arge[0].toString());
	}
}
