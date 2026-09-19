/*    */ package meteordevelopment.meteorclient.gui.screens.settings.base;
/*    */ import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
/*    */ import java.util.Collection;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WPlus;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import net.minecraft.class_151;
/*    */ import net.minecraft.class_2378;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_5321;
/*    */ import net.minecraft.class_634;
/*    */ import net.minecraft.class_7225;
/*    */ import net.minecraft.class_7887;
/*    */ 
/*    */ public abstract class DynamicRegistryListSettingScreen<T> extends CollectionListSettingScreen<class_5321<T>> {
/*    */   protected final class_5321<class_2378<T>> registryKey;
/*    */   
/*    */   public DynamicRegistryListSettingScreen(GuiTheme theme, String title, Setting<?> setting, Collection<class_5321<T>> collection, class_5321<class_2378<T>> registryKey) {
/* 27 */     super(theme, title, setting, collection, createUniverse(collection, registryKey));
/*    */     
/* 29 */     this.registryKey = registryKey;
/*    */   }
/*    */   
/*    */   private static <T> Iterable<class_5321<T>> createUniverse(Collection<class_5321<T>> collection, class_5321<class_2378<T>> registryKey) {
/* 33 */     ReferenceOpenHashSet referenceOpenHashSet = new ReferenceOpenHashSet(collection);
/*    */     
/* 35 */     ((class_7225.class_7874)Optional.<class_634>ofNullable(class_310.method_1551().method_1562())
/* 36 */       .map(networkHandler -> networkHandler.method_29091())
/* 37 */       .orElseGet(class_7887::method_46817))
/* 38 */       .method_46759(registryKey)
/* 39 */       .ifPresent(registry -> {
/*    */           Objects.requireNonNull(set); registry.method_46754().forEach(set::add);
/* 41 */         }); return (Iterable<class_5321<T>>)referenceOpenHashSet;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void postWidgets(WTable left, WTable right) {
/* 46 */     if (!left.cells.isEmpty()) {
/* 47 */       left.add((WWidget)this.theme.horizontalSeparator()).expandX();
/* 48 */       left.row();
/*    */     } 
/*    */     
/* 51 */     WHorizontalList manualEntry = (WHorizontalList)left.add((WWidget)this.theme.horizontalList()).expandX().widget();
/* 52 */     WTextBox textBox = (WTextBox)manualEntry.add((WWidget)this.theme.textBox("minecraft:")).expandX().minWidth(120.0D).widget();
/* 53 */     ((WPlus)manualEntry.add((WWidget)this.theme.plus()).expandCellX().right().widget()).action = (() -> {
/*    */         String entry = textBox.get().trim();
/*    */         try {
/*    */           class_2960 id = entry.contains(":") ? class_2960.method_60654(entry) : class_2960.method_60656(entry);
/*    */           addValue(class_5321.method_29179(this.registryKey, id));
/* 58 */         } catch (class_151 class_151) {}
/*    */       });
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\settings\base\DynamicRegistryListSettingScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */