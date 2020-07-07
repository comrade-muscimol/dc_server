package servlets.input;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import engine.LogManager;
import org.hibernate.SessionFactory;
import tasks.*;
import tasks.input.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "In")
public class In extends HttpServlet {

    private SessionFactory sessionFactory;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String encrypted_string;
        String decoded_string;
        InRequestPackage input_request;

        String address;
        String secret;

        try {
             encrypted_string = request.getParameter("encrypted_string");
             decoded_string = decode(encrypted_string);

             input_request = in_pack_from_json(decoded_string);

             address = input_request.string_params.get("address");
             secret = input_request.string_params.get("secret");

//            sessionFactory = new Configuration().configure().buildSessionFactory();
//            Session session = this.sessionFactory.openSession();
//            Transaction transaction = null;
//
//            transaction = session.beginTransaction();
//            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);
//
//            if(!account.getSecret().equals(secret)){
//                out.print("false");
//                out.close();
//                return;
//            }

            switch (input_request.type){

                case "manual":
                    TaskManager.getInstance().add_to_queue(new ManualTask(
                            address,
                            input_request.integer_params.get("record")
                    ));
                    break;

                case "auto":
                    TaskManager.getInstance().add_to_queue(new AutoTask(
                           address,
                            input_request.integer_params.get("matches")
                    ));
                    break;

                case "buy1dd":
                case "buy10dd":
                case "buy100dd":
                case "sell1dd":
                case "sell10dd":
                case "sell100dd":
                    TaskManager.getInstance().add_to_queue(new BankSellTask(
                            input_request.type,
                            address
                    ));
                    break;

                case "ad":
                    TaskManager.getInstance().add_to_queue(new AdTask(
                            address
                    ));
                    break;

                case "small_dc":
                case "middle_dc":
                case "big_dc":
                case "small_dd":
                case "middle_dd":
                case "big_dd":
                    TaskManager.getInstance().add_to_queue(new CaseTask(
                            address,
                            input_request.type
                    ));
                    break;

                case "ap1":
                case "ap10":
                case "ap50":
                case "ap100":
                    TaskManager.getInstance().add_to_queue(
                            new ApExchangeTask(
                                    address,
                                    input_request.type
                            ));
                    break;

                case "code":
                    TaskManager.getInstance().add_to_queue(
                            new CodeTask(
                                    address,
                                    input_request.string_params.get("code")
                            ));
                    break;

                case "hipe_90":

                    TaskManager.getInstance().add_to_queue(new HipeTask(
                            HipeTask.Offer.HIPE_90,
                            address,
                            input_request.double_params.get("dc")
                    ));
                    break;

                case "hipe_80":
                    TaskManager.getInstance().add_to_queue(new HipeTask(
                            HipeTask.Offer.HIPE_80,
                            address,
                           input_request.double_params.get("dc")
                    ));
                    break;

                case "hipe_70":
                    TaskManager.getInstance().add_to_queue(new HipeTask(
                            HipeTask.Offer.HIPE_70,
                            address,
                            input_request.double_params.get("dc")
                    ));
                    break;

                case "hipe_60":
                    TaskManager.getInstance().add_to_queue(new HipeTask(
                            HipeTask.Offer.HIPE_60,
                            address,
                           input_request.double_params.get("dc")
                    ));
                    break;

                case "hipe_50":
                    TaskManager.getInstance().add_to_queue(new HipeTask(
                            HipeTask.Offer.HIPE_50,
                            address,
                           input_request.double_params.get("dc")
                    ));
                    break;

                case "hipe_40":
                    TaskManager.getInstance().add_to_queue(new HipeTask(
                            HipeTask.Offer.HIPE_40,
                            address,
                           input_request.double_params.get("dc")
                    ));
                    break;

                case "hipe_30":
                    TaskManager.getInstance().add_to_queue(new HipeTask(
                            HipeTask.Offer.HIPE_30,
                            address,
                            input_request.double_params.get("dc")
                    ));
                    break;

                case "world_chat_message":
                    TaskManager.getInstance().add_to_queue(
                            new ChatTask(
                                    address,
                                    input_request.string_params.get("message")
                            ));
                    break;

                case "hack":
                    TaskManager.getInstance().add_to_queue(
                            new HackTask(
                                    address,
                                    input_request.string_params.get("address_to")
                            )
                    );
                    break;

                case "hack_upgrade_max":
                case "hack_upgrade_min":
                case "hack_upgrade_bounty":
                    TaskManager.getInstance().add_to_queue(new HackUpgradeTask(
                            address,
                            input_request.type
                            )
                    );
                    break;

                case "kill":
                    TaskManager.getInstance().add_to_queue(new KillTask(
                            address,
                            input_request.string_params.get("address_to")
                    ));
                    break;

                case "kill_upgrade_max":
                case "kill_upgrade_min":
                    TaskManager.getInstance().add_to_queue(new KillUpgradeTask(
                            address,
                            input_request.type
                            )
                    );
                    break;

                    case "buy_lottery_ticket":
                        TaskManager.getInstance().add_to_queue(
                                new LotteryTask(
                                        address
                                ));
                    break;

                case "online":
                    TaskManager.getInstance().add_to_queue(
                            new OnlineTask(
                                    address                            ));
                    break;

                case "online_upgrade":
                    TaskManager.getInstance().add_to_queue(
                            new OnlineUpgradeTask(
                                    address                            ));
                    break;

                case "mail_message":
                    TaskManager.getInstance().add_to_queue( new MailTask(
                            address,
                            input_request.string_params.get("address_to"),
                            input_request.string_params.get("topic"),
                            input_request.string_params.get("message")
                    ));
                    break;
                case "transaction":
                    TaskManager.getInstance().add_to_queue(new TransactionTask(
                            address,
                            input_request.string_params.get("address_to"),
                            input_request.double_params.get("dc"),
                            input_request.integer_params.get("dd")
                    ));
                    break;

                default:
                    out.print("false");
                    out.close();
                    LogManager.getInstance().log_add_message(LogManager.Type.IN_SERVLET, request.getRemoteAddr()+" --- "+address+" --- "+input_request.type, LogManager.Total.DEFAULT);
                    return;
            }

            out.print("true");
            //session.close();
            out.close();

            LogManager.getInstance().log_add_message(LogManager.Type.IN_SERVLET, request.getRemoteAddr()+" --- "+address+" --- "+input_request.type, LogManager.Total.SUCCESS);
        }catch (Exception e){

            out.println("false");
            out.close();
            LogManager.getInstance().log_add_message(LogManager.Type.IN_SERVLET, request.getRemoteAddr(), LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, request.getRemoteAddr()+e.getMessage(), LogManager.Total.STACK_TRACE);
        }
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

    public static String encode(String address) {
        return address;
    }
    public static String decode(String secret) {
        return secret;
    }

    public static InRequestPackage in_pack_from_json(String json){
        try {
            Gson gson = new Gson();
            InRequestPackage input = gson.fromJson(json, InRequestPackage.class);
            return input;
        } catch (Exception e) {

            System.out.println(json);
            return null;
        }
    }
}
