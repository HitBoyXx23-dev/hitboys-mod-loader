/*     */ package meteordevelopment.meteorclient.utils.network;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.Comparator;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_8037;
/*     */ import net.minecraft.class_8038;
/*     */ import org.reflections.Reflections;
/*     */ import org.reflections.scanners.Scanner;
/*     */ import org.reflections.scanners.Scanners;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketUtilsUtil
/*     */ {
/*     */   public static void main(String[] args) {
/*     */     try {
/*  30 */       init();
/*  31 */     } catch (IOException e) {
/*  32 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() throws IOException {
/*  38 */     File file = new File("src/main/java/%s/PacketUtils.java".formatted(new Object[] { PacketUtilsUtil.class.getPackageName().replace('.', '/') }));
/*  39 */     if (!file.exists()) {
/*  40 */       file.getParentFile().mkdirs();
/*  41 */       file.createNewFile();
/*     */     } 
/*     */     
/*  44 */     BufferedWriter writer = new BufferedWriter(new FileWriter(file)); try {
/*  45 */       writer.write("/*\n");
/*  46 */       writer.write(" * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).\n");
/*  47 */       writer.write(" * Copyright (c) Meteor Development.\n");
/*  48 */       writer.write(" */\n\n");
/*     */       
/*  50 */       writer.write("package meteordevelopment.meteorclient.utils.network;\n\n");
/*     */ 
/*     */       
/*  53 */       writer.write("import com.google.common.collect.Sets;\n");
/*  54 */       writer.write("import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;\n");
/*  55 */       writer.write("import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;\n");
/*  56 */       writer.write("import net.minecraft.network.packet.Packet;\n\n");
/*     */       
/*  58 */       writer.write("import java.util.Map;\n");
/*  59 */       writer.write("import java.util.Set;\n");
/*     */ 
/*     */       
/*  62 */       writer.write("\npublic class PacketUtils {\n");
/*     */ 
/*     */       
/*  65 */       writer.write("    private static final Map<Class<? extends Packet<?>>, String> S2C_PACKETS = new Reference2ObjectOpenHashMap<>();\n");
/*  66 */       writer.write("    private static final Map<Class<? extends Packet<?>>, String> C2S_PACKETS = new Reference2ObjectOpenHashMap<>();\n\n");
/*  67 */       writer.write("    private static final Map<String, Class<? extends Packet<?>>> S2C_PACKETS_R = new Object2ReferenceOpenHashMap<>();\n");
/*  68 */       writer.write("    private static final Map<String, Class<? extends Packet<?>>> C2S_PACKETS_R = new Object2ReferenceOpenHashMap<>();\n\n");
/*  69 */       writer.write("    public static final Set<Class<? extends Packet<?>>> PACKETS = Sets.union(getC2SPackets(), getS2CPackets());\n\n");
/*     */ 
/*     */       
/*  72 */       writer.write("    static {\n");
/*     */ 
/*     */       
/*  75 */       processPackets(writer, "net.minecraft.network.packet.c2s", "C2S_PACKETS", "C2S_PACKETS_R", packet -> false);
/*     */ 
/*     */       
/*  78 */       writer.newLine();
/*  79 */       processPackets(writer, "net.minecraft.network.packet.s2c", "S2C_PACKETS", "S2C_PACKETS_R", packet -> 
/*  80 */           (class_8038.class.isAssignableFrom(packet) || class_8037.class.isAssignableFrom(packet)));
/*     */ 
/*     */       
/*  83 */       writer.write("    }\n\n");
/*     */       
/*  85 */       writer.write("    private PacketUtils() {\n");
/*  86 */       writer.write("    }\n\n");
/*     */ 
/*     */       
/*  89 */       writer.write("    public static String getName(Class<? extends Packet<?>> packetClass) {\n");
/*  90 */       writer.write("        String name = S2C_PACKETS.get(packetClass);\n");
/*  91 */       writer.write("        if (name != null) return name;\n");
/*  92 */       writer.write("        return C2S_PACKETS.get(packetClass);\n");
/*  93 */       writer.write("    }\n\n");
/*     */ 
/*     */       
/*  96 */       writer.write("    public static Class<? extends Packet<?>> getPacket(String name) {\n");
/*  97 */       writer.write("        Class<? extends Packet<?>> packet = S2C_PACKETS_R.get(name);\n");
/*  98 */       writer.write("        if (packet != null) return packet;\n");
/*  99 */       writer.write("        return C2S_PACKETS_R.get(name);\n");
/* 100 */       writer.write("    }\n\n");
/*     */ 
/*     */       
/* 103 */       writer.write("    public static Set<Class<? extends Packet<?>>> getS2CPackets() {\n");
/* 104 */       writer.write("        return S2C_PACKETS.keySet();\n");
/* 105 */       writer.write("    }\n\n");
/*     */ 
/*     */       
/* 108 */       writer.write("    public static Set<Class<? extends Packet<?>>> getC2SPackets() {\n");
/* 109 */       writer.write("        return C2S_PACKETS.keySet();\n");
/* 110 */       writer.write("    }\n");
/*     */ 
/*     */       
/* 113 */       writer.write("}\n");
/* 114 */       writer.close();
/*     */     } catch (Throwable throwable) {
/*     */       try {
/*     */         writer.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       }  throw throwable;
/* 121 */     }  } private static void processPackets(BufferedWriter writer, String packageName, String packetMapName, String reverseMapName, Predicate<Class<?>> exclusionFilter) throws IOException { Comparator<Class<?>> packetsComparator = Comparator.comparing(cls -> cls.getName().substring(cls.getName().lastIndexOf('.') + 1)).thenComparing(Class::getName);
/*     */     
/* 123 */     Reflections reflections = new Reflections(packageName, new Scanner[] { (Scanner)Scanners.SubTypes });
/* 124 */     Set<Class<? extends class_2596>> packets = reflections.getSubTypesOf(class_2596.class);
/* 125 */     SortedSet<Class<? extends class_2596>> sortedPackets = (SortedSet)new TreeSet<>(packetsComparator);
/* 126 */     sortedPackets.addAll(packets);
/*     */     
/* 128 */     for (Class<? extends class_2596> packet : sortedPackets) {
/* 129 */       if (exclusionFilter.test(packet))
/*     */         continue; 
/* 131 */       String name = packet.getName();
/* 132 */       String className = name.substring(name.lastIndexOf('.') + 1).replace('$', '.');
/* 133 */       String fullName = name.replace('$', '.');
/*     */       
/* 135 */       writer.write("        %s.put(%s.class, \"%s\");%n".formatted(new Object[] { packetMapName, fullName, className }));
/* 136 */       writer.write("        %s.put(\"%s\", %s.class);%n".formatted(new Object[] { reverseMapName, className, fullName }));
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\network\PacketUtilsUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */