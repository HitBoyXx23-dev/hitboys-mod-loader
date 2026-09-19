/*     */ package meteordevelopment.meteorclient.commands;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*     */ import net.minecraft.class_2170;
/*     */ import net.minecraft.class_2172;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_7157;
/*     */ import net.minecraft.class_7887;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Command
/*     */ {
/*  26 */   protected static class_7157 REGISTRY_ACCESS = class_2170.method_46732(class_7887.method_46817());
/*     */   protected static final int SINGLE_SUCCESS = 1;
/*  28 */   protected static final class_310 mc = MeteorClient.mc;
/*     */   
/*     */   private final String name;
/*     */   private final String title;
/*     */   private final String description;
/*     */   private final List<String> aliases;
/*     */   
/*     */   public Command(String name, String description, String... aliases) {
/*  36 */     this.name = name;
/*  37 */     this.title = Utils.nameToTitle(name);
/*  38 */     this.description = description;
/*  39 */     this.aliases = List.of(aliases);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static <T> RequiredArgumentBuilder<class_2172, T> argument(String name, ArgumentType<T> type) {
/*  44 */     return RequiredArgumentBuilder.argument(name, type);
/*     */   }
/*     */   
/*     */   protected static LiteralArgumentBuilder<class_2172> literal(String name) {
/*  48 */     return LiteralArgumentBuilder.literal(name);
/*     */   }
/*     */   
/*     */   public final void registerTo(CommandDispatcher<class_2172> dispatcher) {
/*  52 */     register(dispatcher, this.name);
/*  53 */     for (String alias : this.aliases) register(dispatcher, alias); 
/*     */   }
/*     */   
/*     */   public void register(CommandDispatcher<class_2172> dispatcher, String name) {
/*  57 */     LiteralArgumentBuilder<class_2172> builder = LiteralArgumentBuilder.literal(name);
/*  58 */     build(builder);
/*  59 */     dispatcher.register(builder);
/*     */   }
/*     */   
/*     */   public abstract void build(LiteralArgumentBuilder<class_2172> paramLiteralArgumentBuilder);
/*     */   
/*     */   public String getName() {
/*  65 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  69 */     return this.description;
/*     */   }
/*     */   
/*     */   public List<String> getAliases() {
/*  73 */     return this.aliases;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  77 */     return (String)(Config.get()).prefix.get() + (String)(Config.get()).prefix.get();
/*     */   }
/*     */   
/*     */   public String toString(String... args) {
/*  81 */     StringBuilder base = new StringBuilder(toString());
/*  82 */     for (String arg : args) base.append(' ').append(arg); 
/*  83 */     return base.toString();
/*     */   }
/*     */   
/*     */   public void info(class_2561 message) {
/*  87 */     ChatUtils.forceNextPrefixClass(getClass());
/*  88 */     ChatUtils.sendMsg(this.title, message);
/*     */   }
/*     */   
/*     */   public void info(String message, Object... args) {
/*  92 */     ChatUtils.forceNextPrefixClass(getClass());
/*  93 */     ChatUtils.infoPrefix(this.title, message, args);
/*     */   }
/*     */   
/*     */   public void warning(String message, Object... args) {
/*  97 */     ChatUtils.forceNextPrefixClass(getClass());
/*  98 */     ChatUtils.warningPrefix(this.title, message, args);
/*     */   }
/*     */   
/*     */   public void error(String message, Object... args) {
/* 102 */     ChatUtils.forceNextPrefixClass(getClass());
/* 103 */     ChatUtils.errorPrefix(this.title, message, args);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\Command.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */