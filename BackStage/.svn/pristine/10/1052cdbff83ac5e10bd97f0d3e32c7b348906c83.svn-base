package com.shadow.Utils.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.shadow.Utils.DBUtil;
import com.shadow.Utils.Entity.LoadClass;
import com.shadow.Utils.Entity.Song;

/**
 * Servlet implementation class SongServlet
 */
@WebServlet("/song.do")
public class SongServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SongServlet() {
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
		try {
			List<Song> list = dbUtil.Query(clazz,parameter,request);
			int total = dbUtil.QueryTotal("select count(*) total  from song");
			Map<String, Object> map=new HashMap<>();
			map.put("total", total);
			map.put("rows", list);
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(JSON.toJSONString(map));
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
