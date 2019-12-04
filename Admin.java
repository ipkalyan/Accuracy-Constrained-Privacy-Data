/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
public class Admin extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String userId = request.getParameter("userId");

            userId = (userId == null ? "You are Trying To Hack.Leave Immediately" : userId);

            Class.forName(web.Resource.getdbDriver());

            Connection conn = DriverManager.getConnection(web.Resource.getDbUrl(), web.Resource.getDbUser(), web.Resource.getDbPass());
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Admin</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center><h2><font color=blue>Website Access Details</font></h2><br/><hr>");

            String queryz = " select * from users";
            ResultSet rsett = stmt.executeQuery(queryz);
            ResultSetMetaData metaData = rsett.getMetaData();
            int columns = metaData.getColumnCount();
            String html = "<table border=1>";

            if (rsett.next()) {
                for (int i = 1; i <= columns; i++) {
                    html += "<th>" + metaData.getColumnName(i) + "</th>";

                }

            }

            rsett.beforeFirst();
            while (rsett.next()) {
                html += "<tr>";
                for (int i = 1; i <= columns; i++) {
                    html += "<td>" + rsett.getString(i) + "</td>";
                }
                html += "</tr>";
            }

            html += "</table>";
            out.println(html);
            out.println("</center></body>");           
            out.println("</html>");
            
            
            
            

        } catch (SQLException e) {
            out.println("SQLException=" + e.toString());
            response.sendRedirect(web.Resource.getmyIp() + "index.jsp?msg=" + e.toString());
        } catch (ClassNotFoundException e) {
            out.println(" ClassNotFoundException =" + e.toString());
            response.sendRedirect(web.Resource.getmyIp() + "index.jsp?msg=" + e.toString());
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
