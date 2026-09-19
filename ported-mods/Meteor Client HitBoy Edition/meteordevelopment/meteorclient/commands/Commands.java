/*     */ package meteordevelopment.meteorclient.commands;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import java.util.ArrayList;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.commands.commands.BindsCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.CommandsCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.DamageCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.DisconnectCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.DismountCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.EnderChestCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.FakePlayerCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.FriendsCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.HelpCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.LocateCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.NotebotCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.PeekCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.ProfilesCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.ReloadCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.RotationCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.SaveMapCommand;
/*     */ import meteordevelopment.meteorclient.commands.commands.SettingCommand;
/*     */ import meteordevelopment.meteorclient.utils.PostInit;
/*     */ import net.minecraft.class_634;
/*     */ 
/*     */ public class Commands {
/*  27 */   public static final List<Command> COMMANDS = new ArrayList<>();
/*  28 */   public static CommandDispatcher<class_2172> DISPATCHER = new CommandDispatcher();
/*     */   
/*     */   @PostInit(dependencies = {PathManagers.class})
/*     */   public static void init() {
/*  32 */     add((Command)new VClipCommand());
/*  33 */     add((Command)new HClipCommand());
/*  34 */     add((Command)new DismountCommand());
/*  35 */     add((Command)new DisconnectCommand());
/*  36 */     add((Command)new DamageCommand());
/*  37 */     add((Command)new DropCommand());
/*  38 */     add((Command)new EnchantCommand());
/*  39 */     add((Command)new FakePlayerCommand());
/*  40 */     add((Command)new FriendsCommand());
/*  41 */     add((Command)new CommandsCommand());
/*  42 */     add((Command)new InventoryCommand());
/*  43 */     add((Command)new NbtCommand());
/*  44 */     add((Command)new NotebotCommand());
/*  45 */     add((Command)new PeekCommand());
/*  46 */     add((Command)new EnderChestCommand());
/*  47 */     add((Command)new ProfilesCommand());
/*  48 */     add((Command)new ReloadCommand());
/*  49 */     add((Command)new ResetCommand());
/*  50 */     add((Command)new SayCommand());
/*  51 */     add((Command)new ServerCommand());
/*  52 */     add((Command)new SwarmCommand());
/*  53 */     add((Command)new ToggleCommand());
/*  54 */     add((Command)new SettingCommand());
/*  55 */     add((Command)new SpectateCommand());
/*  56 */     add((Command)new GamemodeCommand());
/*  57 */     add((Command)new SaveMapCommand());
/*  58 */     add((Command)new MacroCommand());
/*  59 */     add((Command)new ModulesCommand());
/*  60 */     add((Command)new BindsCommand());
/*  61 */     add((Command)new GiveCommand());
/*  62 */     add((Command)new NameHistoryCommand());
/*  63 */     add((Command)new BindCommand());
/*  64 */     add((Command)new FovCommand());
/*  65 */     add((Command)new RotationCommand());
/*  66 */     add((Command)new WaypointCommand());
/*  67 */     add((Command)new InputCommand());
/*  68 */     add((Command)new WaspCommand());
/*  69 */     add((Command)new LocateCommand());
/*  70 */     add((Command)new HelpCommand());
/*     */     
/*  72 */     COMMANDS.sort(Comparator.comparing(Command::getName));
/*     */     
/*  74 */     MeteorClient.EVENT_BUS.subscribe(Commands.class);
/*     */   }
/*     */   
/*     */   public static void add(Command command) {
/*  78 */     COMMANDS.removeIf(existing -> existing.getName().equals(command.getName()));
/*  79 */     COMMANDS.add(command);
/*     */   }
/*     */   
/*     */   public static void dispatch(String message) throws CommandSyntaxException {
/*  83 */     DISPATCHER.execute(message, MeteorClient.mc.method_1562().method_2875());
/*     */   }
/*     */   
/*     */   public static Command get(String name) {
/*  87 */     for (Command command : COMMANDS) {
/*  88 */       if (command.getName().equals(name)) {
/*  89 */         return command;
/*     */       }
/*     */     } 
/*     */     
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private static void onJoin(GameJoinedEvent event) {
/* 115 */     class_634 networkHandler = MeteorClient.mc.method_1562();
/* 116 */     Command.REGISTRY_ACCESS = class_7157.method_46722((class_7225.class_7874)networkHandler.method_29091(), networkHandler.method_45735());
/*     */     
/* 118 */     DISPATCHER = new CommandDispatcher();
/* 119 */     for (Command command : COMMANDS)
/* 120 */       command.registerTo(DISPATCHER); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\Commands.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */