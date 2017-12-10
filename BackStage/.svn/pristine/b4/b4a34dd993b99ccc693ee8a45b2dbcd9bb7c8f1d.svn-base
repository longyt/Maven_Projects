package com.shadow.Utils.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.shadow.Utils.DBUtil;
import com.shadow.Utils.Entity.BaseEntity;
import com.shadow.Utils.Entity.Bundle;
import com.shadow.Utils.Entity.Contain;
import com.shadow.Utils.Entity.LoadClass;
import com.shadow.Utils.Entity.Song;
import com.shadow.Utils.Entity.Topic;
import com.shadow.Utils.Entity.User;

/**
 * Servlet implementation class SelectTopicServlet
 */
@WebServlet("/Select.do")
public class SelectTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectTopicServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String parameter=request.getParameter("clazz");
		
		Class clazz =new LoadClass().Loadclass(parameter);
		
		DBUtil dbUtil=new DBUtil();
		List<Bundle> list=null;
		try {
			if(clazz.equals(Bundle.class)&&parameter.equals(Contain.bundle)){
				list= dbUtil.Query(clazz,parameter,request);
				for (Bundle bundle1 : list) {
					int total = dbUtil.QueryTotal("select count(*) total from bundle where pid = "+bundle1.getId());
					if(total>0){
						bundle1.setIsParent(true);
					}else{
						bundle1.setIsParent(false);
					}
				}
			}else{
				list = dbUtil.Query(clazz,parameter,request);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(JSON.toJSONString(list));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
