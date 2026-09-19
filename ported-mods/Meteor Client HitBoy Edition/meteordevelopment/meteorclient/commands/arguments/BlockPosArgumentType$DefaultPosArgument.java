/*     */ package meteordevelopment.meteorclient.commands.arguments;
/*     */ 
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import net.minecraft.class_2277;
/*     */ import net.minecraft.class_2278;
/*     */ import net.minecraft.class_241;
/*     */ import net.minecraft.class_243;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultPosArgument
/*     */   implements BlockPosArgumentType.PosArgument
/*     */ {
/*     */   private final class_2278 x;
/*     */   private final class_2278 y;
/*     */   private final class_2278 z;
/*     */   
/*     */   public DefaultPosArgument(class_2278 x, class_2278 y, class_2278 z) {
/* 153 */     this.x = x;
/* 154 */     this.y = y;
/* 155 */     this.z = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> class_243 getPosition(S source) {
/* 160 */     class_243 vec3 = MeteorClient.mc.field_1724.method_73189();
/* 161 */     return new class_243(this.x.method_9740(vec3.field_1352), this.y.method_9740(vec3.field_1351), this.z.method_9740(vec3.field_1350));
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> class_241 getRotation(S source) {
/* 166 */     class_241 vec2 = MeteorClient.mc.field_1724.method_5802();
/* 167 */     return new class_241((float)this.x.method_9740(vec2.field_1343), (float)this.y.method_9740(vec2.field_1342));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isXRelative() {
/* 172 */     return this.x.method_9741();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isYRelative() {
/* 177 */     return this.y.method_9741();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isZRelative() {
/* 182 */     return this.z.method_9741();
/*     */   }
/*     */   public boolean equals(Object o) {
/*     */     DefaultPosArgument defaultPosArgument;
/* 186 */     if (this == o) {
/* 187 */       return true;
/*     */     }
/* 189 */     if (o instanceof DefaultPosArgument) { defaultPosArgument = (DefaultPosArgument)o; }
/* 190 */     else { return false; }
/*     */     
/* 192 */     return (this.x.equals(defaultPosArgument.x) && this.y.equals(defaultPosArgument.y) && this.z.equals(defaultPosArgument.z));
/*     */   }
/*     */   
/*     */   public static DefaultPosArgument parse(StringReader reader) throws CommandSyntaxException {
/* 196 */     int cursor = reader.getCursor();
/* 197 */     class_2278 worldCoordinate = class_2278.method_9739(reader);
/* 198 */     if (reader.canRead() && reader.peek() == ' ') {
/* 199 */       reader.skip();
/* 200 */       class_2278 worldCoordinate2 = class_2278.method_9739(reader);
/* 201 */       if (reader.canRead() && reader.peek() == ' ') {
/* 202 */         reader.skip();
/* 203 */         class_2278 worldCoordinate3 = class_2278.method_9739(reader);
/* 204 */         return new DefaultPosArgument(worldCoordinate, worldCoordinate2, worldCoordinate3);
/*     */       } 
/*     */     } 
/* 207 */     reader.setCursor(cursor);
/* 208 */     throw class_2277.field_10755.createWithContext(reader);
/*     */   }
/*     */   
/*     */   public static DefaultPosArgument parse(StringReader reader, boolean centerIntegers) throws CommandSyntaxException {
/* 212 */     int cursor = reader.getCursor();
/* 213 */     class_2278 worldCoordinate = class_2278.method_9743(reader, centerIntegers);
/* 214 */     if (reader.canRead() && reader.peek() == ' ') {
/* 215 */       reader.skip();
/* 216 */       class_2278 worldCoordinate2 = class_2278.method_9743(reader, false);
/* 217 */       if (reader.canRead() && reader.peek() == ' ') {
/* 218 */         reader.skip();
/* 219 */         class_2278 worldCoordinate3 = class_2278.method_9743(reader, centerIntegers);
/* 220 */         return new DefaultPosArgument(worldCoordinate, worldCoordinate2, worldCoordinate3);
/*     */       } 
/*     */     } 
/* 223 */     reader.setCursor(cursor);
/* 224 */     throw class_2277.field_10755.createWithContext(reader);
/*     */   }
/*     */   
/*     */   public static DefaultPosArgument absolute(double x, double y, double z) {
/* 228 */     return new DefaultPosArgument(new class_2278(false, x), new class_2278(false, y), new class_2278(false, z));
/*     */   }
/*     */   
/*     */   public static DefaultPosArgument absolute(class_241 vec) {
/* 232 */     return new DefaultPosArgument(new class_2278(false, vec.field_1343), new class_2278(false, vec.field_1342), new class_2278(true, 0.0D));
/*     */   }
/*     */   
/*     */   public static DefaultPosArgument current() {
/* 236 */     return new DefaultPosArgument(new class_2278(true, 0.0D), new class_2278(true, 0.0D), new class_2278(true, 0.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 241 */     int i = this.x.hashCode();
/* 242 */     i = 31 * i + this.y.hashCode();
/* 243 */     return 31 * i + this.z.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\arguments\BlockPosArgumentType$DefaultPosArgument.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */