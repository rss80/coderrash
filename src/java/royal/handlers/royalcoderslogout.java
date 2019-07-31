package royal.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import royal.bean.Crbean;
import royal.context.coderscode.competecoderscode;
import royal.context.competecache.competecache;
import royal.database.databaseconn;

@WebServlet(name = "royalcoderslogout", urlPatterns = {"/crremovelive"})
public class royalcoderslogout extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        databaseconn d = new databaseconn();
        try {
            if (session != null) {
                Crbean cr = (Crbean) session.getAttribute("coderrashbean");
                String unique_code = cr.getUnique_code();
                String coder = cr.getName();
                ServletContext ctx = getServletContext();
                competecache c = (competecache) ctx.getAttribute("context_cache_cr");

                competecoderscode self = c.getCache_codersrash_compete_codercode(unique_code + coder);
                int cache_question = self.getQuestion_number();
                String cache_code = self.getCoders_code();
                if (cache_question != 0) {
                    d.set_sqlstatement("Update CODERRASH.\"CODE_BASE\" set \"CODE\" = ? where \"UNIQUE_EVENT_ID\" = ? and \"PARTICIPANT\" = ? and \"QUESTION_NUMBER\" = ?");
                    Map<Integer, Object> param1 = new HashMap<>();
                    param1.put(1, cache_code);
                    param1.put(2, unique_code);
                    param1.put(3, coder);
                    param1.put(4, cache_question);
                    d.set_sqlparameters(param1);
                    int return_rs_update = d.process_update_Query();
                }

                c.destroy_Cache_codersrash_coders(unique_code + coder);
                session.invalidate();
            }
        } catch (Exception e) {
            Logger.getLogger(royalcoderslogout.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            d.invalidate();
        }
        response.sendRedirect("/coderrash");
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
