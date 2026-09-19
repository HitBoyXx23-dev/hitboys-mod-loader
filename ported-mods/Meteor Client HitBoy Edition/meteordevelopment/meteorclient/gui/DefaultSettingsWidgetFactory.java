/*     */ package meteordevelopment.meteorclient.gui;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WItem;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WLabel;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WSection;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WBlockPosEdit;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WDoubleEdit;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WDropdown;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WIntEdit;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.settings.BlockPosSetting;
/*     */ import meteordevelopment.meteorclient.settings.BlockSetting;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorListSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.FontFaceSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.ItemSetting;
/*     */ import meteordevelopment.meteorclient.settings.KeybindSetting;
/*     */ import meteordevelopment.meteorclient.settings.PotionSetting;
/*     */ import meteordevelopment.meteorclient.settings.ProvidedStringSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.settings.StringSetting;
/*     */ import meteordevelopment.meteorclient.settings.Vector3dSetting;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import net.minecraft.class_437;
/*     */ import org.joml.Vector3d;
/*     */ 
/*     */ public class DefaultSettingsWidgetFactory extends SettingsWidgetFactory {
/*  40 */   private static final SettingColor WHITE = new SettingColor();
/*     */   
/*     */   public DefaultSettingsWidgetFactory(GuiTheme theme) {
/*  43 */     super(theme);
/*     */     
/*  45 */     this.factories.put(BoolSetting.class, (table, setting) -> boolW(table, (BoolSetting)setting));
/*  46 */     this.factories.put(IntSetting.class, (table, setting) -> intW(table, (IntSetting)setting));
/*  47 */     this.factories.put(DoubleSetting.class, (table, setting) -> doubleW(table, (DoubleSetting)setting));
/*  48 */     this.factories.put(StringSetting.class, (table, setting) -> stringW(table, (StringSetting)setting));
/*  49 */     this.factories.put(EnumSetting.class, (table, setting) -> enumW(table, (EnumSetting<Enum<?>>)setting));
/*  50 */     this.factories.put(ProvidedStringSetting.class, (table, setting) -> providedStringW(table, (ProvidedStringSetting)setting));
/*  51 */     this.factories.put(GenericSetting.class, (table, setting) -> genericW(table, (GenericSetting)setting));
/*  52 */     this.factories.put(ColorSetting.class, (table, setting) -> colorW(table, (ColorSetting)setting));
/*  53 */     this.factories.put(KeybindSetting.class, (table, setting) -> keybindW(table, (KeybindSetting)setting));
/*  54 */     this.factories.put(BlockSetting.class, (table, setting) -> blockW(table, (BlockSetting)setting));
/*  55 */     this.factories.put(BlockListSetting.class, (table, setting) -> blockListW(table, (BlockListSetting)setting));
/*  56 */     this.factories.put(ItemSetting.class, (table, setting) -> itemW(table, (ItemSetting)setting));
/*  57 */     this.factories.put(ItemListSetting.class, (table, setting) -> itemListW(table, (ItemListSetting)setting));
/*  58 */     this.factories.put(EntityTypeListSetting.class, (table, setting) -> entityTypeListW(table, (EntityTypeListSetting)setting));
/*  59 */     this.factories.put(EnchantmentListSetting.class, (table, setting) -> enchantmentListW(table, (EnchantmentListSetting)setting));
/*  60 */     this.factories.put(ModuleListSetting.class, (table, setting) -> moduleListW(table, (ModuleListSetting)setting));
/*  61 */     this.factories.put(PacketListSetting.class, (table, setting) -> packetListW(table, (PacketListSetting)setting));
/*  62 */     this.factories.put(ParticleTypeListSetting.class, (table, setting) -> particleTypeListW(table, (ParticleTypeListSetting)setting));
/*  63 */     this.factories.put(SoundEventListSetting.class, (table, setting) -> soundEventListW(table, (SoundEventListSetting)setting));
/*  64 */     this.factories.put(StatusEffectAmplifierMapSetting.class, (table, setting) -> statusEffectAmplifierMapW(table, (StatusEffectAmplifierMapSetting)setting));
/*  65 */     this.factories.put(StatusEffectListSetting.class, (table, setting) -> statusEffectListW(table, (StatusEffectListSetting)setting));
/*  66 */     this.factories.put(StorageBlockListSetting.class, (table, setting) -> storageBlockListW(table, (StorageBlockListSetting)setting));
/*  67 */     this.factories.put(ScreenHandlerListSetting.class, (table, setting) -> screenHandlerListW(table, (ScreenHandlerListSetting)setting));
/*  68 */     this.factories.put(BlockDataSetting.class, (table, setting) -> blockDataW(table, (BlockDataSetting)setting));
/*  69 */     this.factories.put(PotionSetting.class, (table, setting) -> potionW(table, (PotionSetting)setting));
/*  70 */     this.factories.put(StringListSetting.class, (table, setting) -> stringListW(table, (StringListSetting)setting));
/*  71 */     this.factories.put(BlockPosSetting.class, (table, setting) -> blockPosW(table, (BlockPosSetting)setting));
/*  72 */     this.factories.put(ColorListSetting.class, (table, setting) -> colorListW(table, (ColorListSetting)setting));
/*  73 */     this.factories.put(FontFaceSetting.class, (table, setting) -> fontW(table, (FontFaceSetting)setting));
/*  74 */     this.factories.put(Vector3dSetting.class, (table, setting) -> vector3dW(table, (Vector3dSetting)setting));
/*  75 */     this.factories.put(KeyboardHud.CustomKeyListSetting.class, (table, setting) -> customKeyListW(table, (KeyboardHud.CustomKeyListSetting)setting));
/*     */   }
/*     */ 
/*     */   
/*     */   public WWidget create(GuiTheme theme, Settings settings, String filter) {
/*  80 */     WVerticalList list = theme.verticalList();
/*     */     
/*  82 */     List<RemoveInfo> removeInfoList = new ArrayList<>();
/*     */ 
/*     */     
/*  85 */     for (SettingGroup group : settings.groups) {
/*  86 */       group(list, group, filter, removeInfoList);
/*     */     }
/*     */ 
/*     */     
/*  90 */     list.calculateSize();
/*  91 */     list.minWidth = list.width;
/*     */ 
/*     */     
/*  94 */     for (RemoveInfo removeInfo : removeInfoList) {
/*  95 */       removeInfo.remove(list);
/*     */     }
/*     */     
/*  98 */     return (WWidget)list;
/*     */   }
/*     */ 
/*     */   
/*     */   protected double settingTitleTopMargin() {
/* 103 */     return 6.0D;
/*     */   }
/*     */   
/*     */   private void group(WVerticalList list, SettingGroup group, String filter, List<RemoveInfo> removeInfoList) {
/* 107 */     WSection section = (WSection)list.add((WWidget)this.theme.section(group.name, group.sectionExpanded)).expandX().widget();
/* 108 */     section.action = (() -> group.sectionExpanded = section.isExpanded());
/*     */     
/* 110 */     WTable table = (WTable)section.add((WWidget)this.theme.table()).expandX().widget();
/*     */     
/* 112 */     RemoveInfo removeInfo = null;
/*     */     
/* 114 */     for (Setting<?> setting : (Iterable<Setting<?>>)group) {
/* 115 */       if (!Strings.CI.contains(setting.title, filter))
/*     */         continue; 
/* 117 */       boolean visible = setting.isVisible();
/* 118 */       setting.lastWasVisible = visible;
/* 119 */       if (!visible) {
/* 120 */         if (removeInfo == null) removeInfo = new RemoveInfo(section, table); 
/* 121 */         removeInfo.markRowForRemoval();
/*     */       } 
/*     */       
/* 124 */       ((WLabel)table.add((WWidget)this.theme.label(setting.title)).top().marginTop(settingTitleTopMargin()).widget()).tooltip = setting.description;
/*     */       
/* 126 */       SettingsWidgetFactory.Factory factory = getFactory(setting.getClass());
/* 127 */       if (factory != null) factory.create(table, setting);
/*     */       
/* 129 */       table.row();
/*     */     } 
/*     */     
/* 132 */     if (removeInfo != null) removeInfoList.add(removeInfo); 
/*     */   }
/*     */   
/*     */   private static class RemoveInfo {
/*     */     private final WSection section;
/*     */     private final WTable table;
/* 138 */     private final IntList rowIds = (IntList)new IntArrayList();
/*     */     
/*     */     public RemoveInfo(WSection section, WTable table) {
/* 141 */       this.section = section;
/* 142 */       this.table = table;
/*     */     }
/*     */     
/*     */     public void markRowForRemoval() {
/* 146 */       this.rowIds.add(this.table.rowI());
/*     */     }
/*     */     
/*     */     public void remove(WVerticalList list) {
/* 150 */       for (int i = 0; i < this.rowIds.size(); i++) {
/* 151 */         this.table.removeRow(this.rowIds.getInt(i) - i);
/*     */       }
/*     */       
/* 154 */       if (this.table.cells.isEmpty()) list.cells.removeIf(cell -> (cell.widget() == this.section));
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void boolW(WTable table, BoolSetting setting) {
/* 161 */     WCheckbox checkbox = (WCheckbox)table.add((WWidget)this.theme.checkbox(((Boolean)setting.get()).booleanValue())).expandCellX().widget();
/* 162 */     checkbox.action = (() -> setting.set(Boolean.valueOf(checkbox.checked)));
/*     */     
/* 164 */     reset((WContainer)table, (Setting<?>)setting, () -> checkbox.checked = ((Boolean)setting.get()).booleanValue());
/*     */   }
/*     */   
/*     */   private void intW(WTable table, IntSetting setting) {
/* 168 */     WIntEdit edit = (WIntEdit)table.add((WWidget)this.theme.intEdit(((Integer)setting.get()).intValue(), setting.min, setting.max, setting.sliderMin, setting.sliderMax, setting.noSlider)).expandX().widget();
/*     */     
/* 170 */     edit.action = (() -> {
/*     */         if (!setting.set(Integer.valueOf(edit.get())))
/*     */           edit.set(((Integer)setting.get()).intValue()); 
/*     */       });
/* 174 */     reset((WContainer)table, (Setting<?>)setting, () -> edit.set(((Integer)setting.get()).intValue()));
/*     */   }
/*     */   
/*     */   private void doubleW(WTable table, DoubleSetting setting) {
/* 178 */     WDoubleEdit edit = this.theme.doubleEdit(((Double)setting.get()).doubleValue(), setting.min, setting.max, setting.sliderMin, setting.sliderMax, setting.decimalPlaces, setting.noSlider);
/* 179 */     table.add((WWidget)edit).expandX();
/*     */     
/* 181 */     Runnable action = () -> {
/*     */         if (!setting.set(Double.valueOf(edit.get())))
/*     */           edit.set(((Double)setting.get()).doubleValue()); 
/*     */       };
/* 185 */     if (setting.onSliderRelease) { edit.actionOnRelease = action; }
/* 186 */     else { edit.action = action; }
/*     */     
/* 188 */     reset((WContainer)table, (Setting<?>)setting, () -> edit.set(((Double)setting.get()).doubleValue()));
/*     */   }
/*     */   
/*     */   private void stringW(WTable table, StringSetting setting) {
/* 192 */     CharFilter filter = (setting.filter == null) ? ((text, c) -> true) : setting.filter;
/* 193 */     Cell<WTextBox> cell = table.add((WWidget)this.theme.textBox((String)setting.get(), setting.placeholder, filter, setting.renderer));
/* 194 */     if (setting.wide) cell.minWidth(Utils.getWindowWidth() - Utils.getWindowWidth() / 4.0D);
/*     */     
/* 196 */     WTextBox textBox = (WTextBox)cell.expandX().widget();
/* 197 */     textBox.action = (() -> setting.set(textBox.get()));
/*     */     
/* 199 */     reset((WContainer)table, (Setting<?>)setting, () -> textBox.set((String)setting.get()));
/*     */   }
/*     */   
/*     */   private void stringListW(WTable table, StringListSetting setting) {
/* 203 */     WTable wtable = (WTable)table.add((WWidget)this.theme.table()).expandX().widget();
/* 204 */     StringListSetting.fillTable(this.theme, wtable, setting);
/*     */   }
/*     */   
/*     */   private <T extends Enum<?>> void enumW(WTable table, EnumSetting<T> setting) {
/* 208 */     WDropdown<T> dropdown = (WDropdown<T>)table.add((WWidget)this.theme.dropdown((Enum)setting.get())).expandCellX().widget();
/* 209 */     dropdown.action = (() -> setting.set(dropdown.get()));
/*     */     
/* 211 */     reset((WContainer)table, (Setting<?>)setting, () -> dropdown.set(setting.get()));
/*     */   }
/*     */   
/*     */   private void providedStringW(WTable table, ProvidedStringSetting setting) {
/* 215 */     WDropdown<String> dropdown = (WDropdown<String>)table.add((WWidget)this.theme.dropdown(setting.supplier.get(), (String)setting.get())).expandCellX().widget();
/* 216 */     dropdown.action = (() -> setting.set(dropdown.get()));
/*     */     
/* 218 */     reset((WContainer)table, (Setting<?>)setting, () -> dropdown.set(setting.get()));
/*     */   }
/*     */   
/*     */   private void genericW(WTable table, GenericSetting<?> setting) {
/* 222 */     WButton edit = (WButton)table.add((WWidget)this.theme.button(GuiRenderer.EDIT)).widget();
/* 223 */     edit.action = (() -> MeteorClient.mc.method_1507(setting.createScreen(this.theme)));
/*     */     
/* 225 */     reset((WContainer)table, (Setting<?>)setting, (Runnable)null);
/*     */   }
/*     */   
/*     */   private void colorW(WTable table, ColorSetting setting) {
/* 229 */     WHorizontalList list = (WHorizontalList)table.add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */     
/* 231 */     WQuad quad = (WQuad)list.add((WWidget)this.theme.quad((Color)setting.get())).widget();
/*     */     
/* 233 */     WButton edit = (WButton)list.add((WWidget)this.theme.button(GuiRenderer.EDIT)).widget();
/* 234 */     edit.action = (() -> MeteorClient.mc.method_1507((class_437)new ColorSettingScreen(this.theme, (Setting)setting)));
/*     */     
/* 236 */     reset((WContainer)table, (Setting<?>)setting, () -> quad.color = (Color)setting.get());
/*     */   }
/*     */   
/*     */   private void keybindW(WTable table, KeybindSetting setting) {
/* 240 */     WHorizontalList list = (WHorizontalList)table.add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */     
/* 242 */     WKeybind keybind = (WKeybind)list.add((WWidget)this.theme.keybind((Keybind)setting.get(), (Keybind)setting.getDefaultValue())).expandX().widget();
/* 243 */     Objects.requireNonNull(setting); keybind.action = setting::onChanged;
/* 244 */     setting.widget = keybind;
/*     */     
/* 246 */     WButton reset = (WButton)list.add((WWidget)this.theme.button(GuiRenderer.RESET)).expandCellX().right().widget();
/* 247 */     Objects.requireNonNull(keybind); reset.action = keybind::resetBind;
/* 248 */     reset.tooltip = "Reset";
/*     */   }
/*     */   
/*     */   private void blockW(WTable table, BlockSetting setting) {
/* 252 */     WHorizontalList list = (WHorizontalList)table.add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */     
/* 254 */     WItem item = (WItem)list.add((WWidget)this.theme.item(((class_2248)setting.get()).method_8389().method_7854())).widget();
/*     */     
/* 256 */     WButton select = (WButton)list.add((WWidget)this.theme.button("Select")).widget();
/* 257 */     select.action = (() -> {
/*     */         BlockSettingScreen screen = new BlockSettingScreen(this.theme, setting);
/*     */         
/*     */         screen.onClosed(());
/*     */         
/*     */         MeteorClient.mc.method_1507((class_437)screen);
/*     */       });
/* 264 */     reset((WContainer)table, (Setting<?>)setting, () -> item.set(((class_2248)setting.get()).method_8389().method_7854()));
/*     */   }
/*     */   
/*     */   private void blockPosW(WTable table, BlockPosSetting setting) {
/* 268 */     WBlockPosEdit edit = (WBlockPosEdit)table.add((WWidget)this.theme.blockPosEdit((class_2338)setting.get())).expandX().widget();
/*     */     
/* 270 */     edit.actionOnRelease = (() -> {
/*     */         if (!setting.set(edit.get()))
/*     */           edit.set((class_2338)setting.get()); 
/*     */       });
/* 274 */     reset((WContainer)table, (Setting<?>)setting, () -> edit.set((class_2338)setting.get()));
/*     */   }
/*     */   
/*     */   private void blockListW(WTable table, BlockListSetting setting) {
/* 278 */     selectW((WContainer)table, (Setting<?>)setting, () -> MeteorClient.mc.method_1507((class_437)new BlockListSettingScreen(this.theme, setting)));
/*     */   }
/*     */   
/*     */   private void itemW(WTable table, ItemSetting setting) {
/* 282 */     WHorizontalList list = (WHorizontalList)table.add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */     
/* 284 */     WItem item = (WItem)list.add((WWidget)this.theme.item(((class_1792)setting.get()).method_8389().method_7854())).widget();
/*     */     
/* 286 */     WButton select = (WButton)list.add((WWidget)this.theme.button("Select")).widget();
/* 287 */     select.action = (() -> {
/*     */         ItemSettingScreen screen = new ItemSettingScreen(this.theme, setting);
/*     */         
/*     */         screen.onClosed(());
/*     */         
/*     */         MeteorClient.mc.method_1507((class_437)screen);
/*     */       });
/* 294 */     reset((WContainer)table, (Setting<?>)setting, () -> item.set(((class_1792)setting.get()).method_7854()));
/*     */   }
/*     */   
/*     */   private void itemListW(WTable table, ItemListSetting setting) {
/* 298 */     selectW((WContainer)table, (Setting<?>)setting, () -> MeteorClient.mc.method_1507((class_437)new ItemListSettingScreen(this.theme, setting)));
/*     */   }
/*     */   
/*     */   private void entityTypeListW(WTable table, EntityTypeListSetting setting) {
/* 302 */     selectW((WContainer)table, (Setting<?>)setting, () -> MeteorClient.mc.method_1507((class_437)new EntityTypeListSettingScreen(this.theme, setting)));
/*     */   }
/*     */   
/*     */   private void enchantmentListW(WTable table, EnchantmentListSetting setting) {
/* 306 */     selectW((WContainer)table, (Setting<?>)setting, () -> MeteorClient.mc.method_1507((class_437)new EnchantmentListSettingScreen(this.theme, (Setting)setting)));
/*     */   }
/*     */   
/*     */   private void moduleListW(WTable table, ModuleListSetting setting) {
/* 310 */     selectW((WContainer)table, (Setting<?>)setting, () -> MeteorClient.mc.method_1507((class_437)new ModuleListSettingScreen(this.theme, (Setting)setting)));
/*     */   }
/*     */   
/*     */   private void packetListW(WTable table, PacketListSetting setting) {
/* 314 */     selectW((WContainer)table, (Setting<?>)setting, () -> MeteorClient.mc.method_1507((class_437)new PacketBoolSettingScreen(this.theme, (Setting)setting)));
/*     */   }
/*     */   
/*     */   private void particleTypeListW(WTable table, ParticleTypeListSetting setting) {
/* 318 */     selectW((WContainer)table, (Setting<?>)setting, () -> MeteorClient.mc.method_1507((class_437)new ParticleTypeListSettingScreen(this.theme, (Setting)setting)));
/*     */   }
/*     */   
/*     */   private void soundEventListW(WTable table, SoundEventListSetting setting) {
/* 322 */     selectW((WContainer)table, (Setting<?>)setting, () -> MeteorClient.mc.method_1507((class_437)new SoundEventListSettingScreen(this.theme, (Setting)setting)));
/*     */   }
/*     */   
/*     */   private void statusEffectAmplifierMapW(WTable table, StatusEffectAmplifierMapSetting setting) {
/* 326 */     selectW((WContainer)table, (Setting<?>)setting, () -> MeteorClient.mc.method_1507((class_437)new StatusEffectAmplifierMapSettingScreen(this.theme, (Setting)setting)));
/*     */   }
/*     */   
/*     */   private void statusEffectListW(WTable table, StatusEffectListSetting setting) {
/* 330 */     selectW((WContainer)table, (Setting<?>)setting, () -> MeteorClient.mc.method_1507((class_437)new StatusEffectListSettingScreen(this.theme, (Setting)setting)));
/*     */   }
/*     */   
/*     */   private void storageBlockListW(WTable table, StorageBlockListSetting setting) {
/* 334 */     selectW((WContainer)table, (Setting<?>)setting, () -> MeteorClient.mc.method_1507((class_437)new StorageBlockListSettingScreen(this.theme, (Setting)setting)));
/*     */   }
/*     */   
/*     */   private void screenHandlerListW(WTable table, ScreenHandlerListSetting setting) {
/* 338 */     selectW((WContainer)table, (Setting<?>)setting, () -> MeteorClient.mc.method_1507((class_437)new ScreenHandlerSettingScreen(this.theme, (Setting)setting)));
/*     */   }
/*     */   
/*     */   private void blockDataW(WTable table, BlockDataSetting<?> setting) {
/* 342 */     WButton button = (WButton)table.add((WWidget)this.theme.button(GuiRenderer.EDIT)).expandCellX().widget();
/* 343 */     button.action = (() -> MeteorClient.mc.method_1507((class_437)new BlockDataSettingScreen(this.theme, setting)));
/*     */     
/* 345 */     reset((WContainer)table, (Setting<?>)setting, (Runnable)null);
/*     */   }
/*     */   
/*     */   private void potionW(WTable table, PotionSetting setting) {
/* 349 */     WHorizontalList list = (WHorizontalList)table.add((WWidget)this.theme.horizontalList()).expandX().widget();
/* 350 */     WItemWithLabel item = (WItemWithLabel)list.add((WWidget)this.theme.itemWithLabel(((MyPotion)setting.get()).potion, class_1074.method_4662(((MyPotion)setting.get()).potion.method_7909().method_7876(), new Object[0]))).widget();
/*     */     
/* 352 */     WButton button = (WButton)list.add((WWidget)this.theme.button("Select")).expandCellX().widget();
/* 353 */     button.action = (() -> {
/*     */         PotionSettingScreen potionSettingScreen = new PotionSettingScreen(this.theme, setting);
/*     */         
/*     */         potionSettingScreen.onClosed(());
/*     */         
/*     */         MeteorClient.mc.method_1507((class_437)potionSettingScreen);
/*     */       });
/* 360 */     reset((WContainer)list, (Setting<?>)setting, () -> item.set(((MyPotion)setting.get()).potion));
/*     */   }
/*     */   
/*     */   private void fontW(WTable table, FontFaceSetting setting) {
/* 364 */     WHorizontalList list = (WHorizontalList)table.add((WWidget)this.theme.horizontalList()).expandX().widget();
/* 365 */     WLabel label = (WLabel)list.add((WWidget)this.theme.label(((FontFace)setting.get()).info.family())).widget();
/*     */     
/* 367 */     WButton button = (WButton)list.add((WWidget)this.theme.button("Select")).expandCellX().widget();
/* 368 */     button.action = (() -> {
/*     */         FontFaceSettingScreen fontFaceSettingScreen = new FontFaceSettingScreen(this.theme, setting);
/*     */         
/*     */         fontFaceSettingScreen.onClosed(());
/*     */         
/*     */         MeteorClient.mc.method_1507((class_437)fontFaceSettingScreen);
/*     */       });
/* 375 */     reset((WContainer)list, (Setting<?>)setting, () -> label.set(Fonts.DEFAULT_FONT.info.family()));
/*     */   }
/*     */   
/*     */   private void colorListW(WTable table, ColorListSetting setting) {
/* 379 */     WTable tab = (WTable)table.add((WWidget)this.theme.table()).expandX().widget();
/* 380 */     WTable t = (WTable)tab.add((WWidget)this.theme.table()).expandX().widget();
/* 381 */     tab.row();
/*     */     
/* 383 */     colorListWFill(t, setting);
/*     */     
/* 385 */     WPlus add = (WPlus)tab.add((WWidget)this.theme.plus()).expandCellX().widget();
/* 386 */     add.action = (() -> {
/*     */         ((List<SettingColor>)setting.get()).add(new SettingColor());
/*     */         
/*     */         setting.onChanged();
/*     */         
/*     */         t.clear();
/*     */         colorListWFill(t, setting);
/*     */       });
/* 394 */     reset((WContainer)tab, (Setting<?>)setting, () -> {
/*     */           t.clear();
/*     */           colorListWFill(t, setting);
/*     */         });
/*     */   }
/*     */   
/*     */   private void colorListWFill(WTable t, ColorListSetting setting) {
/* 401 */     int i = 0;
/* 402 */     for (Iterator<SettingColor> iterator = ((List)setting.get()).iterator(); iterator.hasNext(); ) { SettingColor color = iterator.next();
/* 403 */       int _i = i;
/*     */       
/* 405 */       t.add((WWidget)this.theme.label("" + i + ":"));
/*     */       
/* 407 */       t.add((WWidget)this.theme.quad((Color)color)).widget();
/*     */       
/* 409 */       WButton edit = (WButton)t.add((WWidget)this.theme.button(GuiRenderer.EDIT)).widget();
/* 410 */       edit.action = (() -> {
/*     */           SettingColor defaultValue = WHITE;
/*     */           
/*     */           if (_i < ((List)setting.getDefaultValue()).size()) {
/*     */             defaultValue = ((List<SettingColor>)setting.getDefaultValue()).get(_i);
/*     */           }
/*     */           
/*     */           ColorSetting set = new ColorSetting(setting.name, setting.description, defaultValue, (), null, null);
/*     */           
/*     */           set.set(((List<SettingColor>)setting.get()).get(_i));
/*     */           MeteorClient.mc.method_1507((class_437)new ColorSettingScreen(this.theme, (Setting)set));
/*     */         });
/* 422 */       WMinus remove = (WMinus)t.add((WWidget)this.theme.minus()).expandCellX().right().widget();
/* 423 */       remove.action = (() -> {
/*     */           ((List)setting.get()).remove(_i);
/*     */           
/*     */           setting.onChanged();
/*     */           
/*     */           t.clear();
/*     */           colorListWFill(t, setting);
/*     */         });
/* 431 */       t.row();
/* 432 */       i++; }
/*     */   
/*     */   }
/*     */   
/*     */   private void vector3dW(WTable table, Vector3dSetting setting) {
/* 437 */     WTable internal = (WTable)table.add((WWidget)this.theme.table()).expandX().widget();
/*     */     
/* 439 */     WDoubleEdit x = addVectorComponent(internal, "X", ((Vector3d)setting.get()).x, val -> ((Vector3d)setting.get()).x = val.doubleValue(), setting);
/* 440 */     WDoubleEdit y = addVectorComponent(internal, "Y", ((Vector3d)setting.get()).y, val -> ((Vector3d)setting.get()).y = val.doubleValue(), setting);
/* 441 */     WDoubleEdit z = addVectorComponent(internal, "Z", ((Vector3d)setting.get()).z, val -> ((Vector3d)setting.get()).z = val.doubleValue(), setting);
/*     */     
/* 443 */     reset((WContainer)table, (Setting<?>)setting, () -> {
/*     */           x.set(((Vector3d)setting.get()).x);
/*     */           y.set(((Vector3d)setting.get()).y);
/*     */           z.set(((Vector3d)setting.get()).z);
/*     */         });
/*     */   }
/*     */   
/*     */   private WDoubleEdit addVectorComponent(WTable table, String label, double value, Consumer<Double> update, Vector3dSetting setting) {
/* 451 */     table.add((WWidget)this.theme.label(label + ": "));
/*     */     
/* 453 */     WDoubleEdit component = (WDoubleEdit)table.add((WWidget)this.theme.doubleEdit(value, setting.min, setting.max, setting.sliderMin, setting.sliderMax, setting.decimalPlaces, setting.noSlider)).expandX().widget();
/* 454 */     if (setting.onSliderRelease) {
/* 455 */       component.actionOnRelease = (() -> {
/*     */           update.accept(Double.valueOf(component.get()));
/*     */           setting.onChanged();
/*     */         });
/*     */     } else {
/* 460 */       component.action = (() -> {
/*     */           update.accept(Double.valueOf(component.get()));
/*     */           
/*     */           setting.onChanged();
/*     */         });
/*     */     } 
/* 466 */     table.row();
/*     */     
/* 468 */     return component;
/*     */   }
/*     */   
/*     */   private void customKeyListW(WTable table, KeyboardHud.CustomKeyListSetting setting) {
/* 472 */     WTable wtable = (WTable)table.add((WWidget)this.theme.table()).expandX().widget();
/* 473 */     KeyboardHud.fillTable(this.theme, wtable, setting);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void selectW(WContainer c, Setting<?> setting, Runnable action) {
/* 479 */     boolean addCount = (WSelectedCountLabel.getSize(setting) != -1);
/*     */     
/* 481 */     WContainer c2 = c;
/* 482 */     if (addCount) {
/* 483 */       c2 = (WContainer)c.add((WWidget)this.theme.horizontalList()).expandCellX().widget();
/* 484 */       ((WHorizontalList)c2).spacing *= 2.0D;
/*     */     } 
/*     */     
/* 487 */     WButton button = (WButton)c2.add((WWidget)this.theme.button("Select")).expandCellX().widget();
/* 488 */     button.action = action;
/*     */     
/* 490 */     if (addCount) c2.add((WWidget)(new WSelectedCountLabel(setting)).color(this.theme.textSecondaryColor()));
/*     */     
/* 492 */     reset(c, setting, (Runnable)null);
/*     */   }
/*     */   
/*     */   private void reset(WContainer c, Setting<?> setting, Runnable action) {
/* 496 */     WButton reset = (WButton)c.add((WWidget)this.theme.button(GuiRenderer.RESET)).widget();
/* 497 */     reset.action = (() -> {
/*     */         setting.reset(); if (action != null)
/*     */           action.run(); 
/*     */       });
/* 501 */     reset.tooltip = "Reset";
/*     */   }
/*     */   
/*     */   private static class WSelectedCountLabel extends WMeteorLabel {
/*     */     private final Setting<?> setting;
/* 506 */     private int lastSize = -1;
/*     */     
/*     */     public WSelectedCountLabel(Setting<?> setting) {
/* 509 */       super("", false);
/*     */       
/* 511 */       this.setting = setting;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 516 */       int size = getSize(this.setting);
/*     */       
/* 518 */       if (size != this.lastSize) {
/* 519 */         set("(" + size + " selected)");
/* 520 */         this.lastSize = size;
/*     */       } 
/*     */       
/* 523 */       super.onRender(renderer, mouseX, mouseY, delta);
/*     */     }
/*     */     
/*     */     public static int getSize(Setting<?> setting) {
/* 527 */       Object object = setting.get(); if (object instanceof Collection) { Collection<?> collection = (Collection)object; return collection.size(); }
/* 528 */        object = setting.get(); if (object instanceof Map) { Map<?, ?> map = (Map<?, ?>)object; return map.size(); }
/*     */       
/* 530 */       return -1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\DefaultSettingsWidgetFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */