/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package royal.database;

import java.sql.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class databaseconn {

    private Connection conn;
    private PreparedStatement sta_prep;
    private Map<Integer, Object> _parameters;
    private String _sql_statement;
    private String driver = "org.apache.derby.jdbc.ClientDriver";
    private String user = "coderrash";
    private String password = "coderrash";
    private String connection_URL = "jdbc:derby://localhost:1527/coderrash";
    public ResultSet rs;

    public databaseconn() {
        try {
            Class.forName(this.driver);
            conn = DriverManager.getConnection(this.connection_URL, this.user, this.password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(databaseconn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getSql_statement() {
        return _sql_statement;
    }

    public void set_sqlstatement(String statement) {
        this._sql_statement = statement;
    }

    public void set_sqlparameters(Map<Integer, Object> param) {
        this._parameters = param;
    }

    public void invalidate() {
        try {
            if (this.conn != null) {
                this.conn.close();
            }
            if (this.sta_prep != null) {
                this.sta_prep.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(databaseconn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void process_select_Query() {
        try {

            this.sta_prep = this.conn.prepareStatement(this._sql_statement);

            if (this._parameters != null) {
                for (Integer key : this._parameters.keySet()) {
                    this.sta_prep.setObject(key, this._parameters.get(key));
                }
            }
            this.rs = null;
            this.rs = this.sta_prep.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(databaseconn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int process_update_Query() {
        try {

            this.sta_prep = this.conn.prepareStatement(this._sql_statement);
            if (this._parameters != null) {
                for (Integer key : this._parameters.keySet()) {
                    this.sta_prep.setObject(key, this._parameters.get(key));
                }
            }
            this.rs = null;
            int rs = this.sta_prep.executeUpdate();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(databaseconn.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
}
