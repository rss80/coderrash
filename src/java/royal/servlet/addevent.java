/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package royal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.websocket.Session;
import royal.bean.*;
import royal.database.databaseconn;

@WebServlet(name = "addevent", urlPatterns = {"/addevent"})
public class addevent extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
         {
        response.setContentType("text/html;charset=UTF-8");
        databaseconn dc = new databaseconn();
        PrintWriter out = response.getWriter();
        try {
            String question1,ueid,question2,question3,player1,player2,event_name;
            HttpSession session = request.getSession(true);
            Crbean cb = (Crbean) session.getAttribute("coderrashbean"); 
            int time,hid = cb.getHid(); 
            event_name = request.getParameter("event_name");
            time = Integer.parseInt(request.getParameter("time"));
            ueid = request.getParameter("eventhash");
            question1 = request.getParameter("question1");
            question2 = request.getParameter("question2");
            question3 = request.getParameter("question3");
            player1 = request.getParameter("player1").toLowerCase();
            player2 = request.getParameter("player2").toLowerCase();
            String query = "INSERT INTO EVENT_HOST(H_ID,UNIQUE_EVENT_ID, EVENT_NAME, PARTICIPANT1, PARTICIPANT2, TOTAL_TIME) VALUES(?,?,?,?,?,?)";
            Map<Integer, Object> m = new HashMap();
            m.put(1,hid);
            m.put(2,ueid);
            m.put(3,event_name);
            m.put(4,player1);
            m.put(5,player2);
            m.put(6,time);
            dc.set_sqlstatement(query);
            dc.set_sqlparameters(m);
            int resultset = dc.process_update_Query();
            query = "INSERT INTO EVENT_QUESTION(UNIQUE_EVENT_ID,QUESTIONS, QUESTION_NUMBER) VALUES(?,?,1)";
            Map<Integer, Object> m1 = new HashMap();
            m1.put(1, ueid);
            m1.put(2, question1);
            dc.set_sqlstatement(query);
            dc.set_sqlparameters(m1);
            resultset = dc.process_update_Query();
            query = "INSERT INTO EVENT_QUESTION(UNIQUE_EVENT_ID,QUESTIONS, QUESTION_NUMBER) VALUES(?,?,2)";
            Map<Integer, Object> m2 = new HashMap();
            m2.put(1, ueid);
            m2.put(2, question2);
            dc.set_sqlstatement(query);
            dc.set_sqlparameters(m2);
            resultset = dc.process_update_Query();
            query = "INSERT INTO EVENT_QUESTION(UNIQUE_EVENT_ID,QUESTIONS, QUESTION_NUMBER) VALUES(?,?,3)";
            Map<Integer, Object> m3 = new HashMap();
            m3.put(1, ueid);
            m3.put(2, question3);
            dc.set_sqlstatement(query);
            dc.set_sqlparameters(m3);
            resultset = dc.process_update_Query();
        }
        catch(Exception e)
        {
            Logger.getLogger(addevent.class.getName()).log(Level.SEVERE, null, e);
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp?message=Some error occured on our end. Please try again");
            rd.forward(request, response);
        }
        finally
        {
            dc.invalidate(); 
        }
            RequestDispatcher rd=request.getRequestDispatcher("dashboard.jsp");  
            rd.forward(request, response);
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
