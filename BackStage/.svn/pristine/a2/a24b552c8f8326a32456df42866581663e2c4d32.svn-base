package com.shadow.Utils.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.shadow.Utils.DBUtil;
import com.shadow.Utils.Entity.BaseEntity;
import com.shadow.Utils.Entity.Contain;
import com.shadow.Utils.Entity.LoadClass;
import com.shadow.Utils.Entity.Song;
import com.shadow.Utils.Entity.Topic;
import com.shadow.Utils.Entity.User;

/**
 * Servlet implementation class AddServlet
 */
@WebServlet("/add.do")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String parameter=request.getParameter("clazz");
		
		Class clazz =new LoadClass().Loadclass(parameter);
		
		DBUtil dbUtil =new DBUtil();
		try {
			BaseEntity baseEntity =  (BaseEntity) dbUtil.InsertSstuEntity(request,clazz);
			dbUtil.SaveSong(baseEntity, parameter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", Contain.success);
		response.getWriter().println(jsonObject);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
