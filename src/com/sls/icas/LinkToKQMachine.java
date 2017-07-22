package com.sls.icas;

import java.util.Properties;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.DispatchEvents;
import com.jacob.com.STA;

public class LinkToKQMachine {

	private static int port = 4370;

	private static int iMachineNumber = 1;

	private static String KQMachineIP = "";

	public void rtEvent(Dispatch myCom, String ip) throws Exception {

		// setup listener
		
		RTEventHandler rtevent = new RTEventHandler(this, ip);

		new DispatchEvents(myCom, rtevent);

		// connect
		
		Boolean isConnected = Dispatch.call(myCom, "Connect_Net", ip, port).getBoolean();

		// act base on the response

		if (isConnected == true) {

			int idwErrorCode = 0;

			// register all event
			if (Dispatch.call(myCom, "RegEvent", iMachineNumber, 65535).getBoolean())
			{
				//				System.out.println("register successful");
				STA sta = new STA();
				sta.doMessagePump();
			} else {
				Dispatch.call(myCom, "GetLastError", idwErrorCode);

				if (idwErrorCode != 0)
				{
					System.out .println("Reading data from terminal failed,ErrorCode: "+ idwErrorCode);
				} else {
					System.out.println("No data from terminal returns!");
				}
			}
			Dispatch.call(myCom, "Disconnect");// disconnect
		} else {
			// 发布用下面版本
			throw new RuntimeException("Attention!!Failed to connect to the Finger Machine!");
			//测试用下面版本
			
//**************************************************************			
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ActiveXComponent objArchSend = new ActiveXComponent("zkemkeeper.ZKEM.1");
		Dispatch myCom = (Dispatch) objArchSend.getObject();

		Properties ps = new Properties();
		String conffilename = "KQMachineConf.properties";

		try {
			new Readconf(ps, conffilename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		KQMachineIP = ps.getProperty("KQMachineIP");

		try {
			new LinkToKQMachine().rtEvent(myCom, KQMachineIP);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}