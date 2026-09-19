/*    */ package meteordevelopment.meteorclient.gui.screens.settings;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Reference2IntMap;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Comparator;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WIntEdit;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.utils.misc.Names;
/*    */ import net.minecraft.class_1291;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_1802;
/*    */ import net.minecraft.class_1844;
/*    */ import net.minecraft.class_9334;
/*    */ import org.apache.commons.lang3.Strings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatusEffectAmplifierMapSettingScreen
/*    */   extends WindowScreen
/*    */ {
/*    */   private final Setting<Reference2IntMap<class_1291>> setting;
/*    */   private WTable table;
/* 33 */   private String filterText = "";
/*    */   
/*    */   public StatusEffectAmplifierMapSettingScreen(GuiTheme theme, Setting<Reference2IntMap<class_1291>> setting) {
/* 36 */     super(theme, "Modify Amplifiers");
/*    */     
/* 38 */     this.setting = setting;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 43 */     WTextBox filter = (WTextBox)add((WWidget)this.theme.textBox("")).minWidth(400.0D).expandX().widget();
/* 44 */     filter.setFocused(true);
/* 45 */     filter.action = (() -> {
/*    */         this.filterText = filter.get().trim();
/*    */         
/*    */         this.table.clear();
/*    */         
/*    */         initTable();
/*    */       });
/* 52 */     this.table = (WTable)add((WWidget)this.theme.table()).expandX().widget();
/*    */     
/* 54 */     initTable();
/*    */   }
/*    */   
/*    */   private void initTable() {
/* 58 */     List<class_1291> statusEffects = new ArrayList<>((Collection<? extends class_1291>)((Reference2IntMap)this.setting.get()).keySet());
/* 59 */     statusEffects.sort(Comparator.comparing(Names::get));
/*    */     
/* 61 */     for (Iterator<class_1291> iterator = statusEffects.iterator(); iterator.hasNext(); ) { class_1291 statusEffect = iterator.next();
/* 62 */       String name = Names.get(statusEffect);
/* 63 */       if (!Strings.CI.contains(name, this.filterText))
/*    */         continue; 
/* 65 */       this.table.add((WWidget)this.theme.itemWithLabel(getPotionStack(statusEffect), name)).expandCellX();
/*    */       
/* 67 */       WIntEdit level = this.theme.intEdit(((Reference2IntMap)this.setting.get()).getInt(statusEffect), 0, 2147483647, true);
/* 68 */       level.action = (() -> {
/*    */           ((Reference2IntMap)this.setting.get()).put(statusEffect, level.get());
/*    */           
/*    */           this.setting.onChanged();
/*    */         });
/* 73 */       this.table.add((WWidget)level).minWidth(50.0D);
/* 74 */       this.table.row(); }
/*    */   
/*    */   }
/*    */   
/*    */   private class_1799 getPotionStack(class_1291 effect) {
/* 79 */     class_1799 potion = class_1802.field_8574.method_7854();
/*    */     
/* 81 */     potion.method_57379(class_9334.field_49651, new class_1844(((class_1844)potion
/*    */ 
/*    */           
/* 84 */           .method_58694(class_9334.field_49651)).comp_2378(), 
/* 85 */           Optional.of(Integer.valueOf(effect.method_5556())), ((class_1844)potion
/* 86 */           .method_58694(class_9334.field_49651)).comp_2380(), 
/* 87 */           Optional.empty()));
/*    */ 
/*    */ 
/*    */     
/* 91 */     return potion;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\settings\StatusEffectAmplifierMapSettingScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */