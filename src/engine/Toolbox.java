package engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entities.AccountsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Toolbox {

    private static SessionFactory sessionFactory;

    private static Random random = new Random();
    public static String generateRandomAddress(int address_length){
        String out = "";
        while(true){
            for (int i = 0; i < address_length; i++) {
                out += SymbolManager.Symbols.values()[random.nextInt(SymbolManager.Symbols.values().length)].getCharacter();
            }
            try{
                sessionFactory = new Configuration().configure().buildSessionFactory();
                Session session = Toolbox.sessionFactory.openSession();
                Transaction transaction = null;

                transaction = session.beginTransaction();
                AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, out);
                account.getAddress();

            }catch (Exception e){
                return out;
            }
        }
    }

    public static String encode(String address, String mainKey) {

        byte[] adr = address.getBytes();
        byte[] key = mainKey.getBytes();
        byte[] res = new byte[address.length()];
        for (int i = 0; i < adr.length; i++) {
            res[i] = (byte) (adr[i] ^ key[i % key.length]);
        }

        return res.toString();
    }
    public static String decode(String secret, String mainKey) {

        byte[] sec = secret.getBytes();
        byte[] res = new byte[sec.length];
        byte[] key = mainKey.getBytes();
        for (int i = 0; i < secret.toCharArray().length; i++) {
            res[i] = (byte) (sec[i] ^ key[i % key.length]);
        }
        return new String(res);
    }

    public static String list_to_json(List<String> list){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String JSON  = gson.toJson(list);
        return JSON;
    }
    public static List<String> json_to_list(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        List<String> yourClassList = new Gson().fromJson(json, listType);
        return  yourClassList;
    }

}
