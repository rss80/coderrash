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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import royal.database.databaseconn;

/**
 *
 * @author Ajay
 */
@WebServlet(name = "finddatabaseentries", urlPatterns = {"/getentries"})
public class finddatabaseentries extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        databaseconn d = new databaseconn();
        String participant_name = request.getParameter("participant_name");
        String unique_event_id = request.getParameter("unique_event_id");
        try (PrintWriter out = response.getWriter()) {
            try {
                d.set_sqlstatement("SELECT QUESTION_NUMBER, CODE FROM CODERRASH.\"CODE_BASE\" where \"UNIQUE_EVENT_ID\" = ? and  \"PARTICIPANT\" = ?");
                Map<Integer, Object> param_1 = new HashMap<>();
                param_1.put(1, unique_event_id);
                param_1.put(2, participant_name);
                d.set_sqlparameters(param_1);
                d.process_select_Query();
                String output = "";
                while (d.rs.next()) {
                    output = output + "\n\n\n\n Question: " + d.rs.getString("QUESTION_NUMBER");
                    output = output + "\n\n Code: \n\n" + d.rs.getString("CODE");
                }
                if (output == "") {
                    output = "None Submitted";
                }
                out.print(output);
            } catch (Exception ex) {
                Logger.getLogger(finddatabaseentries.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                d.invalidate();
            }
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
