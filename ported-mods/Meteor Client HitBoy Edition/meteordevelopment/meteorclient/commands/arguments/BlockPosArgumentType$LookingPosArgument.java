/*     */ package meteordevelopment.meteorclient.commands.arguments;
/*     */ 
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_2183;
/*     */ import net.minecraft.class_2277;
/*     */ import net.minecraft.class_2278;
/*     */ import net.minecraft.class_241;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LookingPosArgument
/*     */   implements BlockPosArgumentType.PosArgument
/*     */ {
/*     */   private final double x;
/*     */   private final double y;
/*     */   private final double z;
/*     */   
/*     */   public LookingPosArgument(double x, double y, double z) {
/* 253 */     this.x = x;
/* 254 */     this.y = y;
/* 255 */     this.z = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> class_243 getPosition(S source) {
/* 260 */     class_241 vec2 = MeteorClient.mc.field_1724.method_5802();
/* 261 */     class_243 vec3 = class_2183.class_2184.field_9853.method_9302((class_1297)MeteorClient.mc.field_1724);
/* 262 */     float f = class_3532.method_15362(((vec2.field_1342 + 90.0F) * 0.017453292F));
/* 263 */     float g = class_3532.method_15374(((vec2.field_1342 + 90.0F) * 0.017453292F));
/* 264 */     float h = class_3532.method_15362((-vec2.field_1343 * 0.017453292F));
/* 265 */     float i = class_3532.method_15374((-vec2.field_1343 * 0.017453292F));
/* 266 */     float j = class_3532.method_15362(((-vec2.field_1343 + 90.0F) * 0.017453292F));
/* 267 */     float k = class_3532.method_15374(((-vec2.field_1343 + 90.0F) * 0.017453292F));
/* 268 */     class_243 vec32 = new class_243((f * h), i, (g * h));
/* 269 */     class_243 vec33 = new class_243((f * j), k, (g * j));
/* 270 */     class_243 vec34 = vec32.method_1036(vec33).method_1021(-1.0D);
/* 271 */     double d = vec32.field_1352 * this.z + vec33.field_1352 * this.y + vec34.field_1352 * this.x;
/* 272 */     double e = vec32.field_1351 * this.z + vec33.field_1351 * this.y + vec34.field_1351 * this.x;
/* 273 */     double l = vec32.field_1350 * this.z + vec33.field_1350 * this.y + vec34.field_1350 * this.x;
/* 274 */     return new class_243(vec3.field_1352 + d, vec3.field_1351 + e, vec3.field_1350 + l);
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> class_241 getRotation(S source) {
/* 279 */     return class_241.field_1340;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isXRelative() {
/* 284 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isYRelative() {
/* 289 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isZRelative() {
/* 294 */     return true;
/*     */   }
/*     */   
/*     */   public static LookingPosArgument parse(StringReader reader) throws CommandSyntaxException {
/* 298 */     int cursor = reader.getCursor();
/* 299 */     double d = readCoordinate(reader, cursor);
/* 300 */     if (!reader.canRead() || reader.peek() != ' ') {
/* 301 */       reader.setCursor(cursor);
/* 302 */       throw class_2277.field_10755.createWithContext(reader);
/*     */     } 
/* 304 */     reader.skip();
/* 305 */     double e = readCoordinate(reader, cursor);
/* 306 */     if (!reader.canRead() || reader.peek() != ' ') {
/* 307 */       reader.setCursor(cursor);
/* 308 */       throw class_2277.field_10755.createWithContext(reader);
/*     */     } 
/* 310 */     reader.skip();
/* 311 */     double f = readCoordinate(reader, cursor);
/* 312 */     return new LookingPosArgument(d, e, f);
/*     */   }
/*     */   
/*     */   private static double readCoordinate(StringReader reader, int startingCursorPos) throws CommandSyntaxException {
/* 316 */     if (!reader.canRead()) {
/* 317 */       throw class_2278.field_10759.createWithContext(reader);
/*     */     }
/* 319 */     if (reader.peek() != '^') {
/* 320 */       reader.setCursor(startingCursorPos);
/* 321 */       throw class_2277.field_10757.createWithContext(reader);
/*     */     } 
/* 323 */     reader.skip();
/* 324 */     return (reader.canRead() && reader.peek() != ' ') ? reader.readDouble() : 0.0D;
/*     */   }
/*     */   public boolean equals(Object o) {
/*     */     LookingPosArgument lookingPosArgument;
/* 328 */     if (this == o) {
/* 329 */       return true;
/*     */     }
/* 331 */     if (o instanceof LookingPosArgument) { lookingPosArgument = (LookingPosArgument)o; }
/* 332 */     else { return false; }
/*     */     
/* 334 */     return (this.x == lookingPosArgument.x && this.y == lookingPosArgument.y && this.z == lookingPosArgument.z);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 338 */     return Objects.hash(new Object[] { Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z) });
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\arguments\BlockPosArgumentType$LookingPosArgument.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */