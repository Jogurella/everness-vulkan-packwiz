package com.viris.compat.everness;

/** Scope of Vitrail's separate caster submission; never the normal equipment view. */
public final class EquipmentShadowScope {
    private static final ThreadLocal<Integer> DEPTH = ThreadLocal.withInitial(() -> 0);
    private static long baseShadows;
    private EquipmentShadowScope() {}
    public static void enter() { DEPTH.set(DEPTH.get() + 1); }
    public static void exit() { int n = DEPTH.get() - 1; if (n <= 0) DEPTH.remove(); else DEPTH.set(n); }
    public static boolean active() { return DEPTH.get() > 0; }
    public static void recordBaseShadow() { if(baseShadows++ == 0) com.viris.Viris.LOGGER.info("VIRIS: enchanted equipment keeps its base texture in the Vitrail shadow submission"); }
    public static long baseShadows() { return baseShadows; }
}
