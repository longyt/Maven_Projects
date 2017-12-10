package com.shadow.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;

import com.shadow.Utils.Entity.BaseEntity;
import com.shadow.Utils.Entity.Contain;

public class ExcelUtils {
	private static Short red=(Short) HSSFColorPredefined.RED.getIndex();
	private static Short green=(Short) HSSFColorPredefined.GREEN.getIndex();
	private static Short blur=(Short) HSSFColorPredefined.BLUE.getIndex();
	private static Short white=(Short) HSSFColorPredefined.WHITE.getIndex();
	
	/**
	 * 导出数据
	 * @throws Exception 
	 */
	public static void Export(List<BaseEntity> list,HttpServletRequest resuest,Class clazz) throws Exception{
		

		
		Class.forName("com.shadow.Utils.Entity.Contain");
		
		HSSFWorkbook hssfWorkbook= new HSSFWorkbook();
		
		HSSFSheet hssfSheet = hssfWorkbook.createSheet();//拿到第一幅
		
		HSSFRow OneRow = hssfSheet.createRow(0);//拿到第一行
		
		HSSFCell  OneCell= OneRow.createCell(0, CellType.STRING);
		
		CustomFontAndBackgroundColor(hssfWorkbook,OneCell,blur,(short) 30);//设置字体颜色
		
		OneCell.setCellValue("导出"+clazz.getSimpleName()+"表中的数据");
		
		//拿到标题
		HSSFRow TitleRow = hssfSheet.createRow(1);
		
		int TitleRowIndex=0;
		for (int i = 0; i < Contain.list.size(); i++) {
			HSSFCell TitleCell = TitleRow.createCell(TitleRowIndex, CellType.STRING);
			TitleCell.setCellValue((String) Contain.list.get(i).get("title"+(i+1)));
			TitleRowIndex++;
		}
			
		
		//拿到标题下的数据
		int RowIndex=2;
		for (BaseEntity entity : list) {
			HSSFRow DataRow = hssfSheet.createRow(RowIndex);
			int CellIndex=0;
			Class clabb = entity.getClass();
			Field [] fields = clabb.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if(field.getType().getSimpleName().equals("Integer")){
					Integer Tempvalue = (Integer) field.get(entity);
					DataRow.createCell(CellIndex, CellType.NUMERIC).setCellValue(Tempvalue);
				}else{
					String Tempvalue = (String) field.get(entity);
					DataRow.createCell(CellIndex, CellType.STRING).setCellValue(Tempvalue);
				}
				CellIndex++;
			}
			RowIndex++;
		}
		
		
		
		String ExportPath = resuest.getServletContext().getRealPath("//export");
		File file=new File(ExportPath);
		if(!file.exists()){
			file.mkdirs();
		}
		Contain.ExportResurcePath=ExportPath;
		
		System.out.println(ExportPath);
		
		File ExportFile = new File(Contain.ExportResurcePath+"//"+clazz.getSimpleName()+".xls");
		OutputStream fos=new FileOutputStream(ExportFile);
		hssfWorkbook.write(fos);
		if(!ExportFile.exists()){
			ExportFile.createNewFile();
		}
		fos.flush();
		fos.close();
		hssfWorkbook.close();
	}
	
	
	public static List<BaseEntity> Import(String ImportFileName,Class clazz) throws Exception{
		
		/**
		 * 定义一个list
		 */
		List<BaseEntity> list=new ArrayList<>();
		
		File file=new File(ImportFileName);
		InputStream in=new FileInputStream(file);
		HSSFWorkbook hssfWorkbook=new HSSFWorkbook(in);//拿到xls文件
		
		int sheets = hssfWorkbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(i);
			HSSFRow OneRow = hssfSheet.createRow(0);
			int OneRowCells = OneRow.getLastCellNum();
			if(OneRowCells>1){
				System.out.println("格式错误");
			}else{
				HSSFRow TwoRow = hssfSheet.getRow(1);
				int TwoRowCells = TwoRow.getLastCellNum();
				for (int j = 0; j < TwoRowCells; j++) {
					String TitleName = TwoRow.getCell(j).getStringCellValue();
					if(TitleName.equals(Contain.list.get(j).get("title"+(j+1)))){
						//System.out.println("模板正确");
					}else{
						System.out.println("模板错误");
					}
				}
			}
			
			/**
			 * 遍历有多少行遍历所有数据放入list中返回
			 */
			int AllRows = hssfSheet.getLastRowNum();
			for (int j = 2; j <= AllRows; j++) {
					if(j%3==0){
						Thread.sleep(1000);
					}
				HSSFRow Row = hssfSheet.getRow(j);
				int Allcells = Row.getLastCellNum();
				BaseEntity entity2 = (BaseEntity) clazz.newInstance();
				for (int k = 0; k < Allcells; k++) {
					HSSFCell cell = Row.getCell(k);
					String FieldName = (String) Contain.list.get(k).get("name"+(k+1));
					Field field = clazz.getDeclaredField(FieldName);
					field.setAccessible(true);
					
					if(cell.getCellTypeEnum()==CellType.NUMERIC){
						Integer Tempvalue = (int) cell.getNumericCellValue();
						if(field.getType().getSimpleName().equals("Integer")){
							field.set(entity2, Tempvalue);
						}else if(field.getType().getSimpleName().equals("String")){
							field.set(entity2, Tempvalue.toString());
						}
					}else if(cell.getCellTypeEnum()==CellType.STRING){
						String Tempvalue = (String)cell.getStringCellValue();
						if(field.getType().getSimpleName().equals("Integer")){
							field.set(entity2, Tempvalue);
						}else if(field.getType().getSimpleName().equals("String")){
							field.set(entity2, Tempvalue.toString());
						}
					}
					
				}
				Contain.percent=((j*1.0)/(AllRows*1.0))*100;
				list.add(entity2);
				
			}//遍历数据for循环结束
			
		}//遍历sheet循环结束
		
		return list;
	}
	
		//字体颜色大小设置
		public static void CustomFontAndBackgroundColor(HSSFWorkbook hssfWorkbook,HSSFCell hssfCell,Short color,Short fontsize){
					//设置背景颜色
					CellStyle cellStyle = hssfWorkbook.createCellStyle();
					//cellStyle.setFillBackgroundColor(HSSFColorPredefined.RED.getIndex());
					short alignCenter = CellStyle.VERTICAL_TOP;
					cellStyle.setAlignment(alignCenter);
					hssfCell.setCellStyle(cellStyle);
					//设置字体颜色和大小
					HSSFFont hssfFont = hssfWorkbook.createFont();
					hssfFont.setColor(color);
					hssfFont.setFontHeightInPoints(fontsize);
					cellStyle.setFont(hssfFont);
		}

}
