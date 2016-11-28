package net.scientifichooliganism.ssorgame;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


import javax.servlet.*;
import javax.servlet.http.*;

public class SSORRestSvc extends HttpServlet {
	private Logger logger;
	private ConcurrentHashMap<String, SSORGame> games;

	public void init() throws ServletException {
		try {
			logger = Logger.getLogger(this.getClass().getName());
			games = new ConcurrentHashMap<String, SSORGame>();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = null;
		String webMethod = request.getPathInfo();
		SSORGame game = null;

		if (webMethod != null) {
			webMethod = webMethod.substring(webMethod.lastIndexOf("/") + 1);
		}

		if (webMethod.equals("newGame")) {
			game = new SSORGame(request.getParameter("gameName"), SSORGame.SINGLE);
			games.putIfAbsent(game.getId(), game);
		}
		else {
			game = games.get(request.getParameter("gameName"));

			if (game == null) {
				throw new RuntimeException("specified game does not exist");
			}
		}

		switch (webMethod) {
			case "newGame":
				//this just prevents any further action being taken
				//in the case of a new game.
				break;
			case "setGameType":
				game.setGameType(request.getParameter("gameType"));
				break;
			case "setPoints":
				//
				break;
			case "addPlayer":
				//
				break;
			case "addTeam":
				//
				break;
			case "startGame":
				//
				break;
			case "getPoint":
				//
				break;
			case "getPoints":
				//
				break;
			case "stopGame":
				//
				break;
			case "endGame":
				//
				break;
			case "":
				//
				break;
			default:
				throw new RuntimeException("unrecognized web method: " + webMethod);
		}

		try {
			response.setContentType("text/json");
			out = response.getWriter();
		}
		catch (Exception exc) {
			logger.throwing(this.getClass().getName(), "doGet", exc);
		}

		if (out != null) {
			out.println("<html>");
			out.println("	<body>");
			out.println("		webMethod: " + webMethod + "<br>");
			out.println("	</body>");
			out.println("</html>");
		}
	}

	public void destroy() {
		// do nothing.
	}
}

//C:\scratch\rest_servlet-01>javac -classpath C:\scratch\apache-tomcat-8.0.29\lib\servlet-api.jar -d bin src\example.java