/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.systems.hud.Hud;
/*    */ import meteordevelopment.meteorclient.systems.hud.HudElement;
/*    */ import meteordevelopment.meteorclient.systems.hud.elements.TextHud;
/*    */ import meteordevelopment.meteorclient.systems.modules.Category;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import net.minecraft.class_128;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_128.class})
/*    */ public abstract class CrashReportMixin
/*    */ {
/*    */   @Inject(method = {"method_555"}, at = {@At("TAIL")})
/*    */   private void onAddDetails(StringBuilder sb, CallbackInfo info) {
/* 27 */     sb.append("\n\n-- Meteor Client --\n\n");
/* 28 */     sb.append("Version: ").append(MeteorClient.VERSION).append("\n");
/* 29 */     if (!MeteorClient.BUILD_NUMBER.isEmpty()) {
/* 30 */       sb.append("Build: ").append(MeteorClient.BUILD_NUMBER).append("\n");
/*    */     }
/*    */     
/* 33 */     if (Modules.get() != null) {
/* 34 */       boolean modulesActive = false;
/* 35 */       for (Category category : Modules.loopCategories()) {
/* 36 */         List<Module> modules = Modules.get().getGroup(category);
/* 37 */         boolean categoryActive = false;
/*    */         
/* 39 */         for (Module module : modules) {
/* 40 */           if (module == null || !module.isActive())
/*    */             continue; 
/* 42 */           if (!modulesActive) {
/* 43 */             modulesActive = true;
/* 44 */             sb.append("\n[[ Active Modules ]]\n");
/*    */           } 
/*    */           
/* 47 */           if (!categoryActive) {
/* 48 */             categoryActive = true;
/* 49 */             sb.append("\n[")
/* 50 */               .append(category)
/* 51 */               .append("]:\n");
/*    */           } 
/*    */           
/* 54 */           sb.append(module.name).append("\n");
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 61 */     if (Hud.get() != null && (Hud.get()).active) {
/* 62 */       boolean hudActive = false;
/* 63 */       for (HudElement element : Hud.get()) {
/* 64 */         TextHud textHud; if (element == null || !element.isActive())
/*    */           continue; 
/* 66 */         if (!hudActive) {
/* 67 */           hudActive = true;
/* 68 */           sb.append("\n[[ Active Hud Elements ]]\n");
/*    */         } 
/*    */         
/* 71 */         if (element instanceof TextHud) { textHud = (TextHud)element; } else { sb.append(element.info.name).append("\n"); continue; }
/*    */         
/* 73 */         sb.append("Text\n{")
/* 74 */           .append((String)textHud.text.get())
/* 75 */           .append("}\n");
/* 76 */         if (textHud.shown.get() != TextHud.Shown.Always)
/* 77 */           sb.append("(")
/* 78 */             .append(textHud.shown.get())
/* 79 */             .append((String)textHud.condition.get())
/* 80 */             .append(")\n"); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\CrashReportMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */