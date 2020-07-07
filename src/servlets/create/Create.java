package servlets.create;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import engine.Prop;
import engine.Toolbox;
import engine.LogManager;
import tasks.input.CreateTask;
import tasks.TaskManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet(name = "Create")
public class Create extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String encrypted_string;
        String decoded_string;
        CreateRequestPackage input_request;

        String address;
        String secret;

        try {

            encrypted_string = request.getParameter("encrypted_string");
            decoded_string = decode(encrypted_string);

            input_request = create_pack_from_json(decoded_string);

            address = Toolbox.generateRandomAddress(10);
            secret = Toolbox.encode(address, Prop.SECRET_ENCODE_KEY+new Date().getTime());


            TaskManager.getInstance().add_to_queue(new CreateTask(
                    address,
                    secret,
                    input_request.id
            ));


            CreateResponsePackage createResponsePackage = new CreateResponsePackage();
            createResponsePackage.address = address;
            createResponsePackage.secret = secret;

            out.print(encode(serial(createResponsePackage)));
            out.close();

            LogManager.getInstance().log_add_message(LogManager.Type.CREATE_SERVLET, request.getRemoteAddr()+" --- "+address+" --- "+secret, LogManager.Total.SUCCESS);
        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.CREATE_SERVLET, request.getRemoteAddr(), LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            out.print("false");
            out.close();

        }
    }

    public static String encode(String address) {
        return address;
    }
    public static String decode(String secret) {
        return secret;
    }

    public static CreateRequestPackage create_pack_from_json(String json){
        try {
            Gson gson = new Gson();
            CreateRequestPackage input = gson.fromJson(json, CreateRequestPackage.class);
            return input;
        } catch (Exception e) {

            return null;
        }
    }
    public static String serial(CreateResponsePackage pack){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String JSON  = gson.toJson(pack);
        return JSON;
    }
}
