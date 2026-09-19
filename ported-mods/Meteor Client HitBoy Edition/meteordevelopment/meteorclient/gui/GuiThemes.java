/*     */ package meteordevelopment.meteorclient.gui;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;
/*     */ import meteordevelopment.meteorclient.utils.PostInit;
/*     */ import meteordevelopment.meteorclient.utils.PreInit;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2507;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiThemes
/*     */ {
/*  22 */   private static final File FOLDER = new File(MeteorClient.FOLDER, "gui");
/*  23 */   private static final File THEMES_FOLDER = new File(FOLDER, "themes");
/*  24 */   private static final File FILE = new File(FOLDER, "gui.nbt");
/*     */   
/*  26 */   private static final List<GuiTheme> themes = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private static GuiTheme theme;
/*     */ 
/*     */   
/*     */   @PreInit
/*     */   public static void init() {
/*  34 */     add((GuiTheme)new MeteorGuiTheme());
/*     */   }
/*     */   
/*     */   @PostInit
/*     */   public static void postInit() {
/*  39 */     if (FILE.exists()) {
/*     */       try {
/*  41 */         class_2487 tag = class_2507.method_10633(FILE.toPath());
/*     */         
/*  43 */         if (tag != null) select(tag.method_68564("currentTheme", "")); 
/*  44 */       } catch (IOException e) {
/*  45 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  49 */     if (theme == null) select("Meteor"); 
/*     */   }
/*     */   
/*     */   public static void add(GuiTheme theme) {
/*  53 */     for (ListIterator<GuiTheme> it = themes.listIterator(); it.hasNext();) {
/*  54 */       if (((GuiTheme)it.next()).name.equals(theme.name)) {
/*     */         
/*  56 */         it.set(theme);
/*     */         
/*  58 */         MeteorClient.LOG.error("Theme with the name '{}' has already been added.", theme.name);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  63 */     themes.add(theme);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void select(String name) {
/*  68 */     GuiTheme theme = null;
/*     */     
/*  70 */     for (GuiTheme t : themes) {
/*  71 */       if (t.name.equals(name)) {
/*  72 */         theme = t;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  77 */     if (theme != null) {
/*     */       
/*  79 */       saveTheme();
/*     */ 
/*     */       
/*  82 */       GuiThemes.theme = theme;
/*     */ 
/*     */       
/*     */       try {
/*  86 */         File file = new File(THEMES_FOLDER, (get()).name + ".nbt");
/*     */         
/*  88 */         if (file.exists()) {
/*  89 */           class_2487 tag = class_2507.method_10633(file.toPath());
/*  90 */           if (tag != null) get().fromTag(tag); 
/*     */         } 
/*  92 */       } catch (IOException e) {
/*  93 */         e.printStackTrace();
/*     */       } 
/*     */ 
/*     */       
/*  97 */       saveGlobal();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static GuiTheme get() {
/* 102 */     return theme;
/*     */   }
/*     */   
/*     */   public static String[] getNames() {
/* 106 */     String[] names = new String[themes.size()];
/*     */     
/* 108 */     for (int i = 0; i < themes.size(); i++) {
/* 109 */       names[i] = ((GuiTheme)themes.get(i)).name;
/*     */     }
/*     */     
/* 112 */     return names;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void saveTheme() {
/* 118 */     if (get() != null) {
/*     */       try {
/* 120 */         class_2487 tag = get().toTag();
/*     */         
/* 122 */         THEMES_FOLDER.mkdirs();
/* 123 */         class_2507.method_10630(tag, (new File(THEMES_FOLDER, (get()).name + ".nbt")).toPath());
/* 124 */       } catch (IOException e) {
/* 125 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void saveGlobal() {
/*     */     try {
/* 132 */       class_2487 tag = new class_2487();
/* 133 */       tag.method_10582("currentTheme", (get()).name);
/*     */       
/* 135 */       FOLDER.mkdirs();
/* 136 */       class_2507.method_10630(tag, FILE.toPath());
/* 137 */     } catch (IOException e) {
/* 138 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void save() {
/* 143 */     saveTheme();
/* 144 */     saveGlobal();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\GuiThemes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */