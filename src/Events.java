import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class Events extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] message = event.getMessage().getContentRaw().split(" ");
        TextChannel channel = Bot.jda.getTextChannelById("923266842460581940");

        switch(message[0]){

            case Bot.prefix + "task":
                if(message.length < 2) {
                    event.getChannel().sendMessage(Embeds.toShortError().build()).queue();
                }else {
                    MessageEmbed embed;
                    try {
                        embed = Embeds.taskMessage(message, event).build();

                        channel.sendMessage(embed).queue(message1 -> {
                            message1.addReaction("✅").queue();
                            Tasks.tasks.put(Tasks.taskCount, message1.getIdLong());
                            try {
                                Tasks.saveList();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                }

            case Bot.prefix + "complete" :
                if(message.length != 3 && message[0].equals(Bot.prefix + "complete")){
                    event.getChannel().sendMessage(Embeds.wrongFormat().build()).queue();
                }else{
                    Task task = Tasks.taskContents.get(Long.parseLong(message[1]));

                    if(!task.isComplete) {

                        if(!message[2].contains("github.com")){
                            event.getChannel().sendMessage(Embeds.noLinkError().build()).queue();
                        }else {
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle(task.title);
                            builder.setAuthor(task.author);
                            builder.setDescription(task.description + '\n' + '\n' + "**Github:** " + message[2]);
                            builder.setFooter("Zadanie wykonane przez " + event.getMessage().getAuthor().getName());
                            builder.setColor(Color.green);

                            channel.editMessageById(Tasks.tasks.get(Long.parseLong(message[1])), builder.build()).queue();

                            task.isComplete = true;

                            Tasks.taskContents.put(Long.parseLong(message[1]), task);

                            Level.levelXP += 20;

                            try {
                                Tasks.saveList();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        event.getChannel().sendMessage(Embeds.completedInfo().build()).queue();
                    }
                    break;
                }


        }
    }

}
