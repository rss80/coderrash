package royal.handlers;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet(name = "coderrashquestionHandler", urlPatterns = {"/royalcoderrashquestion"})
public class coderrashquestionHandler extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        databaseconn d = new databaseconn();
        HttpSession session = request.getSession(false);
        try (PrintWriter out = response.getWriter()) {
            if (session == null) {
                out.print("session_expired");
            } else {
                try {
                    Crbean cr = (Crbean) session.getAttribute("coderrashbean");
                    int question_number = cr.getQuestion();
                    String unique_code = cr.getUnique_code();
                    question_number++;
                    if (question_number < 4) {
                        cr.setQuestion(question_number);
                        session.setAttribute("coderrashbean", cr);
                        d.set_sqlstatement("SELECT QUESTIONS, Q_ID FROM CODERRASH.\"EVENT_QUESTION\" where \"UNIQUE_EVENT_ID\" = ? and  \"QUESTION_NUMBER\" = ?");
                        Map<Integer, Object> param_1 = new HashMap<>();
                        param_1.put(1, unique_code);
                        param_1.put(2, question_number);
                        d.set_sqlparameters(param_1);
                        d.process_select_Query();
                        String output = null;
                        String q_id = null;
                        if (d.rs.next()) {
                            output = d.rs.getString("QUESTIONS");
                            q_id = d.rs.getString("Q_ID");
                        }
                        if (output != null) {
                            String coder = cr.getName();
                            ServletContext ctx = getServletContext();
                            competecache c = (competecache) ctx.getAttribute("context_cache_cr");
                            competecoderscode self = c.getCache_codersrash_compete_codercode(unique_code + coder);
                            int cache_question = self.getQuestion_number();
                            String cache_code = self.getCoders_code();
                            self.setCoders_code("Not Yet Started");
                            self.setQuestion_number(question_number);
                            c.updateCache_codersrash(unique_code + coder, self);
                            d.set_sqlstatement("Insert into CODERRASH.\"CODE_BASE\" (\"UNIQUE_EVENT_ID\",\"PARTICIPANT\",\"CODE\",\"Q_ID\",\"QUESTION_NUMBER\") values (?, ?, ?, ?, ?)");
                            Map<Integer, Object> param = new HashMap<>();
                            param.put(1, unique_code);
                            param.put(2, coder);
                            param.put(3, "Started, but not submitted");
                            param.put(4, q_id);
                            param.put(5, question_number);
                            d.set_sqlparameters(param);
                            int return_rs = d.process_update_Query();
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
                            out.print(output);
                        } else {
                            out.print("No Questions");
                        }
                    } else {
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
                        out.print("questions_3");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(coderrashquestionHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    Logger.getLogger(coderrashquestionHandler.class.getName()).log(Level.SEVERE, null, e);
                } finally {
                    d.invalidate();
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
