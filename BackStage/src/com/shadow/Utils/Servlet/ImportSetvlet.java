package com.shadow.Utils.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shadow.Utils.DBUtil;
import com.shadow.Utils.ExcelUtils;
import com.shadow.Utils.Entity.BaseEntity;
import com.shadow.Utils.Entity.Contain;
import com.shadow.Utils.Entity.LoadClass;
import com.shadow.Utils.Entity.Topic;

/**
 * Servlet implementation class ImportSetvlet
 */
@WebServlet("/ImportSetvlet.do")
public class ImportSetvlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportSetvlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ExcelUtils excelUtils=new ExcelUtils();
		
		String parameter=request.getParameter("clazz");
		
		Class clazz =new LoadClass().Loadclass(parameter);
		
		String filename = request.getParameter("filename");
		String resurce=request.getServletContext().getRealPath("/export");
		
		
		DBUtil dbUtil=new DBUtil();
		List<BaseEntity> list;
		try {
			Contain.InitData(clazz, parameter);
			list = ExcelUtils.Import(resurce+"/"+filename,clazz);
			for (BaseEntity baseEntity : list) {
				dbUtil.Import(baseEntity, clazz, parameter, request);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().write("{\"percent\":\""+Contain.percent+"\"}");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
