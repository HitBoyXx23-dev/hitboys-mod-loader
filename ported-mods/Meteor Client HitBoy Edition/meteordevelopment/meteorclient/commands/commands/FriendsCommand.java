/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.arguments.FriendArgumentType;
/*    */ import meteordevelopment.meteorclient.commands.arguments.PlayerListEntryArgumentType;
/*    */ import meteordevelopment.meteorclient.systems.friends.Friend;
/*    */ import meteordevelopment.meteorclient.systems.friends.Friends;
/*    */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*    */ import net.minecraft.class_124;
/*    */ import net.minecraft.class_2172;
/*    */ 
/*    */ public class FriendsCommand
/*    */   extends Command
/*    */ {
/*    */   public FriendsCommand() {
/* 21 */     super("friends", "Manages friends.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 26 */     builder.then(literal("add")
/* 27 */         .then(argument("player", (ArgumentType)PlayerListEntryArgumentType.create())
/* 28 */           .executes(context -> {
/*    */               GameProfile profile = PlayerListEntryArgumentType.get(context).method_2966();
/*    */               
/*    */               Friend friend = new Friend(profile.name(), profile.id());
/*    */               
/*    */               if (Friends.get().add(friend)) {
/*    */                 ChatUtils.sendMsg(friend.hashCode(), class_124.field_1080, "Added (highlight)%s (default)to friends.".formatted(new Object[] { friend.getName() }, ), new Object[0]);
/*    */               } else {
/*    */                 error("Already friends with that player.", new Object[0]);
/*    */               } 
/*    */               
/*    */               return 1;
/*    */             })));
/*    */     
/* 42 */     builder.then(literal("remove")
/* 43 */         .then(argument("friend", (ArgumentType)FriendArgumentType.create())
/* 44 */           .executes(context -> {
/*    */               Friend friend = FriendArgumentType.get(context);
/*    */               
/*    */               if (friend == null) {
/*    */                 error("Not friends with that player.", new Object[0]);
/*    */                 
/*    */                 return 1;
/*    */               } 
/*    */               
/*    */               if (Friends.get().remove(friend)) {
/*    */                 ChatUtils.sendMsg(friend.hashCode(), class_124.field_1080, "Removed (highlight)%s (default)from friends.".formatted(new Object[] { friend.getName() }, ), new Object[0]);
/*    */               } else {
/*    */                 error("Failed to remove that friend.", new Object[0]);
/*    */               } 
/*    */               
/*    */               return 1;
/*    */             })));
/* 61 */     builder.then(literal("list").executes(context -> {
/*    */             info("--- Friends ((highlight)%s(default)) ---", new Object[] { Integer.valueOf(Friends.get().count()) });
/*    */             Friends.get().forEach(());
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\FriendsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */