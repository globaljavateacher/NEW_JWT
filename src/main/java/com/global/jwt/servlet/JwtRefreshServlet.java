package com.global.jwt.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.global.jwt.service.JwtService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/jwt/refresh")
public class JwtRefreshServlet extends HttpServlet {
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    	try {
	        String refreshToken = req.getParameter("refresh");
	        var decoded = JwtService.verify(refreshToken);
	        String newAccess = JwtService.createAccessToken(decoded.getSubject());
	        ObjectMapper mapper = new ObjectMapper();
            ObjectNode rootNode = mapper.createObjectNode();
            rootNode.put("access", newAccess);
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            PrintWriter pw = resp.getWriter();
            pw.println(jsonString);
			pw.close();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
}

