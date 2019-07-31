package royal.coders;

import java.io.IOException;
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
import royal.database.databaseconn;

@WebServlet(name = "rashers", urlPatterns = {"/rashers/*"})
public class competerashers extends HttpServlet {

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
            databaseconn d = new databaseconn();
            d.set_sqlstatement("SELECT COUNT(1) as count FROM CODERRASH.\"EVENT_HOST\" where \"UNIQUE_EVENT_ID\" = ?");
            Map<Integer, Object> param = new HashMap<>();
            param.put(1, compete_path);
            d.set_sqlparameters(param);
            d.process_select_Query();
            String output = null;
            try {
                if (d.rs.next()) {
                    output = d.rs.getString("COUNT");
                }

                response.setContentType("text/html;charset=UTF-8");

                if (output != null && output.equals("1")) {
                    RequestDispatcher rd = request.getRequestDispatcher("/competitorslogin.jsp");
                    rd.include(request, response);
                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("/invalidurl.jsp?message=Invalid URL. Try- http://localhost:8080/coderrash/");
                    rd.forward(request, response);
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
