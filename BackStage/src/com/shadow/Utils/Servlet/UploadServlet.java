package com.shadow.Utils.Servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shadow.Utils.Entity.Data;

@WebServlet("/upload.do")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Data data=new Data();
		request.setCharacterEncoding("utf-8");
		String boundary=request.getContentType();
		boundary ="--"+boundary.substring(boundary.indexOf("=")+1, boundary.length())+"--";
		int boundaryLen = boundary.getBytes("iso-8859-1").length;
		int contentLength=request.getContentLength();
		DataInputStream di =new DataInputStream(request.getInputStream());
		FileOutputStream fos =null;
		byte[] body = new byte[5000];
		int totalReadSize =0;
		String filePath ="";
		String suffix="";
		String fileName="";
		String oldfilename="";
		String fullfilename="";
		int k =0;
		while(totalReadSize<contentLength){
			//��ȡ
			int readSize=di.read(body, 0, 5000);
			totalReadSize+=readSize;
			String content = new String(body,"iso-8859-1");
			int contentTypeIndex = content.indexOf("Content-Type");
			int flag =0;
			//д���ļ�
			if(k==0){//��һ�ζ�
				//�Ѷ�ȡ��������ת����Ϊ�ַ���
				//��Ϊ��ȡ�������ݱ���Ϊ�ֽ�
				//��Ϊ���ݵ�ͷ�������˸����ļ�����Ϣ�������ļ������ļ���׺��
				int fileNameIndex = content.indexOf("filename");
				//��ȡcontentTypeIndex��λ����Ϊ�˷����ҳ��ļ��ĺ�׺
				
				//suffixContet�������ļ���
				String suffixContet = content.substring(fileNameIndex, contentTypeIndex);
				data.setFullSongName(suffixContet);
				fullfilename=suffixContet;
				//��ȡ���ļ���׺
				int pointerIndex = suffixContet.lastIndexOf(".");
				suffix = suffixContet.substring(pointerIndex, suffixContet.length()-3);
				//�õ���֮ǰ������
				oldfilename = suffixContet.substring(0,pointerIndex);
				data.setSongName(oldfilename);
				//ͨ��ϵͳ��ǰʱ������һ��Ψһ���ļ��� 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
				fileName =sdf.format(new Date())+suffix;
				//�ҳ�ͷ������������
				//���Ҽ��������������ݵĳ��ȣ��ֽڵĳ��ȣ�;
				int pos = content.indexOf("\n", contentTypeIndex);
				pos =  content.indexOf("\n", pos+1);
				String disableContent = content.substring(0, pos);
				int start = disableContent.getBytes("iso-8859-1").length;
				//request.getSession().getServletContext() web�������ģ�webroot��·����
				filePath = request.getSession().getServletContext().getRealPath("/resource")+"\\"+fileName;
				File file = new File(filePath);
				file.createNewFile();
				
				flag=content.indexOf(boundary, contentTypeIndex);
				fos = new FileOutputStream(file);
	
				if(flag>0){//һ���Զ�����
					fos.write(body, start+1, readSize-(start+boundaryLen+4));
					
				}else{
					fos.write(body, start+1, readSize-(start+1));
					fos.flush();
					body = new byte[5000];
				}
			}else{
				flag = content.indexOf(boundary, contentTypeIndex);
				if(flag<0){
					fos.write(body, 0, readSize);
					fos.flush();
					body = new byte[5000];
				}else{
					fos.write(body, 0, readSize-(boundaryLen+4));
				}
			}
			
			k++;
		}
		fos.flush();
		fos.close();
		di.close();
		
		//,\"oldfilename\":\""+oldfilename+"\",\"suffixContet\":\""+fullfilename+"\"
		response.getWriter().write("{\"success\":\"success\",\"path\":\""+fileName+"\"}");
		
	}

}
