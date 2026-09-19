/*     */ package meteordevelopment.meteorclient.systems.hud.elements.keyboard;
/*     */ 
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.mixin.KeyBindingAccessor;
/*     */ import meteordevelopment.meteorclient.utils.misc.Keybind;
/*     */ import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2520;
/*     */ import net.minecraft.class_304;
/*     */ import net.minecraft.class_3675;
/*     */ import org.lwjgl.glfw.GLFW;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Key
/*     */ {
/* 659 */   public String name = "";
/*     */   
/*     */   public class_304 binding;
/*     */   
/*     */   public Keybind keybind;
/*     */   public double x;
/* 665 */   private final KeyboardHud.RollingCps rollingCps = new KeyboardHud.RollingCps(); public double y; public double width; public double height; public boolean showCps = false;
/*     */   private boolean isPressed;
/*     */   private float delta;
/*     */   
/*     */   public Key() {
/* 670 */     this.keybind = Keybind.fromKey(32);
/* 671 */     this.width = 60.0D;
/* 672 */     this.height = 40.0D;
/*     */   }
/*     */   
/*     */   public Key(class_2487 compound) {
/* 676 */     this.keybind = Keybind.none().fromTag(compound.method_68568("key"));
/* 677 */     this.name = compound.method_68564("name", "");
/* 678 */     this.x = compound.method_68563("x", 0.0D);
/* 679 */     this.y = compound.method_68563("y", 0.0D);
/* 680 */     this.width = compound.method_68563("width", 60.0D);
/* 681 */     this.height = compound.method_68563("height", 60.0D);
/* 682 */     this.showCps = compound.method_68566("showCps", false);
/*     */   }
/*     */   
/*     */   Key(class_304 binding, String name, double x, double y, double width, double height) {
/* 686 */     this.binding = binding;
/* 687 */     this.name = name;
/* 688 */     this.x = x;
/* 689 */     this.y = y;
/* 690 */     this.width = width;
/* 691 */     this.height = height;
/*     */   }
/*     */   
/*     */   Key(Keybind keybind, String name, double x, double y, double width, double height) {
/* 695 */     this.keybind = keybind;
/* 696 */     this.name = name;
/* 697 */     this.x = x;
/* 698 */     this.y = y;
/* 699 */     this.width = width;
/* 700 */     this.height = height;
/*     */   }
/*     */   
/*     */   public Key setShowCps(boolean show) {
/* 704 */     this.showCps = show;
/* 705 */     return this;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 709 */     if (this.name != null && !this.name.isEmpty()) return this.name; 
/* 710 */     if (this.keybind != null) return KeyboardHud.getShortName(this.keybind.toString()); 
/* 711 */     if (this.binding != null) return KeyboardHud.getShortName(this.binding.method_16007().getString()); 
/* 712 */     return "?";
/*     */   }
/*     */   
/*     */   public boolean matches(int input, int scancode, boolean key) {
/* 716 */     if (this.keybind != null) {
/* 717 */       return (this.keybind.isKey() == key && this.keybind.getValue() == input);
/*     */     }
/* 719 */     class_3675.class_306 inputKey = ((KeyBindingAccessor)this.binding).meteor$getKey();
/* 720 */     boolean isKey = (inputKey.method_1442() != class_3675.class_307.field_1672);
/* 721 */     return (isKey == key && inputKey.method_1442() == class_3675.class_307.field_1671) ? (
/* 722 */       (scancode == inputKey.method_1444())) : (
/* 723 */       (input == inputKey.method_1444()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(KeyAction action) {
/* 728 */     if (action != KeyAction.Release) {
/* 729 */       this.isPressed = true;
/* 730 */       if (this.showCps && action == KeyAction.Press) {
/* 731 */         this.rollingCps.add();
/*     */       }
/*     */     } else {
/* 734 */       this.isPressed = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isNativelyPressed() {
/* 739 */     long window = MeteorClient.mc.method_22683().method_4490();
/* 740 */     if (this.keybind != null) {
/* 741 */       if (!this.keybind.isSet()) return false; 
/* 742 */       return this.keybind.isKey() ? (
/* 743 */         (GLFW.glfwGetKey(window, this.keybind.getValue()) != 0)) : (
/* 744 */         (GLFW.glfwGetMouseButton(window, this.keybind.getValue()) != 0));
/*     */     } 
/* 746 */     int key = ((KeyBindingAccessor)this.binding).meteor$getKey().method_1444();
/* 747 */     return (key >= 0 && key < 8) ? (
/* 748 */       (GLFW.glfwGetMouseButton(window, key) != 0)) : (
/* 749 */       (GLFW.glfwGetKey(window, key) != 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCps() {
/* 754 */     return this.rollingCps.get();
/*     */   }
/*     */   
/*     */   public class_2487 serialize() {
/* 758 */     class_2487 compound = new class_2487();
/* 759 */     compound.method_10566("key", (class_2520)this.keybind.toTag());
/* 760 */     compound.method_10582("name", this.name);
/* 761 */     compound.method_10549("x", this.x);
/* 762 */     compound.method_10549("y", this.y);
/* 763 */     compound.method_10549("width", this.width);
/* 764 */     compound.method_10549("height", this.height);
/* 765 */     compound.method_10556("showCps", this.showCps);
/* 766 */     return compound;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\hud\elements\keyboard\KeyboardHud$Key.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */