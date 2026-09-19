/*    */ package meteordevelopment.meteorclient.utils.misc;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.DataInputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Base64;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import net.minecraft.class_2487;
/*    */ import net.minecraft.class_2499;
/*    */ import net.minecraft.class_2505;
/*    */ import net.minecraft.class_2507;
/*    */ import net.minecraft.class_2520;
/*    */ 
/*    */ public class NbtUtils {
/*    */   public static <T extends ISerializable<?>> class_2499 listToTag(Iterable<T> list) {
/* 20 */     class_2499 tag = new class_2499();
/* 21 */     for (ISerializable iSerializable : list) tag.add(iSerializable.toTag()); 
/* 22 */     return tag;
/*    */   }
/*    */   
/*    */   public static <T> List<T> listFromTag(class_2499 tag, ToValue<T> toItem) {
/* 26 */     List<T> list = new ArrayList<>(tag.size());
/* 27 */     for (class_2520 itemTag : tag) {
/* 28 */       T value = toItem.toValue(itemTag);
/* 29 */       if (value != null) list.add(value); 
/*    */     } 
/* 31 */     return list;
/*    */   }
/*    */   
/*    */   public static <K, V extends ISerializable<?>> class_2487 mapToTag(Map<K, V> map) {
/* 35 */     class_2487 tag = new class_2487();
/* 36 */     for (K key : map.keySet()) tag.method_10566(key.toString(), (class_2520)((ISerializable)map.get(key)).toTag()); 
/* 37 */     return tag;
/*    */   }
/*    */   
/*    */   public static <K, V> Map<K, V> mapFromTag(class_2487 tag, ToKey<K> toKey, ToValue<V> toValue) {
/* 41 */     Map<K, V> map = new HashMap<>(tag.method_10546());
/* 42 */     for (String key : tag.method_10541()) map.put(toKey.toKey(key), toValue.toValue(tag.method_10580(key))); 
/* 43 */     return map;
/*    */   }
/*    */   
/*    */   public static boolean toClipboard(ISerializable<?> serializable) {
/* 47 */     return toClipboard(serializable.toTag());
/*    */   }
/*    */   
/*    */   public static boolean toClipboard(class_2487 tag) {
/* 51 */     String preClipboard = MeteorClient.mc.field_1774.method_1460();
/*    */     try {
/* 53 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 54 */       class_2507.method_10634(tag, byteArrayOutputStream);
/* 55 */       MeteorClient.mc.field_1774.method_1455(Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()));
/* 56 */       return true;
/* 57 */     } catch (Exception e) {
/* 58 */       MeteorClient.LOG.error("Error copying NBT to clipboard!", e);
/* 59 */       MeteorClient.mc.field_1774.method_1455(preClipboard);
/* 60 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean fromClipboard(ISerializable<?> serializable) {
/* 65 */     class_2487 tag = fromClipboard();
/* 66 */     if (tag == null) return false;
/*    */     
/* 68 */     class_2487 sourceTag = serializable.toTag();
/* 69 */     for (String key : sourceTag.method_10541()) {
/* 70 */       if (!tag.method_10545(key)) return false;
/*    */     
/*    */     } 
/* 73 */     serializable.fromTag(tag);
/* 74 */     return true;
/*    */   }
/*    */   
/*    */   public static class_2487 fromClipboard() {
/*    */     try {
/* 79 */       byte[] data = Base64.getDecoder().decode(MeteorClient.mc.field_1774.method_1460().trim());
/* 80 */       ByteArrayInputStream bis = new ByteArrayInputStream(data);
/* 81 */       return class_2507.method_10629(new DataInputStream(bis), class_2505.method_53898());
/* 82 */     } catch (Exception e) {
/* 83 */       MeteorClient.LOG.error("Invalid NBT data pasted!", e);
/* 84 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static interface ToValue<T> {
/*    */     T toValue(class_2520 param1class_2520);
/*    */   }
/*    */   
/*    */   public static interface ToKey<T> {
/*    */     T toKey(String param1String);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\NbtUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */