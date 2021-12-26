import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Bot {
    static final String prefix = "!";
    public static JDA jda;
    public static void main(String[] args) throws LoginException, InterruptedException, IOException, ClassNotFoundException {

        JDABuilder builder = JDABuilder.createDefault(TOKEN.getToken());

        builder.addEventListeners(new Events());

        jda = builder.build();
        jda.awaitReady();


        try{
            Tasks.readList();
        }catch (Exception e){
            e.printStackTrace();
        }

        Thread thread = new Thread(new Level(jda));
        thread.start();

    }
}
