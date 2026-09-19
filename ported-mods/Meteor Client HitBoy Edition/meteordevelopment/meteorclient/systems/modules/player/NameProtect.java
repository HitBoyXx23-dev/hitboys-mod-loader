/*    */ package meteordevelopment.meteorclient.systems.modules.player;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.settings.StringSetting;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NameProtect
/*    */   extends Module
/*    */ {
/* 16 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 18 */   private final Setting<Boolean> nameProtect = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 19 */       .name("name-protect"))
/* 20 */       .description("Hides your name client-side."))
/* 21 */       .defaultValue(Boolean.valueOf(true)))
/* 22 */       .build());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Setting<String> name;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Setting<Boolean> skinProtect;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String username;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NameProtect() {
/* 43 */     super(Categories.Player, "name-protect", "Hide player names and skins.");
/*    */     Objects.requireNonNull(this.nameProtect);
/*    */     this.name = this.sgGeneral.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)(new StringSetting.Builder()).name("name")).description("Name to be replaced with.")).defaultValue("seasnail")).visible(this.nameProtect::get)).build());
/*    */     this.skinProtect = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("skin-protect")).description("Make players become Steves.")).defaultValue(Boolean.valueOf(true))).build());
/*    */     this.username = "If you see this, something is wrong."; } public void onActivate() {
/* 48 */     this.username = this.mc.method_1548().method_1676();
/*    */   }
/*    */   
/*    */   public String replaceName(String string) {
/* 52 */     if (string != null && isActive()) {
/* 53 */       return string.replace(this.username, (CharSequence)this.name.get());
/*    */     }
/*    */     
/* 56 */     return string;
/*    */   }
/*    */   
/*    */   public String getName(String original) {
/* 60 */     if (!((String)this.name.get()).isEmpty() && isActive()) {
/* 61 */       return (String)this.name.get();
/*    */     }
/*    */     
/* 64 */     return original;
/*    */   }
/*    */   
/*    */   public boolean skinProtect() {
/* 68 */     return (isActive() && ((Boolean)this.skinProtect.get()).booleanValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\NameProtect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */