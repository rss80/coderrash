/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package royal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import royal.database.*;

@WebServlet(name = "adminsignup", urlPatterns = {"/adminsignup"})
public class adminsignup extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        databaseconn dc = new databaseconn();
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            String firstname, lastname, company, password, email;
            firstname = request.getParameter("first_name");
            lastname = request.getParameter("last_name");
            company = request.getParameter("company_name");
            password = request.getParameter("password");
            email = request.getParameter("email");
            Map<Integer, Object> m = new HashMap();
            int resultset;
            m.put(1,firstname + " " +lastname);
            m.put(2,email);
            m.put(3,company);
            m.put(4,password);
            String query = "INSERT INTO HOST_ACCOUNT( NAME, EMAIL, COMPANY_NAME, PASSWORD) VALUES(?,?,?,?)";
            dc.set_sqlstatement(query);
            dc.set_sqlparameters(m);
            resultset = dc.process_update_Query();
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp?message=Signup Successful");  
            rd.forward(request, response); 
        }
        catch(Exception e)
        {
            Logger.getLogger(adminsignup.class.getName()).log(Level.SEVERE, null, e);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
