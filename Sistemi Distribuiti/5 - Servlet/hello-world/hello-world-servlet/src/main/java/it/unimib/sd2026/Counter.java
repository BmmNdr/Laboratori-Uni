package it.unimib.sd2026;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/counter")
public class Counter extends HttpServlet {
    private final Object counterLock = new Object();
    private int counter;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException
    {
        int value = 0;
        synchronized (counterLock) {
            value = counter;
            counter++;
        }

        var buf = new StringBuilder();
        buf.append("<!DOCTYPE html>\n");
        buf.append("<html>\n<head>\n<title>Counter Servlet (Sistemi Distribuiti 2025-2026)</title>\n</head>\n<body>\n");
        buf.append("<h1>Counter Servlet (Sistemi Distribuiti 2025-2026)</h1>\n");
        buf.append("<p>This is a counter: " + value);
        buf.append("</p>\n");
        buf.append("<p><a href=\"counter\">Reload</a> to increment the counter.</p>");

        response.setContentType("text/html");
        response.getWriter().append(buf.toString());
    }
}
