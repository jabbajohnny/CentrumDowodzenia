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
                if(message.length != 2 && message[0].equals(Bot.prefix + "complete")){
                    event.getChannel().sendMessage(Embeds.wrongFormat().build()).queue();
                }else{
                    Task task = Tasks.taskContents.get(Long.parseLong(message[1]));

                    if(!task.isComplete) {

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle(task.title);
                        builder.setAuthor(task.author);
                        builder.setDescription(task.description);
                        builder.setFooter("Zadanie wykonane przez " + event.getMessage().getAuthor().getName());
                        builder.setColor(Color.green);


                        List<Message.Attachment> attachments = null;
                        try {

                            attachments = event.getMessage().getAttachments();

                            Tasks.fileCount++;

                            CompletableFuture<File> future = attachments.get(0).downloadToFile(Tasks.fileCount + attachments.get(0).getFileName());
                            future.exceptionally(error -> { // handle possible errors
                                error.printStackTrace();
                                return null;
                            });

                        }catch (Exception e){
                            event.getChannel().sendMessage(Embeds.noAttachmentError().build()).queue();
                        }

                        File file = new File(Tasks.fileCount + attachments.get(0).getFileName());


                        channel.editMessageById(Tasks.tasks.get(Long.parseLong(message[1])), builder.build()).addFile(file).queue(message1 -> message1.removeReaction("✅"));

                        task.isComplete = true;

                        Tasks.taskContents.put(Long.parseLong(message[1]), task);

                        Level.levelXP += 20;

                        try {
                            Tasks.saveList();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        event.getChannel().sendMessage(Embeds.completedInfo().build()).queue();
                    }
                    break;
                }


        }
    }

}
