/*    */ package meteordevelopment.meteorclient.renderer;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.meteor.CustomFontChangedEvent;
/*    */ import meteordevelopment.meteorclient.gui.WidgetScreen;
/*    */ import meteordevelopment.meteorclient.renderer.text.CustomTextRenderer;
/*    */ import meteordevelopment.meteorclient.renderer.text.FontFace;
/*    */ import meteordevelopment.meteorclient.renderer.text.FontFamily;
/*    */ import meteordevelopment.meteorclient.renderer.text.FontInfo;
/*    */ import meteordevelopment.meteorclient.systems.config.Config;
/*    */ import meteordevelopment.meteorclient.utils.PreInit;
/*    */ import meteordevelopment.meteorclient.utils.render.FontUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Fonts
/*    */ {
/* 27 */   public static final String[] BUILTIN_FONTS = new String[] { "JetBrains Mono", "Comfortaa", "Tw Cen MT", "Pixelation" };
/*    */   
/*    */   public static String DEFAULT_FONT_FAMILY;
/*    */   
/*    */   public static FontFace DEFAULT_FONT;
/* 32 */   public static final List<FontFamily> FONT_FAMILIES = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public static CustomTextRenderer RENDERER;
/*    */ 
/*    */   
/*    */   @PreInit
/*    */   public static void refresh() {
/* 40 */     FONT_FAMILIES.clear();
/*    */     
/* 42 */     for (String builtinFont : BUILTIN_FONTS) {
/* 43 */       FontUtils.loadBuiltin(FONT_FAMILIES, builtinFont);
/*    */     }
/*    */     
/* 46 */     for (String fontPath : FontUtils.getSearchPaths()) {
/* 47 */       FontUtils.loadSystem(FONT_FAMILIES, new File(fontPath));
/*    */     }
/*    */     
/* 50 */     FONT_FAMILIES.sort(Comparator.comparing(FontFamily::getName));
/*    */     
/* 52 */     MeteorClient.LOG.info("Found {} font families.", Integer.valueOf(FONT_FAMILIES.size()));
/*    */     
/* 54 */     DEFAULT_FONT_FAMILY = FontUtils.getBuiltinFontInfo(BUILTIN_FONTS[1]).family();
/* 55 */     DEFAULT_FONT = getFamily(DEFAULT_FONT_FAMILY).get(FontInfo.Type.Regular);
/*    */     
/* 57 */     Config config = Config.get();
/* 58 */     load((config != null) ? (FontFace)config.font.get() : DEFAULT_FONT);
/*    */   }
/*    */   
/*    */   public static void load(FontFace fontFace) {
/* 62 */     if (RENDERER != null) {
/* 63 */       if (RENDERER.fontFace.equals(fontFace))
/* 64 */         return;  RENDERER.destroy();
/*    */     } 
/*    */     
/*    */     try {
/* 68 */       RENDERER = new CustomTextRenderer(fontFace);
/* 69 */       MeteorClient.EVENT_BUS.post(CustomFontChangedEvent.get());
/*    */     }
/* 71 */     catch (Exception e) {
/* 72 */       if (fontFace.equals(DEFAULT_FONT)) {
/* 73 */         throw new RuntimeException("Failed to load default font: " + String.valueOf(fontFace), e);
/*    */       }
/*    */       
/* 76 */       MeteorClient.LOG.error("Failed to load font: {}", fontFace, e);
/* 77 */       load(DEFAULT_FONT);
/*    */     } 
/*    */     
/* 80 */     if (MeteorClient.mc.field_1755 instanceof WidgetScreen && ((Boolean)(Config.get()).customFont.get()).booleanValue()) {
/* 81 */       ((WidgetScreen)MeteorClient.mc.field_1755).invalidate();
/*    */     }
/*    */   }
/*    */   
/*    */   public static FontFamily getFamily(String name) {
/* 86 */     for (FontFamily fontFamily : FONT_FAMILIES) {
/* 87 */       if (fontFamily.getName().equalsIgnoreCase(name)) {
/* 88 */         return fontFamily;
/*    */       }
/*    */     } 
/*    */     
/* 92 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\Fonts.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */