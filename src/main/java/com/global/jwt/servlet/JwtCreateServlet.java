package com.global.jwt.servlet;

import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.global.jwt.service.JwtService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/jwt/create")
public class JwtCreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    	try {
			JwtService.init(req);
			String access  = JwtService.createAccessToken("root");
			String refresh = JwtService.createRefreshToken("root");
			ObjectMapper mapper = new ObjectMapper();
            ObjectNode rootNode = mapper.createObjectNode();
            rootNode.put("access", access);
            rootNode.put("refresh", refresh);
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            PrintWriter pw = resp.getWriter();
			pw.println(jsonString);
			pw.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}

