/*     */ package meteordevelopment.meteorclient.utils.misc.text;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5251;
/*     */ import net.minecraft.class_5481;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextUtils
/*     */ {
/*     */   public static List<ColoredText> toColoredTextList(class_2561 text) {
/*  26 */     Deque<ColoredText> stack = new ArrayDeque<>();
/*  27 */     List<ColoredText> coloredTexts = new ArrayList<>();
/*  28 */     preOrderTraverse(text, stack, coloredTexts);
/*  29 */     coloredTexts.removeIf(e -> e.text().isEmpty());
/*  30 */     return coloredTexts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class_5250 parseOrderedText(class_5481 orderedText) {
/*  40 */     class_5250 parsedText = class_2561.method_43473();
/*  41 */     orderedText.accept((i, style, codePoint) -> {
/*     */           parsedText.method_10852((class_2561)class_2561.method_43470(new String(Character.toChars(codePoint))).method_10862(style));
/*     */           return true;
/*     */         });
/*  45 */     return parsedText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color getMostPopularColor(class_2561 text) {
/*  55 */     Object2IntMap.Entry<Color> biggestEntry = null;
/*  56 */     for (ObjectIterator<Object2IntMap.Entry<Color>> objectIterator = getColoredCharacterCount(toColoredTextList(text)).object2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Object2IntMap.Entry<Color> entry = objectIterator.next();
/*  57 */       if (biggestEntry == null) { biggestEntry = entry; continue; }
/*  58 */        if (entry.getIntValue() > biggestEntry.getIntValue()) biggestEntry = entry;  }
/*     */     
/*  60 */     return (biggestEntry == null) ? new Color(255, 255, 255) : (Color)biggestEntry.getKey();
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
/*     */   public static Object2IntMap<Color> getColoredCharacterCount(List<ColoredText> coloredTexts) {
/*  73 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/*     */     
/*  75 */     for (ColoredText coloredText : coloredTexts) {
/*  76 */       if (object2IntOpenHashMap.containsKey(coloredText.color())) {
/*     */         
/*  78 */         object2IntOpenHashMap.put(coloredText.color(), object2IntOpenHashMap.getInt(coloredText.color()) + coloredText.text().length());
/*     */         continue;
/*     */       } 
/*  81 */       object2IntOpenHashMap.put(coloredText.color(), coloredText.text().length());
/*     */     } 
/*     */ 
/*     */     
/*  85 */     return (Object2IntMap<Color>)object2IntOpenHashMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void preOrderTraverse(class_2561 text, Deque<ColoredText> stack, List<ColoredText> coloredTexts) {
/*     */     Color textColor;
/*  97 */     if (text == null) {
/*     */       return;
/*     */     }
/*     */     
/* 101 */     String textString = text.getString();
/*     */     
/* 103 */     class_5251 mcTextColor = text.method_10866().method_10973();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     if (mcTextColor == null) {
/* 111 */       if (stack.isEmpty()) {
/*     */         
/* 113 */         textColor = new Color(255, 255, 255);
/*     */       } else {
/*     */         
/* 116 */         textColor = ((ColoredText)stack.peek()).color();
/*     */       } 
/*     */     } else {
/* 119 */       textColor = new Color(text.method_10866().method_10973().method_27716() | 0xFF000000);
/*     */     } 
/*     */     
/* 122 */     ColoredText coloredText = new ColoredText(textString, textColor);
/* 123 */     coloredTexts.add(coloredText);
/* 124 */     stack.push(coloredText);
/*     */     
/* 126 */     for (class_2561 child : text.method_10855()) {
/* 127 */       preOrderTraverse(child, stack, coloredTexts);
/*     */     }
/* 129 */     stack.pop();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\text\TextUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */