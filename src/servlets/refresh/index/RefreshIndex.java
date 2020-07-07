package servlets.refresh.index;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.AccountsEntity;
import engine.LogManager;
import engine.BankManager;
import engine.ChatManager;
import engine.LotteryManager;
import engine.RatingManager;
import engine.TransactionManager;
import entities.DBConnector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RefreshIndex")
public class RefreshIndex extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String encrypted_string;
        String decoded_string;
        IndexRequestPackage input_request;

        String address;
        String secret;

        try {

            encrypted_string = request.getParameter("encrypted_string");
            decoded_string = decode(encrypted_string);

            input_request = index_pack_from_json(decoded_string);

            address =  input_request.address;
            secret = input_request.secret;

        }catch (Exception e){
            out.print("false");
            out.close();
            return;
        }
        IndexResponsePackage output_response = new IndexResponsePackage();

        try{
            AccountsEntity account = DBConnector.getInstance().getAccountEntity(address);

            if(!account.getSecret().equals(secret)){
                out.print("false");
                out.close();
                return;
            }else{
                output_response.valid_address = true;
                output_response.valid_secret = true;
            }

                    output_response.symbol = BankManager.getInstance().getActualSymbol();

                    if(input_request.account_last_update!=account.getAccountLastUpdate()){
                        output_response.account_outdated = true;
                    }else{
                        output_response.account_outdated = false;
                    }

                    if(input_request.bank_last_update!= BankManager.getInstance().getLast_update()){
                        output_response.bank_outdated = true;
                    }else{
                        output_response.bank_outdated = false;
                    }
                    if(input_request.transaction_last_update!= TransactionManager.getInstance().getLast_update()){
                        output_response.transaction_outdated = true;
                    }else{
                        output_response.transaction_outdated = false;
                    }
                    if(input_request.lottery_last_update!=  LotteryManager.getInstance().getLast_update()){
                        output_response.lottery_outdated = true;
                    }else{
                        output_response.lottery_outdated = false;
                    }


                    if(input_request.history_last_update!=  account.getHistoryLastUpdate()){
                        output_response.history_outdated = true;
                    }else{
                        output_response.history_outdated = false;
                    }
                    if(input_request.mail_last_update!=  account.getMailLastUpdate()){
                        output_response.mail_outdated = true;
                    }else{
                        output_response.mail_outdated = false;
                    }
                    if(input_request.chat_last_update!= ChatManager.getInstance().getLast_update()){
                        output_response.chat_outdated = true;
                    }else{
                        output_response.chat_outdated = false;
                    }
                    if(input_request.rating_last_update!= RatingManager.getInstance().getLast_updated()){
                        output_response.rating_outdated = true;
                    }else{
                        output_response.rating_outdated = false;
                    }

                    out.print(encode(serial(output_response)));
                    out.close();
                    //LogManager.getInstance().log_add_message(LogManager.Type.REFRESH_INDEX_SERVLET, request.getRemoteAddr(), LogManager.Total.SUCCESS);
        }catch (Exception e){

            out.print("false");
            out.close();
            LogManager.getInstance().log_add_message(LogManager.Type.REFRESH_INDEX_SERVLET, request.getRemoteAddr(), LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, request.getRemoteAddr()+e.getMessage(), LogManager.Total.STACK_TRACE);
        }
    }
    public static String encode(String address) {
        return address;
    }
    public static String decode(String secret) {
        return secret;
    }

    public static IndexRequestPackage index_pack_from_json(String json){
        try {
            Gson gson = new Gson();
            IndexRequestPackage input = gson.fromJson(json, IndexRequestPackage.class);
            return input;
        } catch (Exception e) {

            System.out.println(json);
            return null;
        }
    }
    public static String serial(IndexResponsePackage pack){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String JSON  = gson.toJson(pack);
        return JSON;
    }

}
