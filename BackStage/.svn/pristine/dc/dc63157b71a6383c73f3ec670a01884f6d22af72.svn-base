package com.shadow.Utils.Servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import com.shadow.Utils.Entity.Song;
import com.shadow.Utils.Entity.Topic;
import com.shadow.Utils.Entity.User;

/**
 * Servlet implementation class ExportServlet
 */
@WebServlet("/ExportServlet.do")
public class ExportServlet extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			DBUtil dbUtil=new DBUtil();
			List<BaseEntity> list;
			String parameter=request.getParameter("clazz");
			Class clazz =new LoadClass().Loadclass(parameter);
			try {
				System.out.println(parameter);
				list = dbUtil.Query(clazz, parameter,request);//拿到数据
				Contain.InitData(clazz,parameter);//实例化模板
				ExcelUtils.Export(list,request,clazz);//开始导出
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
