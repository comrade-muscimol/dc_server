package servlets.refresh.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import engine.*;
import entities.AccountsEntity;

import org.hibernate.SessionFactory;
import entities.DBConnector;
import tasks.TaskManager;
import tasks.system.ClearHistoryTask;
import tasks.system.ClearMailTask;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "RefreshMain")
public class RefreshMain extends HttpServlet {
    private SessionFactory sessionFactory;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String encrypted_string;
        String decoded_string;
        MainRequestPackage input_request;

        String address;
        String secret;
        String type;

        try {
            encrypted_string = request.getParameter("encrypted_string");
            decoded_string = decode(encrypted_string);

            input_request = index_pack_from_json(decoded_string);

            address = input_request.address;
            secret = input_request.secret;
            type = input_request.type;

        MainResponsePackage output_response = new MainResponsePackage();

        AccountsEntity account = DBConnector.getInstance().getAccountEntity(address);

        output_response.string_params= new HashMap<>();
        output_response.integer_params= new HashMap<>();
        output_response.double_params= new HashMap<>();
        output_response.long_params= new HashMap<>();

        switch (input_request.type){
            case "account":
                output_response.type = type;
                output_response.universal_list = new ArrayList<>();

                output_response.long_params.put("account_last_update", account.getAccountLastUpdate());

                output_response.integer_params.put("dd",account.getDd());
                output_response.double_params.put("dc", account.getDc());
                output_response.integer_params.put("record", account.getRecord());

                output_response.integer_params.put("ads_points", account.getAp());
                output_response.integer_params.put("lottery_tickets", LotteryManager.getInstance().my_tickets(address));

                output_response.double_params.put("kill_minimum_percent", account.getKillMin());
                output_response.double_params.put("kill_maximum_percent", account.getKillMax());

                output_response.double_params.put("hack_minimum_percent",account.getHackMin());
                output_response.double_params.put("hack_maximum_percent", account.getHackMax());
                output_response.double_params.put("hack_bounty_percent", account.getHackBounty());

                output_response.integer_params.put("online_points", account.getOp());
                output_response.double_params.put("online_bounty", account.getOpBounty());
                break;

            case "bank":

                output_response.type = type;
                output_response.universal_list = new ArrayList<>();

                output_response.long_params.put("bank_last_update", BankManager.getInstance().getLast_update());

                output_response.double_params.put("bank_manual_tax", BankManager.getInstance().getManual_tax());
                output_response.double_params.put("bank_auto_tax", BankManager.getInstance().getAuto_tax());

                output_response.double_params.put("bank_dd_course_buy", BankManager.getInstance().getCurrent_buy_course());
                output_response.double_params.put("bank_dd_course_sell", BankManager.getInstance().getCurrent_sell_course());
                break;

            case "transaction":
                output_response.type = type;
                output_response.universal_list = TransactionManager.getInstance().get_last_100();

                output_response.long_params.put("transaction_last_update", TransactionManager.getInstance().getLast_update());
                output_response.double_params.put("transaction_tax_percent", TransactionManager.getInstance().getCurrent_tax());

                break;

            case "lottery":

                output_response.type = type;
                output_response.universal_list = new ArrayList<>();

                output_response.long_params.put("lottery_last_update", LotteryManager.getInstance().getLast_update());
                output_response.long_params.put("lottery_end", LotteryManager.getInstance().getEnd());
                output_response.double_params.put("lottery_dc", LotteryManager.getInstance().getDc());
                output_response.integer_params.put("lottery_circulation", LotteryManager.getInstance().getCirculation());
                output_response.double_params.put("lottery_ticket_coast", Prop.LOTTERY_TICKET_COAST);

                break;

            case "history":
                output_response.type = type;
                output_response.universal_list =(ArrayList<String>) Toolbox.json_to_list(account.getHistory());

                output_response.long_params.put("history_last_update", account.getHistoryLastUpdate());

                TaskManager.getInstance().add_to_queue(new ClearHistoryTask(address));

                break;

            case "mail":
                output_response.type = type;
                output_response.universal_list = Toolbox.json_to_list(account.getMail());

                output_response.long_params.put("mail_last_update", account.getMailLastUpdate());
                TaskManager.getInstance().add_to_queue(new ClearMailTask(address));

                break;

            case "chat":
                output_response.type = type;
                output_response.universal_list = ChatManager.getInstance().get_last();

                output_response.long_params.put("chat_last_update", ChatManager.getInstance().getLast_update());
                break;

            case "rating":
                output_response.type = type;
                output_response.universal_list = RatingManager.getInstance().getTop_100();

                output_response.long_params.put("rating_last_update", RatingManager.getInstance().getLast_updated());
                break;

            default:
                LogManager.getInstance().log_add_message(LogManager.Type.REFRESH_MAIN_SERVLET, request.getRemoteAddr()+" --- "+address+" --- "+secret+" --- "+input_request.type, LogManager.Total.DEFAULT);

                out.print("false");
                return;

        }

        out.print(encode(serial(output_response)));
        LogManager.getInstance().log_add_message(LogManager.Type.REFRESH_MAIN_SERVLET, request.getRemoteAddr()+" --- "+address+" --- "+secret+" --- "+input_request.type, LogManager.Total.SUCCESS);
        out.close();

        }catch (Exception e){

            LogManager.getInstance().log_add_message(LogManager.Type.REFRESH_MAIN_SERVLET, request.getRemoteAddr(), LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, request.getRemoteAddr()+e.getMessage(), LogManager.Total.STACK_TRACE);

            out.print("false");
            out.close();
            return;
        }

    }

    public static String encode(String address) {
        return address;
    }
    public static String decode(String secret) {
        return secret;
    }

    public static MainRequestPackage index_pack_from_json(String json){
        try {
            Gson gson = new Gson();
            MainRequestPackage input = gson.fromJson(json, MainRequestPackage.class);
            return input;
        } catch (Exception e) {

            System.out.println(json);
            return null;
        }
    }
    public static String serial(MainResponsePackage pack){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String JSON  = gson.toJson(pack);
        return JSON;
    }
}
