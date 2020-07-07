package servlets.refresh.main;

import java.util.List;
import java.util.Map;
/**
 * @version 0.2.0
 * @author acidmors
 */
public class MainResponsePackage {
    public  boolean valid_address;
    public  boolean valid_secret;

    public  String type;

    public Map<String, String> string_params;
    public Map<String, Integer> integer_params;
    public Map<String, Double> double_params;
    public Map<String, Long> long_params;

    public  List<String> universal_list;

    public MainResponsePackage(){

    }
}
