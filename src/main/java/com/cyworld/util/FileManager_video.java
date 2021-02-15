package com.cyworld.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

public class FileManager_video {


	//���� ���ε�
	public static String doFileUpload(InputStream is, String originalFileName, String path) throws IOException {

		String newFileName = "";

		if(is==null) {
			return null;
		}

		//Ŭ���̾�Ʈ�� ���ε��� ���� �̸�		
		if(originalFileName.equals("")) {
			return null;
		}

		//Ȯ���� �и�
		String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));

		if(fileExt==null||fileExt.equals("")) {
			return null;
		}

		//������ ������ ���ο� ���ϸ��� ����(saveFileName)
		newFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());

		newFileName += System.nanoTime();//10�� -9��
		newFileName += fileExt;

		//���� ���
		File f = new File(path);

		if(!f.exists()) {
			f.mkdirs();
		}

		String fullfilePath = path + File.separator + newFileName;

		//���� ���ε�
		FileCopyUtils.copy(is, new FileOutputStream(fullfilePath));

		String[] temp=fullfilePath.split("\\\\");
		int num=0;
		for(int i=0;i<URLDecoder.decode(temp[temp.length-2], "UTF-8").length();i++) {
			char j=URLDecoder.decode(temp[temp.length-2], "UTF-8").charAt(i);
			if(!((j>='a'&&j<='z')||(j>='A'&&j<='Z')||(j>='0'&&j<='9'))) {
				num++;
			}
		}

		if(num>0) {

			File file1=new File(fullfilePath);
			FileInputStream fis=new FileInputStream(file1);

			String finalPath="";
			for(int i=0;i<temp.length-2;i++) {
				finalPath+=temp[i]+"\\";
			}
			finalPath+=URLDecoder.decode(temp[temp.length-2], "UTF-8");

			File file2=new File(finalPath+File.separator+newFileName);
			FileOutputStream fos=new FileOutputStream(file2);

			int data;
			while((data=fis.read())!=-1) {
				fos.write(data);
				fos.flush();
			}

			fis.close();
			fos.close();

			String encodedPath="";
			for(int i=0;i<temp.length-2;i++) {
				encodedPath+=temp[i]+"\\";
			}
			encodedPath+=temp[temp.length-2];

			File[] file3=new File(encodedPath).listFiles();
			for(int i=0;i<file3.length;i++) {
				file3[i].delete();
			}

			File file4=new File(encodedPath);
			file4.delete();
		}

		return newFileName;

	}


	//���� �ٿ�ε�
	public static boolean doFileDownload(HttpServletResponse response, String saveFileName,
			String originalFileName,String path) {

		try {

			String filePath = path + File.separator + saveFileName;

			if(originalFileName==null||originalFileName.equals("")) {
				originalFileName = saveFileName;				
			}

			//������ �ٿ� �޾� Ŭ���̾�Ʈ�Ŀ� �����̸� ������ �ѱ� ���� ����
			originalFileName = 
					new String(originalFileName.getBytes("euc-kr"),"ISO-8859-1");//8859_1

			File f = new File(filePath);

			if(!f.exists())
				return false;

			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", 
					"attachment;fileName=" + originalFileName);

			BufferedInputStream bis = 
					new BufferedInputStream(new FileInputStream(f));

			OutputStream out = response.getOutputStream();

			int n;
			byte[] data = new byte[8*1024];
			while((n=bis.read(data, 0, 8*1024))!=-1) {
				out.write(data,0,n);
			}

			out.flush();
			out.close();
			bis.close();

		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}

		return true;	

	}

	//���� ����
	public static void doFileDelete(String fileName,String path) {

		try {

			String filePath = path + File.separator + fileName;

			File f = new File(filePath);

			if(f.exists())
				f.delete();//���� ����

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}








