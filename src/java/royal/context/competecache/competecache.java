package royal.context.competecache;

import java.util.HashMap;
import java.util.Map;
import royal.context.coderscode.competecoderscode;

public class competecache {
    private static Map<String,competecoderscode> cache_codersrash = new HashMap();

    public static Map<String, competecoderscode> getCache_codersrash() {
        return cache_codersrash;
    }
    
    public static competecoderscode getCache_codersrash_compete_codercode(String compete_context) {
        if(cache_codersrash.containsKey(compete_context))
            return cache_codersrash.get(compete_context);
        return null;
    }

    public static void setCache_codersrash(Map<String, competecoderscode> cache_codersrash) {
        competecache.cache_codersrash = cache_codersrash;
    }
    
    public static void updateCache_codersrash(String compete_context, competecoderscode c) {
        cache_codersrash.put(compete_context,c);
    }
    
    public static void insert_Cache_codersrash(String compete_context, competecoderscode coder) {
        if(!cache_codersrash.containsKey(compete_context))
            cache_codersrash.put(compete_context, coder);
    }
    
    public static void destroy_Cache_codersrash_coders(String compete_context) {
        if(cache_codersrash.containsKey(compete_context))
            cache_codersrash.remove(compete_context);
    }
}
