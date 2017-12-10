package com.shadow.Utils.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Contain {
	private static String [] User={"�û����","�û�����","�û�����"};
	private static String [] Topic={"������","����","ѡ��A","ѡ��B","ѡ��C","ѡ��D","��ȷ��","���"};
	private  static String [] Song={"�������","��������","��������","������Դ·��","�����Դ·��"};
	public static String user="user";
	public static String topic="topic";
	public static String song="song";
	public static String bundle="bundle";
	public static List<Map<String,Object>> list=null;
	private static Map<String, Object> map=null;
	public static String ExportResurcePath="";
	
	public static String success="success";
	
	public static Double percent;
	
	
	public static void InitData(Class clazz, String str){
		list=new ArrayList<>();
		
		String [] array=null;
		if(str.equals(user)){
			array=User;
		}else if(str.equals(song)){
			array=Song;
		}else if(str.equals(topic)){
			array=Topic;
		}
		
		Field [] fields = clazz.getDeclaredFields();
		
		int index=0;
		for (Field field : fields) {
			field.setAccessible(true);
			
			map=new LinkedHashMap();
			map.put("title"+(index+1),array[index] );
			map.put("name"+(index+1), field.getName());
			list.add(map);
			index++;
		}
		
	}
	

}
