/*     */ package meteordevelopment.meteorclient.systems.modules.render.blockesp;
/*     */ 
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import net.minecraft.class_1922;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_259;
/*     */ import net.minecraft.class_265;
/*     */ import net.minecraft.class_2680;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ESPBlock
/*     */ {
/*  21 */   private static final class_2338.class_2339 blockPos = new class_2338.class_2339();
/*     */   
/*  23 */   private static final BlockESP blockEsp = (BlockESP)Modules.get().get(BlockESP.class);
/*     */   
/*     */   public static final int FO = 2;
/*     */   
/*     */   public static final int FO_RI = 4;
/*     */   
/*     */   public static final int RI = 8;
/*     */   public static final int BA_RI = 16;
/*     */   public static final int BA = 32;
/*     */   public static final int BA_LE = 64;
/*     */   public static final int LE = 128;
/*     */   public static final int FO_LE = 256;
/*     */   public static final int TO = 512;
/*     */   public static final int TO_FO = 1024;
/*     */   public static final int TO_BA = 2048;
/*     */   public static final int TO_RI = 4096;
/*     */   public static final int TO_LE = 8192;
/*     */   public static final int BO = 16384;
/*     */   public static final int BO_FO = 32768;
/*     */   public static final int BO_BA = 65536;
/*     */   public static final int BO_RI = 131072;
/*     */   public static final int BO_LE = 262144;
/*  45 */   public static final int[] SIDES = new int[] { 2, 32, 128, 8, 512, 16384 };
/*     */   
/*     */   public final int x;
/*     */   public final int y;
/*     */   public final int z;
/*     */   private class_2680 state;
/*     */   public int neighbours;
/*     */   public ESPGroup group;
/*     */   public boolean loaded = true;
/*     */   
/*     */   public ESPBlock(int x, int y, int z) {
/*  56 */     this.x = x;
/*  57 */     this.y = y;
/*  58 */     this.z = z;
/*     */   }
/*     */   
/*     */   public ESPBlock getSideBlock(int side) {
/*  62 */     switch (side) { case 2: case 32: case 128: case 8: case 512: case 16384:  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  69 */       null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void assignGroup() {
/*  74 */     ESPGroup firstGroup = null;
/*     */     
/*  76 */     for (int side : SIDES) {
/*  77 */       if ((this.neighbours & side) == side) {
/*     */         
/*  79 */         ESPBlock neighbour = getSideBlock(side);
/*  80 */         if (neighbour != null && neighbour.group != null)
/*     */         {
/*  82 */           if (firstGroup == null)
/*  83 */           { firstGroup = neighbour.group;
/*     */              }
/*     */           
/*  86 */           else if (firstGroup != neighbour.group) { firstGroup.merge(neighbour.group); }
/*     */            } 
/*     */       } 
/*     */     } 
/*  90 */     if (firstGroup == null) {
/*  91 */       firstGroup = blockEsp.newGroup(this.state.method_26204());
/*     */     }
/*     */     
/*  94 */     firstGroup.add(this);
/*     */   }
/*     */   
/*     */   public void update() {
/*  98 */     this.state = MeteorClient.mc.field_1687.method_8320((class_2338)blockPos.method_10103(this.x, this.y, this.z));
/*  99 */     this.neighbours = 0;
/*     */     
/* 101 */     if (isNeighbour(class_2350.field_11035)) this.neighbours |= 0x2; 
/* 102 */     if (isNeighbourDiagonal(1.0D, 0.0D, 1.0D)) this.neighbours |= 0x4; 
/* 103 */     if (isNeighbour(class_2350.field_11034)) this.neighbours |= 0x8; 
/* 104 */     if (isNeighbourDiagonal(1.0D, 0.0D, -1.0D)) this.neighbours |= 0x10; 
/* 105 */     if (isNeighbour(class_2350.field_11043)) this.neighbours |= 0x20; 
/* 106 */     if (isNeighbourDiagonal(-1.0D, 0.0D, -1.0D)) this.neighbours |= 0x40; 
/* 107 */     if (isNeighbour(class_2350.field_11039)) this.neighbours |= 0x80; 
/* 108 */     if (isNeighbourDiagonal(-1.0D, 0.0D, 1.0D)) this.neighbours |= 0x100;
/*     */     
/* 110 */     if (isNeighbour(class_2350.field_11036)) this.neighbours |= 0x200; 
/* 111 */     if (isNeighbourDiagonal(0.0D, 1.0D, 1.0D)) this.neighbours |= 0x400; 
/* 112 */     if (isNeighbourDiagonal(0.0D, 1.0D, -1.0D)) this.neighbours |= 0x800; 
/* 113 */     if (isNeighbourDiagonal(1.0D, 1.0D, 0.0D)) this.neighbours |= 0x1000; 
/* 114 */     if (isNeighbourDiagonal(-1.0D, 1.0D, 0.0D)) this.neighbours |= 0x2000; 
/* 115 */     if (isNeighbour(class_2350.field_11033)) this.neighbours |= 0x4000; 
/* 116 */     if (isNeighbourDiagonal(0.0D, -1.0D, 1.0D)) this.neighbours |= 0x8000; 
/* 117 */     if (isNeighbourDiagonal(0.0D, -1.0D, -1.0D)) this.neighbours |= 0x10000; 
/* 118 */     if (isNeighbourDiagonal(1.0D, -1.0D, 0.0D)) this.neighbours |= 0x20000; 
/* 119 */     if (isNeighbourDiagonal(-1.0D, -1.0D, 0.0D)) this.neighbours |= 0x40000;
/*     */     
/* 121 */     if (this.group == null) assignGroup(); 
/*     */   }
/*     */   
/*     */   private boolean isNeighbour(class_2350 dir) {
/* 125 */     blockPos.method_10103(this.x + dir.method_10148(), this.y + dir.method_10164(), this.z + dir.method_10165());
/* 126 */     class_2680 neighbourState = MeteorClient.mc.field_1687.method_8320((class_2338)blockPos);
/*     */     
/* 128 */     if (neighbourState.method_26204() != this.state.method_26204()) return false;
/*     */     
/* 130 */     class_265 cube = class_259.method_1077();
/* 131 */     class_265 shape = this.state.method_26218((class_1922)MeteorClient.mc.field_1687, (class_2338)blockPos);
/* 132 */     class_265 neighbourShape = neighbourState.method_26218((class_1922)MeteorClient.mc.field_1687, (class_2338)blockPos);
/*     */     
/* 134 */     if (shape.method_1110()) shape = cube; 
/* 135 */     if (neighbourShape.method_1110()) neighbourShape = cube;
/*     */     
/* 137 */     switch (dir) {
/*     */       case field_11035:
/* 139 */         if (shape.method_1105(class_2350.class_2351.field_11051) == 1.0D && neighbourShape.method_1091(class_2350.class_2351.field_11051) == 0.0D) return true;
/*     */         
/*     */         break;
/*     */       case field_11043:
/* 143 */         if (shape.method_1091(class_2350.class_2351.field_11051) == 0.0D && neighbourShape.method_1105(class_2350.class_2351.field_11051) == 1.0D) return true;
/*     */         
/*     */         break;
/*     */       case field_11034:
/* 147 */         if (shape.method_1105(class_2350.class_2351.field_11048) == 1.0D && neighbourShape.method_1091(class_2350.class_2351.field_11048) == 0.0D) return true;
/*     */         
/*     */         break;
/*     */       case field_11039:
/* 151 */         if (shape.method_1091(class_2350.class_2351.field_11048) == 0.0D && neighbourShape.method_1105(class_2350.class_2351.field_11048) == 1.0D) return true;
/*     */         
/*     */         break;
/*     */       case field_11036:
/* 155 */         if (shape.method_1105(class_2350.class_2351.field_11052) == 1.0D && neighbourShape.method_1091(class_2350.class_2351.field_11052) == 0.0D) return true;
/*     */         
/*     */         break;
/*     */       case field_11033:
/* 159 */         if (shape.method_1091(class_2350.class_2351.field_11052) == 0.0D && neighbourShape.method_1105(class_2350.class_2351.field_11052) == 1.0D) return true;
/*     */         
/*     */         break;
/*     */     } 
/* 163 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isNeighbourDiagonal(double x, double y, double z) {
/* 167 */     blockPos.method_10102(this.x + x, this.y + y, this.z + z);
/* 168 */     return (this.state.method_26204() == MeteorClient.mc.field_1687.method_8320((class_2338)blockPos).method_26204());
/*     */   }
/*     */   
/*     */   public void render(Render3DEvent event) {
/* 172 */     double x1 = this.x;
/* 173 */     double y1 = this.y;
/* 174 */     double z1 = this.z;
/* 175 */     double x2 = (this.x + 1);
/* 176 */     double y2 = (this.y + 1);
/* 177 */     double z2 = (this.z + 1);
/*     */     
/* 179 */     class_265 shape = this.state.method_26218((class_1922)MeteorClient.mc.field_1687, (class_2338)blockPos);
/*     */     
/* 181 */     if (!shape.method_1110()) {
/* 182 */       x1 = this.x + shape.method_1091(class_2350.class_2351.field_11048);
/* 183 */       y1 = this.y + shape.method_1091(class_2350.class_2351.field_11052);
/* 184 */       z1 = this.z + shape.method_1091(class_2350.class_2351.field_11051);
/* 185 */       x2 = this.x + shape.method_1105(class_2350.class_2351.field_11048);
/* 186 */       y2 = this.y + shape.method_1105(class_2350.class_2351.field_11052);
/* 187 */       z2 = this.z + shape.method_1105(class_2350.class_2351.field_11051);
/*     */     } 
/*     */     
/* 190 */     ESPBlockData blockData = blockEsp.getBlockData(this.state.method_26204());
/*     */     
/* 192 */     ShapeMode shapeMode = blockData.shapeMode;
/* 193 */     SettingColor settingColor1 = blockData.lineColor;
/* 194 */     SettingColor settingColor2 = blockData.sideColor;
/*     */     
/* 196 */     if (this.neighbours == 0) {
/* 197 */       event.renderer.box(x1, y1, z1, x2, y2, z2, (Color)settingColor2, (Color)settingColor1, shapeMode, 0);
/*     */     }
/*     */     else {
/*     */       
/* 201 */       if (shapeMode.lines()) {
/*     */         
/* 203 */         if (((this.neighbours & 0x80) != 128 && (this.neighbours & 0x20) != 32) || ((this.neighbours & 0x80) == 128 && (this.neighbours & 0x20) == 32 && (this.neighbours & 0x40) != 64)) {
/* 204 */           event.renderer.line(x1, y1, z1, x1, y2, z1, (Color)settingColor1);
/*     */         }
/*     */         
/* 207 */         if (((this.neighbours & 0x80) != 128 && (this.neighbours & 0x2) != 2) || ((this.neighbours & 0x80) == 128 && (this.neighbours & 0x2) == 2 && (this.neighbours & 0x100) != 256)) {
/* 208 */           event.renderer.line(x1, y1, z2, x1, y2, z2, (Color)settingColor1);
/*     */         }
/*     */         
/* 211 */         if (((this.neighbours & 0x8) != 8 && (this.neighbours & 0x20) != 32) || ((this.neighbours & 0x8) == 8 && (this.neighbours & 0x20) == 32 && (this.neighbours & 0x10) != 16)) {
/* 212 */           event.renderer.line(x2, y1, z1, x2, y2, z1, (Color)settingColor1);
/*     */         }
/*     */         
/* 215 */         if (((this.neighbours & 0x8) != 8 && (this.neighbours & 0x2) != 2) || ((this.neighbours & 0x8) == 8 && (this.neighbours & 0x2) == 2 && (this.neighbours & 0x4) != 4)) {
/* 216 */           event.renderer.line(x2, y1, z2, x2, y2, z2, (Color)settingColor1);
/*     */         }
/*     */ 
/*     */         
/* 220 */         if (((this.neighbours & 0x20) != 32 && (this.neighbours & 0x4000) != 16384) || ((this.neighbours & 0x20) != 32 && (this.neighbours & 0x10000) == 65536)) {
/* 221 */           event.renderer.line(x1, y1, z1, x2, y1, z1, (Color)settingColor1);
/*     */         }
/*     */         
/* 224 */         if (((this.neighbours & 0x2) != 2 && (this.neighbours & 0x4000) != 16384) || ((this.neighbours & 0x2) != 2 && (this.neighbours & 0x8000) == 32768)) {
/* 225 */           event.renderer.line(x1, y1, z2, x2, y1, z2, (Color)settingColor1);
/*     */         }
/*     */         
/* 228 */         if (((this.neighbours & 0x20) != 32 && (this.neighbours & 0x200) != 512) || ((this.neighbours & 0x20) != 32 && (this.neighbours & 0x800) == 2048)) {
/* 229 */           event.renderer.line(x1, y2, z1, x2, y2, z1, (Color)settingColor1);
/*     */         }
/*     */         
/* 232 */         if (((this.neighbours & 0x2) != 2 && (this.neighbours & 0x200) != 512) || ((this.neighbours & 0x2) != 2 && (this.neighbours & 0x400) == 1024)) {
/* 233 */           event.renderer.line(x1, y2, z2, x2, y2, z2, (Color)settingColor1);
/*     */         }
/*     */ 
/*     */         
/* 237 */         if (((this.neighbours & 0x80) != 128 && (this.neighbours & 0x4000) != 16384) || ((this.neighbours & 0x80) != 128 && (this.neighbours & 0x40000) == 262144)) {
/* 238 */           event.renderer.line(x1, y1, z1, x1, y1, z2, (Color)settingColor1);
/*     */         }
/*     */         
/* 241 */         if (((this.neighbours & 0x8) != 8 && (this.neighbours & 0x4000) != 16384) || ((this.neighbours & 0x8) != 8 && (this.neighbours & 0x20000) == 131072)) {
/* 242 */           event.renderer.line(x2, y1, z1, x2, y1, z2, (Color)settingColor1);
/*     */         }
/*     */         
/* 245 */         if (((this.neighbours & 0x80) != 128 && (this.neighbours & 0x200) != 512) || ((this.neighbours & 0x80) != 128 && (this.neighbours & 0x2000) == 8192)) {
/* 246 */           event.renderer.line(x1, y2, z1, x1, y2, z2, (Color)settingColor1);
/*     */         }
/*     */         
/* 249 */         if (((this.neighbours & 0x8) != 8 && (this.neighbours & 0x200) != 512) || ((this.neighbours & 0x8) != 8 && (this.neighbours & 0x1000) == 4096)) {
/* 250 */           event.renderer.line(x2, y2, z1, x2, y2, z2, (Color)settingColor1);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 255 */       if (shapeMode.sides()) {
/*     */         
/* 257 */         if ((this.neighbours & 0x4000) != 16384) {
/* 258 */           event.renderer.quadHorizontal(x1, y1, z1, x2, z2, (Color)settingColor2);
/*     */         }
/*     */         
/* 261 */         if ((this.neighbours & 0x200) != 512) {
/* 262 */           event.renderer.quadHorizontal(x1, y2, z1, x2, z2, (Color)settingColor2);
/*     */         }
/*     */         
/* 265 */         if ((this.neighbours & 0x2) != 2) {
/* 266 */           event.renderer.quadVertical(x1, y1, z2, x2, y2, z2, (Color)settingColor2);
/*     */         }
/*     */         
/* 269 */         if ((this.neighbours & 0x20) != 32) {
/* 270 */           event.renderer.quadVertical(x1, y1, z1, x2, y2, z1, (Color)settingColor2);
/*     */         }
/*     */         
/* 273 */         if ((this.neighbours & 0x8) != 8) {
/* 274 */           event.renderer.quadVertical(x2, y1, z1, x2, y2, z2, (Color)settingColor2);
/*     */         }
/*     */         
/* 277 */         if ((this.neighbours & 0x80) != 128) {
/* 278 */           event.renderer.quadVertical(x1, y1, z1, x1, y2, z2, (Color)settingColor2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getKey(int x, int y, int z) {
/* 286 */     return y << 16L | (z & 0xF) << 8L | (x & 0xF);
/*     */   }
/*     */   
/*     */   public static long getKey(class_2338 blockPos) {
/* 290 */     return getKey(blockPos.method_10263(), blockPos.method_10264(), blockPos.method_10260());
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\blockesp\ESPBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */