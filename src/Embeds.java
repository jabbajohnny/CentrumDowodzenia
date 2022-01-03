import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Embeds{

    public static EmbedBuilder toShortError(){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Błąd");
        builder.setAuthor("System");
        builder.setColor(Color.RED);
        builder.setDescription("Wprowadzone zadanie nie ma treści :( " + '\n' + "Poprawny format: !task *treść zadania*");
        return builder;
    }

    public static EmbedBuilder taskMessage(String[] taskContent, GuildMessageReceivedEvent event) throws Exception {
        Tasks.taskCount++;
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Zadanie #" + Tasks.taskCount);
        builder.setAuthor("Dodane przez: " + event.getAuthor().getName());
        builder.setColor(Color.CYAN);
        builder.setFooter("Biorę się za to! ✅ " );

        StringBuilder content = new StringBuilder();

        for(int a = 1; a < taskContent.length; a++){
            content.append(taskContent[a] + " ");
        }

        content.deleteCharAt(content.length() - 1);


        builder.setDescription(content.toString());

        Tasks.taskContents.put(Tasks.taskCount, new Task("Zadanie #" + Tasks.taskCount, "Dodane przez: " + event.getAuthor().getName(), content.toString(), false));

        Files.createDirectory(Paths.get("D:\\IDEAPROJECTS\\kolkofizyczne\\src\\SpaceshipTasks\\task" +Tasks.taskCount));

        File info = new File("D:\\IDEAPROJECTS\\kolkofizyczne\\src\\SpaceshipTasks\\task" + Tasks.taskCount + "\\task" + Tasks.taskCount + ".txt");

        if(!info.exists()) info.createNewFile();

        FileWriter writer = new FileWriter(info);
        writer.write(builder.toString());
        writer.close();

        return builder;
    }

    public static EmbedBuilder wrongFormat(){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Błąd");
        builder.setAuthor("System");
        builder.setColor(Color.RED);
        builder.setDescription("Wprowadzona komenda jest nieprawidłowa :( " + '\n' + "Poprawny format: !complete *numer zadania*" + '\n' );
        return builder;
    }

    public static EmbedBuilder completedInfo(){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Nie możesz wykonać zadania 2 raz :)");
        builder.setAuthor("System");
        builder.setColor(Color.GREEN);
        builder.setDescription("To zadanie zostało już przesłane." + '\n' + "Spróbuj z innym zadaniem.");

        return builder;
    }

    public static EmbedBuilder noLinkError(){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Nie dołączyłeś linku do githuba w twojej wiadomości");
        builder.setAuthor("System");
        builder.setColor(Color.RED);
        builder.setDescription("Spróbuj ponownie z dołączonym :)");
        return builder;
    }

    public static EmbedBuilder taskChannelMessageInit(){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Tasks");
        builder.setAuthor("System");
        builder.setColor(new Color(51, 85, 255));
        builder.setImage("https://images.pexels.com/photos/2004161/pexels-photo-2004161.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        builder.setDescription("Na tym kanale znajdziesz wszystkie regularne zadania. \n" +
                "\n" +
                ":rocket:  **Dodawanie zadania:** !task *treść zadania*\n" +
                "\n" +
                ":white_check_mark:  **Oznaczenia zadania jako ukończone:** !complete *numer zadania*\n" +
                "\n" +
                ":black_cat:  **Nasze zadania na GitHubie:** https://github.com/jabbajohnny/kolkofizyczne");
        return builder;
    }
}
