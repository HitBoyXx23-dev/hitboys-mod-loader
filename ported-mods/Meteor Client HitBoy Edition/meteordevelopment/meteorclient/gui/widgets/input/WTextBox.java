/*     */ package meteordevelopment.meteorclient.gui.widgets.input;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.GuiKeyEvents;
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.utils.CharFilter;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_11905;
/*     */ import net.minecraft.class_11908;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_6417;
/*     */ import org.apache.commons.lang3.SystemUtils;
/*     */ 
/*     */ public abstract class WTextBox extends WWidget {
/*     */   private static final Renderer DEFAULT_RENDERER;
/*     */   public Runnable action;
/*     */   public Runnable actionOnUnfocused;
/*     */   protected String text;
/*     */   protected String placeholder;
/*     */   protected CharFilter filter;
/*     */   protected final Renderer renderer;
/*     */   
/*     */   static {
/*  31 */     DEFAULT_RENDERER = ((renderer, x, y, text, color) -> renderer.text(text, x, y, color, false));
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
/*  42 */   protected DoubleList textWidths = (DoubleList)new DoubleArrayList();
/*     */   
/*     */   protected int cursor;
/*     */   protected double textStart;
/*     */   protected boolean selecting;
/*     */   protected boolean doubleClick;
/*     */   protected int selectionStart;
/*     */   protected int selectionEnd;
/*     */   private int preSelectionCursor;
/*     */   private List<String> completions;
/*     */   private int completionsStart;
/*     */   private WContainer completionsW;
/*     */   
/*     */   public WTextBox(String text, CharFilter filter, Class<? extends Renderer> renderer) {
/*  56 */     this(text, (String)null, filter, renderer);
/*     */   }
/*     */   
/*     */   public WTextBox(String text, String placeholder, CharFilter filter, Class<? extends Renderer> renderer) {
/*  60 */     this.text = text;
/*  61 */     this.placeholder = placeholder;
/*  62 */     this.filter = filter;
/*     */     
/*     */     try {
/*  65 */       this.renderer = (renderer != null) ? renderer.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]) : DEFAULT_RENDERER;
/*  66 */     } catch (InstantiationException|IllegalAccessException|java.lang.reflect.InvocationTargetException|NoSuchMethodException e) {
/*  67 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCalculateSize() {
/*  77 */     double pad = pad();
/*  78 */     double s = this.theme.textHeight();
/*     */     
/*  80 */     this.width = pad + s + pad;
/*  81 */     this.height = pad + s + pad;
/*     */     
/*  83 */     calculateTextWidths();
/*     */     
/*  85 */     if (this.completionsW != null) this.completionsW.calculateSize();
/*     */   
/*     */   }
/*     */   
/*     */   public void calculateWidgetPositions() {
/*  90 */     super.calculateWidgetPositions();
/*     */     
/*  92 */     if (this.completionsW != null) {
/*  93 */       this.completionsW.x = this.x;
/*  94 */       this.completionsW.y = this.y + this.height;
/*  95 */       this.completionsW.calculateWidgetPositions();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(double deltaX, double deltaY) {
/* 101 */     super.move(deltaX, deltaY);
/* 102 */     if (this.completionsW != null) this.completionsW.move(deltaX, deltaY); 
/*     */   }
/*     */   
/*     */   protected double maxTextWidth() {
/* 106 */     return this.width - pad() * 2.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMouseClicked(class_11909 click, boolean doubled) {
/* 111 */     if (this.mouseOver) {
/* 112 */       if (click.method_74245() == 1) {
/* 113 */         if (!this.text.isEmpty()) {
/* 114 */           this.text = "";
/* 115 */           this.cursor = 0;
/* 116 */           this.selectionStart = 0;
/* 117 */           this.selectionEnd = 0;
/*     */           
/* 119 */           runAction();
/*     */         }
/*     */       
/* 122 */       } else if (click.method_74245() == 0) {
/* 123 */         this.selecting = true;
/*     */         
/* 125 */         double overflowWidth = getOverflowWidthForRender();
/* 126 */         double relativeMouseX = click.comp_4798() - this.x + overflowWidth;
/* 127 */         double pad = pad();
/*     */         
/* 129 */         double smallestDifference = Double.MAX_VALUE;
/*     */         
/* 131 */         this.cursor = this.text.length();
/*     */         
/* 133 */         for (int i = 0; i < this.textWidths.size(); i++) {
/* 134 */           double difference = Math.abs(this.textWidths.getDouble(i) + pad - relativeMouseX);
/*     */           
/* 136 */           if (difference < smallestDifference) {
/* 137 */             smallestDifference = difference;
/* 138 */             this.cursor = i;
/*     */           } 
/*     */         } 
/*     */         
/* 142 */         if (doubled && this.cursor == this.preSelectionCursor) {
/* 143 */           this.doubleClick = true;
/* 144 */           resetSelection();
/*     */           
/* 146 */           this.selectionStart = this.cursor - countToNextSpace(true);
/* 147 */           this.selectionEnd = this.cursor += countToNextSpace(false);
/*     */           
/* 149 */           return true;
/*     */         } 
/*     */         
/* 152 */         this.preSelectionCursor = this.cursor;
/* 153 */         resetSelection();
/* 154 */         cursorChanged();
/*     */       } 
/*     */       
/* 157 */       setFocused(true);
/* 158 */       return true;
/*     */     } 
/*     */     
/* 161 */     if (this.focused) setFocused(false);
/*     */     
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMouseMoved(double mouseX, double mouseY, double lastMouseX, double lastMouseY) {
/* 168 */     if (!this.selecting)
/*     */       return; 
/* 170 */     double overflowWidth = getOverflowWidthForRender();
/* 171 */     double relativeMouseX = mouseX - this.x + overflowWidth;
/* 172 */     double pad = pad();
/*     */     
/* 174 */     double smallestDifference = Double.MAX_VALUE;
/*     */     
/* 176 */     int best = 0;
/* 177 */     for (int i = 0; i < this.textWidths.size(); i++) {
/* 178 */       double difference = Math.abs(this.textWidths.getDouble(i) + pad - relativeMouseX);
/*     */       
/* 180 */       if (difference < smallestDifference) {
/* 181 */         best = i;
/* 182 */         smallestDifference = difference;
/* 183 */         if (!this.doubleClick) {
/* 184 */           if (i < this.preSelectionCursor) {
/* 185 */             this.selectionStart = i;
/* 186 */             this.cursor = i;
/* 187 */           } else if (i > this.preSelectionCursor) {
/* 188 */             this.selectionEnd = i;
/* 189 */             this.cursor = i;
/*     */           } else {
/* 191 */             this.cursor = this.preSelectionCursor;
/* 192 */             resetSelection();
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 199 */     if (this.doubleClick) {
/* 200 */       if (best < this.selectionStart) {
/* 201 */         this.selectionStart = best - countToNextSpace(true, best);
/* 202 */         this.cursor = this.selectionStart;
/*     */       }
/* 204 */       else if (best > this.selectionEnd) {
/* 205 */         this.selectionEnd = best + countToNextSpace(false, best);
/* 206 */         this.cursor = this.selectionEnd;
/*     */       
/*     */       }
/* 209 */       else if (this.cursor == this.selectionStart) {
/* 210 */         int nextRight = countToNextSpace(false);
/* 211 */         if (best > this.cursor + nextRight) {
/* 212 */           this.selectionStart = this.cursor = this.cursor + nextRight + 1;
/* 213 */           if (this.selectionStart <= this.preSelectionCursor && this.selectionStart + countToNextSpace(false) >= this.preSelectionCursor) {
/* 214 */             this.cursor = this.selectionEnd;
/*     */           }
/*     */         }
/*     */       
/* 218 */       } else if (this.cursor == this.selectionEnd) {
/* 219 */         int nextLeft = countToNextSpace(true);
/* 220 */         if (best < this.cursor - nextLeft) {
/* 221 */           this.selectionEnd = this.cursor = this.cursor - nextLeft - 1;
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMouseReleased(class_11909 click) {
/* 230 */     this.selecting = false;
/* 231 */     this.doubleClick = false;
/*     */     
/* 233 */     if (this.selectionStart < this.preSelectionCursor && this.preSelectionCursor == this.selectionEnd) {
/* 234 */       this.cursor = this.selectionStart;
/*     */     }
/* 236 */     else if (this.selectionEnd > this.preSelectionCursor && this.preSelectionCursor == this.selectionStart) {
/* 237 */       this.cursor = this.selectionEnd;
/*     */     } 
/*     */     
/* 240 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onKeyPressed(class_11908 input) {
/* 245 */     if (!this.focused) return false;
/*     */     
/* 247 */     boolean control = class_6417.field_52734 ? ((input.comp_4797() == 8)) : ((input.comp_4797() == 2));
/*     */     
/* 249 */     if (control && input.comp_4795() == 67) {
/* 250 */       if (this.cursor != this.selectionStart || this.cursor != this.selectionEnd) {
/* 251 */         MeteorClient.mc.field_1774.method_1455(this.text.substring(this.selectionStart, this.selectionEnd));
/*     */       }
/* 253 */       return true;
/*     */     } 
/* 255 */     if (control && input.comp_4795() == 88) {
/* 256 */       if (this.cursor != this.selectionStart || this.cursor != this.selectionEnd) {
/* 257 */         MeteorClient.mc.field_1774.method_1455(this.text.substring(this.selectionStart, this.selectionEnd));
/* 258 */         clearSelection();
/*     */       } 
/*     */       
/* 261 */       return true;
/*     */     } 
/* 263 */     if (control && input.comp_4795() == 65) {
/* 264 */       this.cursor = this.text.length();
/* 265 */       this.selectionStart = 0;
/* 266 */       this.selectionEnd = this.cursor;
/*     */     }
/* 268 */     else if (input.comp_4797() == ((class_6417.field_52734 ? 8 : 2) | 0x1) && input.comp_4795() == 65) {
/* 269 */       resetSelection();
/*     */     } else {
/* 271 */       if (input.comp_4795() == 257 || input.comp_4795() == 335) {
/* 272 */         setFocused(false);
/*     */         
/* 274 */         if (this.actionOnUnfocused != null) this.actionOnUnfocused.run(); 
/* 275 */         return true;
/*     */       } 
/* 277 */       if (input.comp_4795() == 258 && this.completionsW != null) {
/* 278 */         String completion = ((ICompletionItem)((Cell)this.completionsW.cells.get(getSelectedCompletion())).widget()).getCompletion();
/*     */         
/* 280 */         StringBuilder sb = new StringBuilder(this.text.length() + completion.length() + 1);
/* 281 */         String a = this.text.substring(0, this.cursor);
/* 282 */         sb.append(a);
/*     */         
/* 284 */         for (int i = 0; i < completion.length() - 1; i++) {
/* 285 */           if (a.endsWith(completion.substring(0, completion.length() - i - 1))) {
/* 286 */             completion = completion.substring(completion.length() - i - 1);
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 291 */         sb.append(completion);
/* 292 */         if (completion.endsWith("(")) sb.append(')');
/*     */         
/* 294 */         sb.append(this.text, this.cursor, this.text.length());
/*     */         
/* 296 */         this.text = sb.toString();
/* 297 */         this.cursor += completion.length();
/* 298 */         resetSelection();
/* 299 */         runAction();
/*     */         
/* 301 */         return true;
/*     */       } 
/*     */     } 
/* 304 */     return onKeyRepeated(input);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onKeyRepeated(class_11908 input) {
/* 309 */     if (!this.focused) return false;
/*     */     
/* 311 */     boolean control = class_6417.field_52734 ? ((input.comp_4797() == 8)) : ((input.comp_4797() == 2));
/* 312 */     boolean shift = (input.comp_4797() == 1);
/* 313 */     boolean controlShift = (input.comp_4797() == ((SystemUtils.IS_OS_WINDOWS ? 4 : (class_6417.field_52734 ? 8 : 2)) | 0x1));
/* 314 */     boolean altShift = (input.comp_4797() == ((SystemUtils.IS_OS_WINDOWS ? 2 : 4) | 0x1));
/*     */     
/* 316 */     if (control && input.comp_4795() == 86) {
/* 317 */       clearSelection();
/*     */       
/* 319 */       String preText = this.text;
/* 320 */       String clipboard = MeteorClient.mc.field_1774.method_1460();
/* 321 */       int addedChars = 0;
/*     */       
/* 323 */       StringBuilder sb = new StringBuilder(this.text.length() + clipboard.length());
/* 324 */       sb.append(this.text);
/*     */       
/* 326 */       for (int i = 0; i < clipboard.length(); i++) {
/* 327 */         char c = clipboard.charAt(i);
/* 328 */         if (this.filter.filter(sb.toString(), c)) {
/* 329 */           sb.insert(this.cursor + addedChars, c);
/* 330 */           addedChars++;
/*     */         } 
/*     */       } 
/*     */       
/* 334 */       this.text = sb.toString();
/* 335 */       this.cursor += addedChars;
/* 336 */       resetSelection();
/*     */       
/* 338 */       if (!this.text.equals(preText)) runAction(); 
/* 339 */       return true;
/*     */     } 
/* 341 */     if (input.comp_4795() == 259) {
/* 342 */       if (this.cursor > 0 && this.cursor == this.selectionStart && this.cursor == this.selectionEnd) {
/* 343 */         String preText = this.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 349 */         int count = (input.comp_4797() == (SystemUtils.IS_OS_WINDOWS ? 4 : (class_6417.field_52734 ? 8 : 2))) ? this.cursor : ((input.comp_4797() == (SystemUtils.IS_OS_WINDOWS ? 2 : 4)) ? countToNextSpace(true) : 1);
/*     */         
/* 351 */         this.text = this.text.substring(0, this.cursor - count) + this.text.substring(0, this.cursor - count);
/* 352 */         this.cursor -= count;
/* 353 */         resetSelection();
/*     */         
/* 355 */         if (!this.text.equals(preText)) runAction();
/*     */       
/* 357 */       } else if (this.cursor != this.selectionStart || this.cursor != this.selectionEnd) {
/* 358 */         clearSelection();
/*     */       } 
/*     */       
/* 361 */       return true;
/*     */     } 
/* 363 */     if (input.comp_4795() == 261) {
/* 364 */       if (this.cursor == this.selectionStart && this.cursor == this.selectionEnd) {
/* 365 */         if (this.cursor < this.text.length()) {
/* 366 */           String preText = this.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 372 */           int count = (input.comp_4797() == (SystemUtils.IS_OS_WINDOWS ? 4 : (class_6417.field_52734 ? 8 : 2))) ? (this.text.length() - this.cursor) : ((input.comp_4797() == (SystemUtils.IS_OS_WINDOWS ? 2 : 4)) ? countToNextSpace(false) : 1);
/*     */           
/* 374 */           this.text = this.text.substring(0, this.cursor) + this.text.substring(0, this.cursor);
/*     */           
/* 376 */           if (!this.text.equals(preText)) runAction();
/*     */         
/*     */         } 
/*     */       } else {
/* 380 */         clearSelection();
/*     */       } 
/* 382 */       return true;
/*     */     } 
/* 384 */     if (input.comp_4795() == 263) {
/* 385 */       if (this.cursor > 0) {
/*     */         
/* 387 */         if (input.comp_4797() == (SystemUtils.IS_OS_WINDOWS ? 2 : 4)) {
/* 388 */           this.cursor -= countToNextSpace(true);
/* 389 */           resetSelection();
/*     */         
/*     */         }
/* 392 */         else if (input.comp_4797() == (SystemUtils.IS_OS_WINDOWS ? 4 : (class_6417.field_52734 ? 8 : 2))) {
/* 393 */           this.cursor = 0;
/* 394 */           resetSelection();
/*     */         
/*     */         }
/* 397 */         else if (altShift) {
/* 398 */           if (this.cursor == this.selectionEnd && this.cursor != this.selectionStart) {
/* 399 */             this.cursor -= countToNextSpace(true);
/* 400 */             if (this.cursor >= this.selectionStart) { this.selectionEnd = this.cursor; }
/*     */             else
/* 402 */             { this.selectionEnd = this.selectionStart;
/* 403 */               this.selectionStart = this.cursor; }
/*     */           
/*     */           } else {
/*     */             
/* 407 */             this.cursor -= countToNextSpace(true);
/* 408 */             this.selectionStart = this.cursor;
/*     */           }
/*     */         
/*     */         }
/* 412 */         else if (controlShift) {
/* 413 */           if (this.cursor == this.selectionEnd && this.cursor != this.selectionStart) {
/* 414 */             this.selectionEnd = this.selectionStart;
/*     */           }
/* 416 */           this.selectionStart = 0;
/*     */           
/* 418 */           this.cursor = 0;
/*     */         
/*     */         }
/* 421 */         else if (shift) {
/* 422 */           if (this.cursor == this.selectionEnd && this.cursor != this.selectionStart) {
/* 423 */             this.selectionEnd = this.cursor - 1;
/*     */           } else {
/*     */             
/* 426 */             this.selectionStart = this.cursor - 1;
/*     */           } 
/*     */           
/* 429 */           this.cursor--;
/*     */         }
/*     */         else {
/*     */           
/* 433 */           if (this.cursor == this.selectionEnd && this.cursor != this.selectionStart) {
/* 434 */             this.cursor = this.selectionStart;
/*     */           } else {
/*     */             
/* 437 */             this.cursor--;
/*     */           } 
/*     */           
/* 440 */           resetSelection();
/*     */         } 
/*     */         
/* 443 */         cursorChanged();
/*     */       }
/* 445 */       else if (this.selectionStart != this.selectionEnd && this.selectionStart == 0 && input.comp_4797() == 0) {
/* 446 */         this.cursor = 0;
/* 447 */         resetSelection();
/* 448 */         cursorChanged();
/*     */       } 
/*     */       
/* 451 */       return true;
/*     */     } 
/* 453 */     if (input.comp_4795() == 262) {
/* 454 */       if (this.cursor < this.text.length()) {
/*     */         
/* 456 */         if (input.comp_4797() == (SystemUtils.IS_OS_WINDOWS ? 2 : 4)) {
/* 457 */           this.cursor += countToNextSpace(false);
/* 458 */           resetSelection();
/*     */         
/*     */         }
/* 461 */         else if (input.comp_4797() == (SystemUtils.IS_OS_WINDOWS ? 4 : (class_6417.field_52734 ? 8 : 2))) {
/* 462 */           this.cursor = this.text.length();
/* 463 */           resetSelection();
/*     */         
/*     */         }
/* 466 */         else if (altShift) {
/* 467 */           if (this.cursor == this.selectionStart && this.cursor != this.selectionEnd) {
/* 468 */             this.cursor += countToNextSpace(false);
/* 469 */             if (this.cursor <= this.selectionEnd) { this.selectionStart = this.cursor; }
/*     */             else
/* 471 */             { this.selectionStart = this.selectionEnd;
/* 472 */               this.selectionEnd = this.cursor; }
/*     */           
/*     */           } else {
/*     */             
/* 476 */             this.cursor += countToNextSpace(false);
/* 477 */             this.selectionEnd = this.cursor;
/*     */           }
/*     */         
/*     */         }
/* 481 */         else if (controlShift) {
/* 482 */           if (this.cursor == this.selectionStart && this.cursor != this.selectionEnd) {
/* 483 */             this.selectionStart = this.selectionEnd;
/*     */           }
/* 485 */           this.cursor = this.text.length();
/* 486 */           this.selectionEnd = this.cursor;
/*     */         
/*     */         }
/* 489 */         else if (shift) {
/* 490 */           if (this.cursor == this.selectionStart && this.cursor != this.selectionEnd) {
/* 491 */             this.selectionStart = this.cursor + 1;
/*     */           } else {
/*     */             
/* 494 */             this.selectionEnd = this.cursor + 1;
/*     */           } 
/*     */           
/* 497 */           this.cursor++;
/*     */         }
/*     */         else {
/*     */           
/* 501 */           if (this.cursor == this.selectionStart && this.cursor != this.selectionEnd) {
/* 502 */             this.cursor = this.selectionEnd;
/*     */           } else {
/*     */             
/* 505 */             this.cursor++;
/*     */           } 
/*     */           
/* 508 */           resetSelection();
/*     */         } 
/*     */         
/* 511 */         cursorChanged();
/*     */       }
/* 513 */       else if (this.selectionStart != this.selectionEnd && this.selectionEnd == this.text.length() && input.comp_4797() == 0) {
/* 514 */         this.cursor = this.text.length();
/* 515 */         resetSelection();
/* 516 */         cursorChanged();
/*     */       } 
/*     */       
/* 519 */       return true;
/*     */     } 
/* 521 */     if (input.comp_4795() == 264 && this.completionsW != null) {
/* 522 */       int currentI = getSelectedCompletion();
/*     */       
/* 524 */       if (currentI == Math.min(5, this.completions.size() - 1)) {
/* 525 */         if (this.completionsStart + 6 < this.completions.size()) {
/* 526 */           this.completionsStart++;
/* 527 */           createCompletions(this.completionsStart + currentI);
/*     */         } 
/*     */       } else {
/*     */         
/* 531 */         ((ICompletionItem)((Cell)this.completionsW.cells.get(currentI)).widget()).setSelected(false);
/* 532 */         ((ICompletionItem)((Cell)this.completionsW.cells.get(currentI + 1)).widget()).setSelected(true);
/*     */       } 
/*     */       
/* 535 */       return true;
/*     */     } 
/* 537 */     if (input.comp_4795() == 265 && this.completionsW != null) {
/* 538 */       int currentI = getSelectedCompletion();
/*     */       
/* 540 */       if (currentI == 0) {
/* 541 */         if (this.completionsStart > 0) {
/* 542 */           this.completionsStart--;
/* 543 */           createCompletions(this.completionsStart + currentI);
/*     */         } 
/*     */       } else {
/*     */         
/* 547 */         ((ICompletionItem)((Cell)this.completionsW.cells.get(currentI)).widget()).setSelected(false);
/* 548 */         ((ICompletionItem)((Cell)this.completionsW.cells.get(currentI - 1)).widget()).setSelected(true);
/*     */       } 
/*     */       
/* 551 */       return true;
/*     */     } 
/*     */     
/* 554 */     return false;
/*     */   }
/*     */   
/*     */   private int getSelectedCompletion() {
/* 558 */     for (int i = 0; i < this.completionsW.cells.size(); ) {
/* 559 */       ICompletionItem item = (ICompletionItem)((Cell)this.completionsW.cells.get(i)).widget();
/* 560 */       if (!item.isSelected()) {
/*     */         i++; continue;
/* 562 */       }  return i;
/*     */     } 
/*     */     
/* 565 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCharTyped(class_11905 input) {
/* 570 */     if (!this.focused) return false;
/*     */     
/* 572 */     if (this.filter.filter(this.text, input.comp_4793())) {
/* 573 */       clearSelection();
/*     */       
/* 575 */       this.text = this.text.substring(0, this.cursor) + this.text.substring(0, this.cursor) + input.method_74226();
/*     */       
/* 577 */       this.cursor++;
/* 578 */       resetSelection();
/*     */       
/* 580 */       runAction();
/* 581 */       return true;
/*     */     } 
/*     */     
/* 584 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 589 */     if (isFocused()) GuiKeyEvents.canUseKeys = false;
/*     */     
/* 591 */     if (this.completionsW != null && this.focused) {
/* 592 */       renderer.absolutePost(() -> {
/*     */             renderer.beginRender();
/*     */             
/*     */             this.completionsW.render(renderer, mouseX, mouseY, delta);
/*     */             renderer.endRender();
/*     */           });
/*     */     }
/* 599 */     return super.render(renderer, mouseX, mouseY, delta);
/*     */   }
/*     */   
/*     */   private void clearSelection() {
/* 603 */     if (this.selectionStart == this.selectionEnd)
/*     */       return; 
/* 605 */     String preText = this.text;
/*     */     
/* 607 */     this.text = this.text.substring(0, this.selectionStart) + this.text.substring(0, this.selectionStart);
/*     */     
/* 609 */     this.cursor = this.selectionStart;
/* 610 */     this.selectionEnd = this.cursor;
/*     */     
/* 612 */     if (!this.text.equals(preText)) runAction(); 
/*     */   }
/*     */   
/*     */   private void resetSelection() {
/* 616 */     this.selectionStart = this.cursor;
/* 617 */     this.selectionEnd = this.cursor;
/*     */   }
/*     */   
/*     */   private int countToNextSpace(boolean toLeft) {
/* 621 */     return countToNextSpace(toLeft, this.cursor);
/*     */   }
/*     */   
/*     */   private int countToNextSpace(boolean toLeft, int startPos) {
/* 625 */     int count = 0;
/* 626 */     boolean hadNonSpace = false;
/*     */     int i;
/* 628 */     for (i = startPos; toLeft ? (i >= 0) : (i < this.text.length()); i += toLeft ? -1 : 1) {
/* 629 */       int j = i;
/* 630 */       if (toLeft) j--;
/*     */       
/* 632 */       if (j < this.text.length()) {
/* 633 */         if (j < 0)
/*     */           break; 
/* 635 */         if (hadNonSpace && Character.isWhitespace(this.text.charAt(j)))
/* 636 */           break;  if (!Character.isWhitespace(this.text.charAt(j))) hadNonSpace = true;
/*     */         
/* 638 */         count++;
/*     */       } 
/*     */     } 
/* 641 */     return count;
/*     */   }
/*     */   
/*     */   private void calculateTextWidths() {
/* 645 */     this.textWidths.clear();
/*     */     
/* 647 */     for (int i = 0; i <= this.text.length(); i++) {
/* 648 */       this.textWidths.add(this.theme.textWidth(this.text, i, false));
/*     */     }
/*     */   }
/*     */   
/*     */   private void runAction() {
/* 653 */     calculateTextWidths();
/* 654 */     cursorChanged();
/*     */     
/* 656 */     if (this.action != null) this.action.run(); 
/*     */   }
/*     */   
/*     */   private double textWidth() {
/* 660 */     return this.textWidths.isEmpty() ? 0.0D : this.textWidths.getDouble(this.textWidths.size() - 1);
/*     */   }
/*     */   
/*     */   private void cursorChanged() {
/* 664 */     double cursor = getCursorTextWidth(-2);
/* 665 */     if (cursor < this.textStart) {
/* 666 */       this.textStart -= this.textStart - cursor;
/*     */     }
/*     */     
/* 669 */     cursor = getCursorTextWidth(2);
/* 670 */     if (cursor > this.textStart + maxTextWidth()) {
/* 671 */       this.textStart += cursor - this.textStart + maxTextWidth();
/*     */     }
/*     */     
/* 674 */     this.textStart = class_3532.method_15350(this.textStart, 0.0D, Math.max(textWidth() - maxTextWidth(), 0.0D));
/*     */     
/* 676 */     onCursorChanged();
/*     */ 
/*     */     
/* 679 */     this.completions = this.renderer.getCompletions(this.text, this.cursor);
/* 680 */     this.completionsStart = 0;
/* 681 */     this.completionsW = null;
/* 682 */     if (this.completions != null && !this.completions.isEmpty()) createCompletions(0); 
/*     */   }
/*     */   protected void onCursorChanged() {}
/*     */   
/*     */   private void createCompletions(int selected) {
/* 687 */     this.completionsW = createCompletionsRootWidget();
/* 688 */     this.completionsW.theme = this.theme;
/*     */     
/* 690 */     int max = Math.min(this.completions.size(), this.completionsStart + 6);
/* 691 */     for (int i = this.completionsStart; i < max; i++) {
/* 692 */       WWidget widget = createCompletionsValueWidth(this.completions.get(i), (i == selected));
/* 693 */       widget.theme = this.theme;
/*     */       
/* 695 */       Cell<?> cell = this.completionsW.add(widget).expandX().padHorizontal(4.0D);
/* 696 */       if (i == max - 1) cell.padBottom(4.0D);
/*     */     
/*     */     } 
/* 699 */     this.completionsW.calculateSize();
/* 700 */     this.completionsW.x = Math.min(Math.max(this.x - pad() * 2.0D + getTextWidth(this.cursor) - getOverflowWidthForRender(), this.x), this.x + this.width - this.completionsW.width);
/* 701 */     this.completionsW.y = this.y + this.height;
/* 702 */     this.completionsW.calculateWidgetPositions();
/*     */   }
/*     */   
/*     */   protected double getTextWidth(int pos) {
/* 706 */     if (this.textWidths.isEmpty()) return 0.0D;
/*     */     
/* 708 */     if (pos < 0) { pos = 0; }
/* 709 */     else if (pos >= this.textWidths.size()) { pos = this.textWidths.size() - 1; }
/*     */     
/* 711 */     return this.textWidths.getDouble(pos);
/*     */   }
/*     */   
/*     */   protected double getCursorTextWidth(int offset) {
/* 715 */     return getTextWidth(this.cursor + offset);
/*     */   }
/*     */   
/*     */   protected double getOverflowWidthForRender() {
/* 719 */     return this.textStart;
/*     */   }
/*     */   
/*     */   public String get() {
/* 723 */     return this.text;
/*     */   }
/*     */   
/*     */   public void set(String text) {
/* 727 */     this.text = text;
/*     */     
/* 729 */     this.cursor = class_3532.method_15340(this.cursor, 0, text.length());
/* 730 */     this.selectionStart = this.cursor;
/* 731 */     this.selectionEnd = this.cursor;
/*     */     
/* 733 */     calculateTextWidths();
/* 734 */     cursorChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFocused(boolean focused) {
/* 739 */     if (this.focused && !focused && this.actionOnUnfocused != null) this.actionOnUnfocused.run();
/*     */     
/* 741 */     boolean wasJustFocused = (focused && !this.focused);
/*     */     
/* 743 */     this.focused = focused;
/*     */     
/* 745 */     resetSelection();
/*     */     
/* 747 */     if (wasJustFocused) onCursorChanged(); 
/*     */   }
/*     */   
/*     */   public void setCursorMax() {
/* 751 */     this.cursor = this.text.length();
/*     */   }
/*     */   protected abstract WContainer createCompletionsRootWidget();
/*     */   
/*     */   protected abstract <T extends WWidget & ICompletionItem> T createCompletionsValueWidth(String paramString, boolean paramBoolean);
/*     */   
/*     */   public static interface Renderer { default List<String> getCompletions(String text, int position) {
/* 758 */       return null;
/*     */     }
/*     */     
/*     */     void render(GuiRenderer param1GuiRenderer, double param1Double1, double param1Double2, String param1String, Color param1Color); }
/*     */ 
/*     */   
/*     */   public static interface ICompletionItem {
/*     */     boolean isSelected();
/*     */     
/*     */     void setSelected(boolean param1Boolean);
/*     */     
/*     */     String getCompletion();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\input\WTextBox.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */