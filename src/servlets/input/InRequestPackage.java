package servlets.input;

import java.util.Map;
/**
 * @version 0.2.0
 * @author acidmors
 */
public class InRequestPackage {

    public String type;

    public Map<String, String> string_params;
    public Map<String, Integer> integer_params;
    public Map<String, Double> double_params;
    public Map<String, Long> long_params;

    public InRequestPackage(){

    }

}
