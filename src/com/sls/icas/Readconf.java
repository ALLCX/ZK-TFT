package com.sls.icas;

import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Readconf {

	public Readconf(Properties ps, String conffilename) throws Exception{

		//����ʱʹ�����д���
//		String file = System.getProperty("user.dir") + "/conf/" + conffilename;
		
		//��Ҫ����jar��ʱʹ�����д���
		String file = System.getProperty("icas.conf") + conffilename;
		
		InputStream ins = new BufferedInputStream (new FileInputStream(file));
		
		try {
			ps.load(ins);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}