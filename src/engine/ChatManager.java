package engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static engine.Prop.*;

public class ChatManager {

    private static ChatManager instance;
    public static ChatManager getInstance(){
        if(instance==null) instance = new ChatManager();
        return instance;
    }

    private List<String> messages;
    public void add_message(String address, String message){

        if(messages==null) messages = new ArrayList<>();

        String out = address + " --> "+message+" <-- "+new Date().getTime();
        messages.add(0, out);

        last_update = new Date().getTime();

        if (messages.size()> CHAT_MAX_MESSAGES){
            List<String> temp = new ArrayList<>();

            int i = 0;
            for (String s : messages){
                temp.add(s);
                i++;
                if(i>CHAT_LAST_AMOUNT) break;
            }
            messages = temp;
        }
    }
    public List<String> get_last(){
        if(messages==null) messages = new ArrayList<>();

        List<String> out = new ArrayList<>();
        int i = 0;

        for (String s: messages) {
            out.add(s);
            i++;
            if(i>CHAT_LAST_AMOUNT) break;
        }
        return out;
    }

    private long last_update;


    private ChatManager(){
        messages = new ArrayList<>();
    }

    public long getLast_update() {
        return last_update;
    }
}
