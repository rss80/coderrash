package royal.servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import royal.bean.Crbean;
import royal.coders.competerashers;
import royal.context.coderscode.competecoderscode;
import royal.context.competecache.competecache;
import royal.database.databaseconn;

@WebServlet(urlPatterns = {"/crashcompete/*"})
public class royalcompe extends HttpServlet {

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
        String compete_path = request.getPathInfo();
        if (compete_path != null) {
            compete_path = compete_path.replaceAll("/", "");
            String competitor_name = request.getParameter("coder_name");
            databaseconn d = new databaseconn();
            d.set_sqlstatement("SELECT \"PARTICIPANT1\",\"PARTICIPANT2\",\"TOTAL_TIME\",\"EVENT_STATUS\" FROM CODERRASH.\"EVENT_HOST\" where \"UNIQUE_EVENT_ID\" = ? and (\"PARTICIPANT1\" = ? OR \"PARTICIPANT2\" = ?)");
            Map<Integer, Object> param = new HashMap<>();
            param.put(1, compete_path);
            param.put(2, competitor_name);
            param.put(3, competitor_name);
            d.set_sqlparameters(param);
            d.process_select_Query();
            String output = null;
            String other_coder = null;
            String event_status = "0";
            String totaltime = "0";
            try {
                if (d.rs.next()) {
                    output = "1";
                    other_coder = d.rs.getString("PARTICIPANT1");
                    if (other_coder != null && other_coder.equals(competitor_name)) {
                        other_coder = d.rs.getString("PARTICIPANT2");
                    }
                    totaltime = d.rs.getString("TOTAL_TIME");
                    event_status = d.rs.getString("EVENT_STATUS");
                }

                response.setContentType("text/html;charset=UTF-8");
                if (output != null && output.equals("1")) {
                    if (event_status.equals("1")) {
                        competecache comr = new competecache();
                        competecoderscode ccc = new competecoderscode("nOt YeT sTaRtEd", compete_path, competitor_name, 0);
                        comr.insert_Cache_codersrash(compete_path + competitor_name, ccc);

                        HttpSession session = request.getSession(true);
                        Crbean cr = new Crbean(competitor_name, compete_path, other_coder);
                        session.setAttribute("coderrashbean", cr);
                        session.setMaxInactiveInterval(900);
                        RequestDispatcher rd = request.getRequestDispatcher("/competedash.jsp?message=" + competitor_name + "&othercoder=" + other_coder + "&totaltime=" + totaltime);
                        rd.forward(request, response);
                    } else {
                        response.sendRedirect("/coderrash/rashers/" + compete_path + "?message=" + URLEncoder.encode("Your event has not benn started by the host.", "UTF-8"));
                    }

                } else {
                    //RequestDispatcher rd = request.getRequestDispatcher("/rashers/"+compete_path+"?message=Your name is not associated with this event. Please contact your event host.");
                    //rd.include(request, response);
                    response.sendRedirect("/coderrash/rashers/" + compete_path + "?message=" + URLEncoder.encode("Your name is not associated with this event. Please contact your event host.", "UTF-8"));
                }

            } catch (SQLException ex) {
                Logger.getLogger(competerashers.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                d.invalidate();
            }
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/invalidurl.jsp?message=Invalid URL. Try- http://localhost:8080/coderrash/");
            rd.forward(request, response);
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
