package com.shadow.Utils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.shadow.Utils.Entity.BaseEntity;

public class DBUtil {
	
	Connection con ;
	
	static{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> List<T> Query(Class<T> clazz,String dataname,HttpServletRequest request) throws Exception{
		Field [] fields = clazz.getDeclaredFields();

		String sql="";
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		if(page!=null||rows!=null){
			int end = Integer.parseInt(rows);
			int start =  ((Integer.parseInt(page)-1)*end);
			sql="select * from "+dataname+"  limit "+start+","+end;
		}else{
			if(dataname.equals("bundle")){
				fields[0].setAccessible(true);
				String Parentid = request.getParameter("Parentid");
				int pid=Integer.parseInt(Parentid==null?"1":Parentid);
				sql+="select * from "+dataname+" where pid = "+pid;
			}else{
				sql+="select * from "+dataname;
			}
			
		}
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData md=ps.getMetaData();
		int count=md.getColumnCount();
		List<String> columnNames=new ArrayList<>(count);
		for(int i=1;i<=count;i++){
			md.getColumnTypeName(i);
		}
		List<T> list=new ArrayList<>();
		/*BaseEntity baseentity;
		while(rs.next()){
			baseentity =  (BaseEntity) clazz.newInstance();
			for (Field field : fields) {
				field.setAccessible(true);
				if(field.getType().getSimpleName().equals("Integer")){
					Integer tempvalue = rs.getInt(field.getName());
					field.set(baseentity, tempvalue);
				}else if(field.getType().getSimpleName().equals("String")){
					String tempvalue = rs.getString(field.getName());
					field.set(baseentity, tempvalue);
				}
			}
			list.add((T)baseentity);
		}*/
		for(Object entity; rs.next(); list.add((T)entity)){
			entity = (BaseEntity) clazz.newInstance();
			for (String columnName:columnNames) {
				String fieldName=columnName;//typeName type_name
				Field field=null;
				try{
					field=clazz.getDeclaredField(fieldName);
					field.setAccessible(true);
					switch(field.getType().getName()){
					case "java.lang.Integer":
						field.set(entity, rs.getInt(field.getName()));
						break;
					case "java.lang.String":
						field.set(entity, rs.getString(field.getName()));
					case "java.sql.Date":
						field.set(entity, new Date(rs.getDate(field.getName()).getTime()));
						break;
					}
				}catch(NoSuchFieldException e){
					continue;
				}
			}
		}
		return list;
	}

	
	public  int QueryTotal(String sql){
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			int total=0;
			if(rs.next()){
				total=rs.getInt("total");
			}
			return total;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	

	
public Object InsertSstuEntity(HttpServletRequest request,Class clazz) throws Exception{
		
		Enumeration<String> ParameterNames=request.getParameterNames();
		Field [] fields = clazz.getDeclaredFields();
		Object object = clazz.newInstance();
		while(ParameterNames.hasMoreElements()){
			String Parametername = ParameterNames.nextElement();
			String[] Parametervalue = request.getParameterValues(Parametername);
			//
			for (Field field : fields){
				field.setAccessible(true);
				Object tempvalue = null;
				if(field.getName().equals(Parametername)){
					if(field.getType().getSimpleName().equals("Integer")){
						tempvalue = Integer.parseInt(Parametervalue[0]);
						field.set(object, tempvalue);
						break;
					}else{
						tempvalue = Parametervalue[0];
						field.set(object, tempvalue);
						break;
					}
				}
		}
	}
		return object;
}
	
		public void AddSong(BaseEntity baseEntity,String dataname) throws Exception{
				
				Field [] fields = baseEntity.getClass().getDeclaredFields();
				String sql="insert into "+dataname+" values (";
				int m=0;
				for (Field field : fields) {
					m++;
				}
				for (int i = 0; i < m; i++) {
					if(i==(m-1)){
						sql+="  ? )";
					}else{
						sql+="  ? ,";
					}
				}
				PreparedStatement ps= con.prepareStatement(sql);
				System.out.println(sql);
				int index=0;
				for (Field field : fields) {
					index++;
					field.setAccessible(true);
					if(field.getType().getSimpleName().equals("Integer")){
						Integer tempvalue = (Integer) field.get(baseEntity);
						ps.setInt(index, tempvalue);
					}else if(field.getType().getSimpleName().equals("String")){
						String tempvalue = (String) field.get(baseEntity);
						ps.setString(index, tempvalue);
					}
				}
			int n=ps.executeUpdate();
			//System.out.println("添加成功了"+n);
		}


		public void UpdateSong(BaseEntity baseEntity,String dataname) throws Exception{
			Field [] fields=baseEntity.getClass().getDeclaredFields();
			String sql="update " +dataname+ " set ";
			int index1 = 0;
			for (Field field : fields) {
				field.setAccessible(true);
				index1++;
			}
			for (int i = 0; i < index1; i++) {
				
				if(i==(index1-1)){
					sql+=fields[i].getName()+" = ? ";
				}else{
					sql+=fields[i].getName()+" = ? ,";
				}
			}
			sql+=" where "+fields[0].getName()+" = "+fields[0].get(baseEntity);
			PreparedStatement ps = con.prepareStatement(sql);
			
			int index=0;
			for (Field field : fields) {
				index++;
				field.setAccessible(true);
				if(field.getType().getSimpleName().equals("Integer")){
					Integer tempvalue = (Integer) field.get(baseEntity);
					ps.setInt(index, tempvalue);
				}else if(field.getType().getSimpleName().equals("String")){
					String tempvalue = (String) field.get(baseEntity);
					ps.setString(index, tempvalue);
				}
			}
			int n=ps.executeUpdate();
			//System.out.println("修改OK"+n);
		}
		
		public void deleteSong(BaseEntity entity,String DataName) throws Exception{
			Class clazz  =  entity.getClass();
			Field [] fields = clazz.getDeclaredFields();
			fields[0].setAccessible(true);
			PreparedStatement ps = con.prepareStatement("delete from "+DataName+" where "+fields[0].getName()+" = "+fields[0].get(entity));
			int n = ps.executeUpdate();
			//System.out.println("删除成功了 "+n);
		}
		
		
		//保存歌曲的路径作者 和 歌曲名
		
		public void SaveSong(BaseEntity baseEntity,String dataname) throws Exception{
			PreparedStatement ps=null;
			int total = 0;
			Field [] fields = baseEntity.getClass().getDeclaredFields();
			int m=0;
			for (Field field : fields) {
				field.setAccessible(true);
				m++;
				if(field.getType().getSimpleName().equals("Integer")){
					if(m==1){
						ps = con.prepareStatement("select max("+field.getName()+") total from "+dataname);
						ResultSet rs = ps.executeQuery();
						if(rs.next()){
							total=rs.getInt("total")+1;
						}
					}
				}
			}
			
			/**
			 * sql语句
			 */
			String sql="insert into "+dataname+" values (";
			int n=0;
			for (Field field : fields) {
				n++;
			}
			for (int i = 0; i < n; i++) {
				if(i==(n-1)){
					sql+="  ? )";
				}else{
					sql+="  ? ,";
				}
			}
			
			ps = con.prepareStatement(sql);
			System.out.println(sql);
			
			int index=0;
			for (Field field : fields) {
				index++;
				field.setAccessible(true);
				if(field.getType().getSimpleName().equals("Integer")){
					if(index==1){
						ps.setInt(index, total);
					}else{
						Integer tempvalue = (Integer) field.get(baseEntity);
						ps.setInt(index, tempvalue);
					}
				}else if(field.getType().getSimpleName().equals("String")){
					String tempvalue = (String) field.get(baseEntity);
					ps.setString(index, tempvalue);
				}
			}
			int a=ps.executeUpdate();
			System.out.println("增加成功了  "+a);
		}
		
		public void Import(BaseEntity entity,Class clazz,String dataname,HttpServletRequest request) throws Exception{
			DBUtil dbUtil=new DBUtil();
			Field []  fields = clazz.getDeclaredFields();
			List<BaseEntity> list = dbUtil.Query(clazz, dataname, request);
			int index=0;
			for (BaseEntity baseEntity : list) {
				fields[index].setAccessible(true);
				//System.out.println(fields[0].get(baseEntity)+"___"+fields[0].get(entity));
				if(fields[0].get(baseEntity)==fields[0].get(entity)){
					dbUtil.UpdateSong(entity, dataname);
					index++;
					break;
				}
			}
			if(index==0){
				dbUtil.SaveSong(entity,dataname);
			}
			
		}
		

	public DBUtil(){
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
