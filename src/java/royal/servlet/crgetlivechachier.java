package royal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import royal.context.coderscode.competecoderscode;
import royal.context.competecache.competecache;

@WebServlet(name = "crgetlivechachier", urlPatterns = {"/crgettinglivechachier"})
public class crgetlivechachier extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            try {
                String participant_name = request.getParameter("participant_name");
                String unique_event_id = request.getParameter("unique_event_id");
                ServletContext ctx = getServletContext();
                competecache c = (competecache) ctx.getAttribute("context_cache_cr");
                competecoderscode other = c.getCache_codersrash_compete_codercode(unique_event_id + participant_name);
                String other_code = "nOt YeT sTaRtEd";
                int question_number_other = 0;
                if (other != null) {
                    other_code = other.getCoders_code();
                    question_number_other = other.getQuestion_number();
                }
                other_code = other_code.replaceAll("\\&", "&amp;");
                other_code = other_code.replaceAll("\"", "&quot;");
                other_code = other_code.replaceAll("\\<", "&lt;");
                other_code = other_code.replaceAll("\\>", "&gt;");
                other_code = other_code.replaceAll("\\'", "&apos");

                String out_xml;
                out_xml = "<coderrash_xml>"
                        + "<coders_code>"
                        + "<question_number>"
                        + question_number_other
                        + "</question_number>"
                        + "<code_live>"
                        + other_code
                        + "</code_live>"
                        + "</coders_code>"
                        + "</coderrash_xml>";
                out.print(out_xml);
            } catch (Exception ex) {
                Logger.getLogger(crgetlivechachier.class.getName()).log(Level.SEVERE, null, ex);
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
