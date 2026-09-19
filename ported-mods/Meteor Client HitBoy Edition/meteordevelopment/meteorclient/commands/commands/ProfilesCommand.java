/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.arguments.ProfileArgumentType;
/*    */ import meteordevelopment.meteorclient.systems.profiles.Profile;
/*    */ import meteordevelopment.meteorclient.systems.profiles.Profiles;
/*    */ import net.minecraft.class_2172;
/*    */ 
/*    */ 
/*    */ public class ProfilesCommand
/*    */   extends Command
/*    */ {
/*    */   public ProfilesCommand() {
/* 18 */     super("profiles", "Loads and saves profiles.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 23 */     builder.then(literal("load").then(argument("profile", (ArgumentType)ProfileArgumentType.create()).executes(context -> {
/*    */               Profile profile = ProfileArgumentType.get(context);
/*    */               
/*    */               if (profile != null) {
/*    */                 profile.load();
/*    */                 
/*    */                 info("Loaded profile (highlight)%s(default).", new Object[] { profile.name.get() });
/*    */               } 
/*    */               
/*    */               return 1;
/*    */             })));
/* 34 */     builder.then(literal("save").then(argument("profile", (ArgumentType)ProfileArgumentType.create()).executes(context -> {
/*    */               Profile profile = ProfileArgumentType.get(context);
/*    */               
/*    */               if (profile != null) {
/*    */                 profile.save();
/*    */                 
/*    */                 info("Saved profile (highlight)%s(default).", new Object[] { profile.name.get() });
/*    */               } 
/*    */               
/*    */               return 1;
/*    */             })));
/* 45 */     builder.then(literal("delete").then(argument("profile", (ArgumentType)ProfileArgumentType.create()).executes(context -> {
/*    */               Profile profile = ProfileArgumentType.get(context);
/*    */               if (profile != null) {
/*    */                 Profiles.get().remove(profile);
/*    */                 info("Deleted profile (highlight)%s(default).", new Object[] { profile.name.get() });
/*    */               } 
/*    */               return 1;
/*    */             })));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\ProfilesCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */