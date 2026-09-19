/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.StringArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.time.Instant;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.mixin.ClientPlayNetworkHandlerAccessor;
/*    */ import meteordevelopment.meteorclient.utils.misc.MeteorStarscript;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2596;
/*    */ import net.minecraft.class_2797;
/*    */ import net.minecraft.class_3515;
/*    */ import net.minecraft.class_634;
/*    */ import net.minecraft.class_7469;
/*    */ import net.minecraft.class_7608;
/*    */ import net.minecraft.class_7637;
/*    */ import org.meteordev.starscript.Script;
/*    */ 
/*    */ public class SayCommand
/*    */   extends Command
/*    */ {
/*    */   public SayCommand() {
/* 26 */     super("say", "Sends messages in chat.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 31 */     builder.then(argument("message", (ArgumentType)StringArgumentType.greedyString()).executes(context -> {
/*    */             String msg = (String)context.getArgument("message", String.class);
/*    */             Script script = MeteorStarscript.compile(msg);
/*    */             if (script != null) {
/*    */               String message = MeteorStarscript.run(script);
/*    */               if (message != null) {
/*    */                 Instant instant = Instant.now();
/*    */                 long l = class_3515.class_7426.method_43531();
/*    */                 class_634 handler = mc.method_1562();
/*    */                 class_7637.class_7816 lastSeenMessages = ((ClientPlayNetworkHandlerAccessor)handler).meteor$getLastSeenMessagesCollector().method_46266();
/*    */                 class_7469 messageSignatureData = ((ClientPlayNetworkHandlerAccessor)handler).meteor$getMessagePacker().pack(new class_7608(message, instant, l, lastSeenMessages.comp_1073()));
/*    */                 handler.method_52787((class_2596)new class_2797(message, instant, l, messageSignatureData, lastSeenMessages.comp_1074()));
/*    */               } 
/*    */             } 
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\SayCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */