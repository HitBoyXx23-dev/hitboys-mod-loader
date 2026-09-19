/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.StringArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.arguments.BlockPosArgumentType;
/*    */ import meteordevelopment.meteorclient.commands.arguments.WaypointArgumentType;
/*    */ import meteordevelopment.meteorclient.systems.waypoints.Waypoint;
/*    */ import meteordevelopment.meteorclient.systems.waypoints.Waypoints;
/*    */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*    */ import net.minecraft.class_124;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2338;
/*    */ 
/*    */ 
/*    */ public class WaypointCommand
/*    */   extends Command
/*    */ {
/*    */   public WaypointCommand() {
/* 23 */     super("waypoint", "Manages waypoints.", new String[] { "wp" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 28 */     builder.then(literal("list").executes(context -> {
/*    */             if (Waypoints.get().isEmpty()) {
/*    */               error("No created waypoints.", new Object[0]);
/*    */             } else {
/*    */               info(String.valueOf(class_124.field_1068) + "Created Waypoints:", new Object[0]);
/*    */               for (Waypoint waypoint : Waypoints.get()) {
/*    */                 info("Name: (highlight)'%s'(default), Dimension: (highlight)%s(default), Pos: (highlight)%s(default)", new Object[] { waypoint.name.get(), waypoint.dimension.get(), waypointPos(waypoint) });
/*    */               } 
/*    */             } 
/*    */             return 1;
/*    */           }));
/* 39 */     builder.then(literal("get").then(argument("waypoint", (ArgumentType)WaypointArgumentType.create()).executes(context -> {
/*    */               Waypoint waypoint = WaypointArgumentType.get(context);
/*    */               
/*    */               info("Name: " + String.valueOf(class_124.field_1068) + (String)waypoint.name.get(), new Object[0]);
/*    */               info("Actual Dimension: " + String.valueOf(class_124.field_1068) + String.valueOf(waypoint.dimension.get()), new Object[0]);
/*    */               info("Position: " + String.valueOf(class_124.field_1068) + waypointFullPos(waypoint), new Object[0]);
/*    */               info("Visible: " + (((Boolean)waypoint.visible.get()).booleanValue() ? (String.valueOf(class_124.field_1060) + "True") : (String.valueOf(class_124.field_1061) + "False")), new Object[0]);
/*    */               return 1;
/*    */             })));
/* 48 */     builder.then(((LiteralArgumentBuilder)literal("add")
/* 49 */         .then(argument("pos", (ArgumentType)BlockPosArgumentType.blockPos())
/* 50 */           .then(argument("waypoint", (ArgumentType)StringArgumentType.greedyString()).executes(context -> addWaypoint(context, true)))))
/*    */ 
/*    */         
/* 53 */         .then(argument("waypoint", (ArgumentType)StringArgumentType.greedyString()).executes(context -> addWaypoint(context, false))));
/*    */ 
/*    */     
/* 56 */     builder.then(literal("delete").then(argument("waypoint", (ArgumentType)WaypointArgumentType.create()).executes(context -> {
/*    */               Waypoint waypoint = WaypointArgumentType.get(context);
/*    */               
/*    */               info("The waypoint (highlight)'%s'(default) has been deleted.", new Object[] { waypoint.name.get() });
/*    */               
/*    */               Waypoints.get().remove(waypoint);
/*    */               
/*    */               return 1;
/*    */             })));
/*    */     
/* 66 */     builder.then(literal("toggle").then(argument("waypoint", (ArgumentType)WaypointArgumentType.create()).executes(context -> {
/*    */               Waypoint waypoint = WaypointArgumentType.get(context);
/*    */               waypoint.visible.set(Boolean.valueOf(!((Boolean)waypoint.visible.get()).booleanValue()));
/*    */               Waypoints.get().save();
/*    */               return 1;
/*    */             })));
/*    */   }
/*    */ 
/*    */   
/*    */   private String waypointPos(Waypoint waypoint) {
/* 76 */     return "X: " + ((class_2338)waypoint.pos.get()).method_10263() + " Z: " + ((class_2338)waypoint.pos.get()).method_10260();
/*    */   }
/*    */   
/*    */   private String waypointFullPos(Waypoint waypoint) {
/* 80 */     return "X: " + ((class_2338)waypoint.pos.get()).method_10263() + ", Y: " + ((class_2338)waypoint.pos.get()).method_10264() + ", Z: " + ((class_2338)waypoint.pos.get()).method_10260();
/*    */   }
/*    */   
/*    */   private int addWaypoint(CommandContext<class_2172> context, boolean withCoords) {
/* 84 */     if (mc.field_1724 == null) return -1;
/*    */     
/* 86 */     class_2338 pos = withCoords ? BlockPosArgumentType.getBlockPos(context, "pos") : mc.field_1724.method_24515().method_10086(2);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 91 */     Waypoint waypoint = (new Waypoint.Builder()).name(StringArgumentType.getString(context, "waypoint")).pos(pos).dimension(PlayerUtils.getDimension()).build();
/*    */     
/* 93 */     Waypoints.get().add(waypoint);
/*    */     
/* 95 */     info("Created waypoint with name: (highlight)%s(default)", new Object[] { waypoint.name.get() });
/* 96 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\WaypointCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */