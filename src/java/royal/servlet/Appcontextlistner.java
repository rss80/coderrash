package royal.servlet;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import royal.context.coderscode.competecoderscode;
import royal.context.competecache.competecache;
import royal.database.databaseconn;
import royal.handlers.royalcoderslogout;

@WebListener
public class Appcontextlistner implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();
        competecache c = new competecache();
        ctx.setAttribute("context_cache_cr", c);

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new AutomateRefresh(), 0, 5, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdown();
    }

    public class AutomateRefresh implements Runnable {

        public void run() {
            competecache c = new competecache();
            Map<String, competecoderscode> cache_codersrash = c.getCache_codersrash();
            databaseconn d = new databaseconn();
            try {

                for (Entry<String, competecoderscode> entry : cache_codersrash.entrySet()) {
                    competecoderscode ccc = entry.getValue();

                    int cache_question = ccc.getQuestion_number();
                    String cache_code = ccc.getCoders_code();
                    String unique_compete_code = ccc.getUnique_compete_code();
                    String coder_name = ccc.getCoder_name();
                    long timestamp = ccc.getTimestamp();
                    if (cache_question != 0) {

                        d.set_sqlstatement("SELECT TOTAL_TIME FROM CODERRASH.\"EVENT_HOST\" where \"UNIQUE_EVENT_ID\" = ?");
                        Map<Integer, Object> param_1 = new HashMap<>();
                        param_1.put(1, unique_compete_code);
                        d.set_sqlparameters(param_1);
                        d.process_select_Query();
                        int TOTAL_TIME = 0;
                        if (d.rs.next()) {
                            TOTAL_TIME = Integer.parseInt(d.rs.getString("TOTAL_TIME"));
                        }
                        TOTAL_TIME = TOTAL_TIME + 5;
                        Timestamp current_timestamp = new Timestamp(System.currentTimeMillis());
                        long timestamp_current = current_timestamp.getTime();
                        if (timestamp_current >=  (timestamp + (TOTAL_TIME * 60 * 1000))) {
                            
                            d.set_sqlstatement("Update CODERRASH.\"CODE_BASE\" set \"CODE\" = ? where \"UNIQUE_EVENT_ID\" = ? and \"PARTICIPANT\" = ? and \"QUESTION_NUMBER\" = ?");
                            Map<Integer, Object> param1 = new HashMap<>();
                            param1.put(1, cache_code);
                            param1.put(2, unique_compete_code);
                            param1.put(3, coder_name);
                            param1.put(4, cache_question);
                            d.set_sqlparameters(param1);
                            int return_rs_update = d.process_update_Query();

                            c.destroy_Cache_codersrash_coders(unique_compete_code + coder_name);
                        }
                    }

                }
            } catch (Exception e) {
                Logger.getLogger(royalcoderslogout.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                d.invalidate();
            }

        }
    }
}
