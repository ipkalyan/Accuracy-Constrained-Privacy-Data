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
public class Registration extends HttpServlet {

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
            String button = request.getParameter("submit");

            if (button.equalsIgnoreCase("Next")) {
                
                String userId = request.getParameter("userid");
                String pass = request.getParameter("passid");
                String name = request.getParameter("username");
                String address = request.getParameter("address");
                String country = request.getParameter("country");
                String zip = request.getParameter("zip");
                String email = request.getParameter("email");
                String salary = request.getParameter("salary");
                String sex = request.getParameter("sex");
                String language = request.getParameter("en");
                String about = request.getParameter("desc");
                String status = "no";                
                

              
                if (!email.contains("@")) {
                    response.sendRedirect(web.Resource.getmyIp()+"index.jsp?msg="+"<br><b>Invalid email id. Plz use @ symbol.");                    
                    return;
                }
                Class.forName(web.Resource.getdbDriver());

                Connection conn = DriverManager.getConnection(web.Resource.getDbUrl(), web.Resource.getDbUser(), web.Resource.getDbPass());
                conn.setAutoCommit(false);
                Statement stmt = conn.createStatement();                
                
                String queryz = " select * from users where userId='"+userId+"'";
                ResultSet rsett = stmt.executeQuery(queryz);

                if (rsett.next()) {
                    response.sendRedirect(web.Resource.getmyIp()+"index.jsp?msg="+"<br><b>Invalid User id. Already Exists. Please Choose Another");                                        
                    return;
                }

                String insert = "insert into  users (userId,password,name,address,country,zip,email,salary,sex,language,about,status) values";
                insert += "('"+userId+"','" + pass + "','" + name + "','" + address + "','" + country + "','" + zip + "','" + email + "','" + salary + "','" + sex + "','" + language + "','" + about + "','" + status + "')";
                
                
                //String ct1 = "create table " + userId + "_images (no smallint(8) unsigned NOT NULL, imgPath text, xno text, yno text,status text)";
                //out.println("<br><b>" + insert + "</b>");
                //String ct2 = "create table " + userId + "_sounds (no smallint(8) unsigned NOT NULL, soundPath text,status text)";
              
                stmt.executeUpdate(insert);
                //stmt.executeUpdate(ct1);
                //stmt.executeUpdate(ct2);
                
                conn.commit();
                stmt.close();
                conn.close();                
                
                response.sendRedirect(web.Resource.getmyIp()+"index.jsp?msg=Successfully Regsitered.<br />Please Wait Untill(24 man hours) Admin validates your account");
            }
            
            if (button.equalsIgnoreCase("Cancel")) {
             response.sendRedirect(web.Resource.getmyIp()+"index.jsp");
            }
        } catch (SQLException e) {
            out.println("SQLException=" + e.toString());
            response.sendRedirect(web.Resource.getmyIp()+"registration.jsp?msg=" + e.toString());
        } catch (ClassNotFoundException e) {
            out.println(" ClassNotFoundException =" + e.toString());
            response.sendRedirect(web.Resource.getmyIp()+"registration.jsp?msg=" + e.toString());
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
