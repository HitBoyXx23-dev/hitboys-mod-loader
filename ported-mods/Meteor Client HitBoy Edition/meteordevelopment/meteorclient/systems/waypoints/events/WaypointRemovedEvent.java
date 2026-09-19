/*    */ package meteordevelopment.meteorclient.systems.waypoints.events;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.waypoints.Waypoint;
/*    */ 
/*    */ public final class WaypointRemovedEvent
/*    */   extends Record {
/*    */   private final Waypoint waypoint;
/*    */   
/*    */   public WaypointRemovedEvent(Waypoint waypoint) {
/* 10 */     this.waypoint = waypoint; } public Waypoint waypoint() { return this.waypoint; }
/*    */ 
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/systems/waypoints/events/WaypointRemovedEvent;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/waypoints/events/WaypointRemovedEvent;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/systems/waypoints/events/WaypointRemovedEvent;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/waypoints/events/WaypointRemovedEvent;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/systems/waypoints/events/WaypointRemovedEvent;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lmeteordevelopment/meteorclient/systems/waypoints/events/WaypointRemovedEvent;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\waypoints\events\WaypointRemovedEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */