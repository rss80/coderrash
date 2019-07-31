/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package royal.handlers;

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
import javax.servlet.http.HttpSession;
import royal.bean.Crbean;
import royal.context.coderscode.competecoderscode;
import royal.context.competecache.competecache;

@WebServlet(name = "royalhandler", urlPatterns = {"/crchachier"})
public class royalhandler extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        try (PrintWriter out = response.getWriter()) {
            if (session == null) {
                out.print("session_expired");
            } else {
                try {
                    String coder_code = request.getParameter("code");
                    if (coder_code == null) {
                        coder_code = "";
                    }
                    Crbean cr = (Crbean) session.getAttribute("coderrashbean");
                    String unique_code = cr.getUnique_code();
                    String other_coder = cr.getOther_coder();
                    String coder = cr.getName();
                    int question_number = cr.getQuestion();
                    ServletContext ctx = getServletContext();
                    competecache c = (competecache) ctx.getAttribute("context_cache_cr");
                    competecoderscode other = c.getCache_codersrash_compete_codercode(unique_code + other_coder);
                    String other_code = "nOt YeT sTaRtEd";
                    int question_number_other = 0;
                    if (other != null) {
                        other_code = other.getCoders_code();
                        question_number_other = other.getQuestion_number();
                    }
                    competecoderscode self = c.getCache_codersrash_compete_codercode(unique_code + coder);
                    self.setCoders_code(coder_code);
                    self.setQuestion_number(question_number);
                    c.updateCache_codersrash(unique_code + coder, self);
                    
                    other_code = other_code.replaceAll("\\&", "&amp;");
                    other_code = other_code.replaceAll("\"", "&quot;");
                    other_code = other_code.replaceAll("\\<", "&lt;");
                    other_code = other_code.replaceAll("\\>", "&gt;");
                    other_code = other_code.replaceAll("\\'", "&apos");
                    
                    String out_xml = "";
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
                    Logger.getLogger(coderrashquestionHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
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
