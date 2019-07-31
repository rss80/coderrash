package royal.servlet;

import java.io.IOException;
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
import royal.database.databaseconn;

@WebServlet(name = "loadevent", urlPatterns = {"/loadevent"})
public class loadevent extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String unique_event = request.getParameter("unique_event");
        //String unique_event = "cr000000000000000000xf12";
        databaseconn d = new databaseconn();
        d.set_sqlstatement("SELECT \"PARTICIPANT1\",\"PARTICIPANT2\" FROM CODERRASH.\"EVENT_HOST\" where \"UNIQUE_EVENT_ID\" = ?");
        Map<Integer, Object> param = new HashMap<>();
        param.put(1, unique_event);
        d.set_sqlparameters(param);
        d.process_select_Query();
        String participant1 = "";
        String participant2 = "";
        try {
            if (d.rs.next()) {
                participant1 = d.rs.getString("PARTICIPANT1");
                participant2 = d.rs.getString("PARTICIPANT2");
            }
            RequestDispatcher rd = request.getRequestDispatcher("adminviewhisevents.jsp?message=" + unique_event + "~" + participant1 + "~" + participant2);
            rd.forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(loadevent.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            d.invalidate();
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
