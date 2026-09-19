/*     */ package meteordevelopment.meteorclient.systems.waypoints;
/*     */ 
/*     */ import meteordevelopment.meteorclient.utils.world.Dimension;
/*     */ import net.minecraft.class_2338;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/* 182 */   private String name = ""; private String icon = "";
/* 183 */   private class_2338 pos = class_2338.field_10980;
/* 184 */   private Dimension dimension = Dimension.Overworld;
/*     */   
/*     */   public Builder name(String name) {
/* 187 */     this.name = name;
/* 188 */     return this;
/*     */   }
/*     */   
/*     */   public Builder icon(String icon) {
/* 192 */     this.icon = icon;
/* 193 */     return this;
/*     */   }
/*     */   
/*     */   public Builder pos(class_2338 pos) {
/* 197 */     this.pos = pos;
/* 198 */     return this;
/*     */   }
/*     */   
/*     */   public Builder dimension(Dimension dimension) {
/* 202 */     this.dimension = dimension;
/* 203 */     return this;
/*     */   }
/*     */   
/*     */   public Waypoint build() {
/* 207 */     Waypoint waypoint = new Waypoint();
/*     */     
/* 209 */     if (!this.name.equals(waypoint.name.getDefaultValue())) waypoint.name.set(this.name); 
/* 210 */     if (!this.icon.equals(waypoint.icon.getDefaultValue())) waypoint.icon.set(this.icon); 
/* 211 */     if (!this.pos.equals(waypoint.pos.getDefaultValue())) waypoint.pos.set(this.pos); 
/* 212 */     if (!this.dimension.equals(waypoint.dimension.getDefaultValue())) waypoint.dimension.set(this.dimension);
/*     */     
/* 214 */     return waypoint;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\waypoints\Waypoint$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */