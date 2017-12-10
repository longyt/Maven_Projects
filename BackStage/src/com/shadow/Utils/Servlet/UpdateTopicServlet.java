package com.shadow.Utils.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shadow.Utils.DBUtil;
import com.shadow.Utils.Entity.BaseEntity;
import com.shadow.Utils.Entity.LoadClass;
import com.shadow.Utils.Entity.Song;
import com.shadow.Utils.Entity.Topic;
import com.shadow.Utils.Entity.User;

/**
 * Servlet implementation class UpdateTopicServlet
 */
@WebServlet("/UpdateServlet.do")
public class UpdateTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Class UserClass=User.class;
	private static Class SongClass=Song.class;
	private static Class TopicClass=Topic.class;
	public static String user="user";
	public static String song="song";
	public static String topic="topic";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTopicServlet() {
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
			BaseEntity bentity = (BaseEntity) dbUtil.InsertSstuEntity(request, clazz);
			dbUtil.UpdateSong(bentity,parameter) ;
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
