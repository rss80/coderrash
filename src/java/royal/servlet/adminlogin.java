/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package royal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import royal.database.*;
import royal.bean.*;

@WebServlet(name = "adminlogin", urlPatterns = {"/adminlogin"})
public class adminlogin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        databaseconn dc = new databaseconn();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String name;
            int hid;
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String query = "SELECT * FROM HOST_ACCOUNT where EMAIL = ? and PASSWORD = ?";
            Map<Integer, Object> m = new HashMap();
            ResultSet resultset;
            m.put(1, email);
            m.put(2, password);
            dc.set_sqlstatement(query);
            dc.set_sqlparameters(m);
            dc.process_select_Query();
            if (dc.rs.next()) {
                hid = dc.rs.getInt("H_ID");
                name = dc.rs.getString("NAME");
                HttpSession Session = request.getSession(true);
                Crbean crbean = new Crbean(hid, name);
                Session.setAttribute("coderrashbean", crbean);
                //out.println("<h2> user successfully logged in " +"</h2>");
                Crbean cc = (Crbean) Session.getAttribute("coderrashbean");
                //out.println("<h3>" +cc.getName()  +"</h3>");
                RequestDispatcher rd = request.getRequestDispatcher("dashboard.jsp");
                rd.forward(request, response);
            }
            else
            {
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp?message=login failed");
                rd.forward(request, response);
            }
            
        }
        catch(Exception e)
        {
            Logger.getLogger(adminlogin.class.getName()).log(Level.SEVERE, null, e);
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp?message=Some error occured on our end. Please try again");
            rd.forward(request, response);
        }
        finally
        {
            dc.invalidate(); 
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(adminlogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(adminlogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
