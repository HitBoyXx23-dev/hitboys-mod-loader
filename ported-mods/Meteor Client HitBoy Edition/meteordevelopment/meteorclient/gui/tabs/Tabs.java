/*    */ package meteordevelopment.meteorclient.gui.tabs;
/*    */ import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.gui.tabs.builtin.ConfigTab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.builtin.FriendsTab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.builtin.GuiTab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.builtin.HudTab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.builtin.MacrosTab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.builtin.ModulesTab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.builtin.PathManagerTab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.builtin.ProfilesTab;
/*    */ import meteordevelopment.meteorclient.pathing.PathManagers;
/*    */ import meteordevelopment.meteorclient.utils.PreInit;
/*    */ 
/*    */ public class Tabs {
/* 17 */   private static final List<Tab> tabs = new ArrayList<>();
/* 18 */   private static final Reference2ReferenceOpenHashMap<Class<? extends Tab>, Tab> tabInstances = new Reference2ReferenceOpenHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @PreInit(dependencies = {PathManagers.class})
/*    */   public static void init() {
/* 25 */     add((Tab)new ModulesTab());
/* 26 */     add((Tab)new ConfigTab());
/* 27 */     add((Tab)new GuiTab());
/* 28 */     add((Tab)new HudTab());
/* 29 */     add((Tab)new FriendsTab());
/* 30 */     add((Tab)new MacrosTab());
/* 31 */     add((Tab)new ProfilesTab());
/*    */     
/* 33 */     if (PathManagers.get().getSettings().get().sizeGroups() > 0) {
/* 34 */       add((Tab)new PathManagerTab());
/*    */     }
/*    */   }
/*    */   
/*    */   public static void add(Tab tab) {
/* 39 */     tabs.add(tab);
/* 40 */     tabInstances.put(tab.getClass(), tab);
/*    */   }
/*    */   
/*    */   public static List<Tab> get() {
/* 44 */     return tabs;
/*    */   }
/*    */   
/*    */   public static Tab get(Class<? extends Tab> klass) {
/* 48 */     return (Tab)tabInstances.get(klass);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\Tabs.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */