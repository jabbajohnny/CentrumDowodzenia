import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

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

        return builder;
    }

    public static EmbedBuilder wrongFormat(){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Błąd");
        builder.setAuthor("System");
        builder.setColor(Color.RED);
        builder.setDescription("Wprowadzona komenda jest nieprawidłowa :( " + '\n' + "Poprawny format: !complete *numer zadania*" + '\n' + "**Pamiętaj, żeby do wiadomości dołączyć plik z rozwiązaniem**");
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

    public static EmbedBuilder noAttachmentError(){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Nie dołączyłeś pliku do rozwiązania");
        builder.setAuthor("System");
        builder.setColor(Color.RED);
        builder.setDescription("Spróbuj ponownie z dołączonym plikiem z kodem :)");
        return builder;
    }
}
