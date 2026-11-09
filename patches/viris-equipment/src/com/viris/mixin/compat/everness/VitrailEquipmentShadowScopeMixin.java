package com.viris.mixin.compat.everness;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.viris.Viris;
import com.viris.compat.everness.EquipmentShadowScope;
import dev.vitrail.render.PackChain;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "dev.vitrail.render.ShadowGeometry", remap = false)
public abstract class VitrailEquipmentShadowScopeMixin {
    @WrapMethod(method = "draw", remap = false)
    private static void everness$casters(Vec3 camera, Operation<Void> original) {
        boolean active = Viris.isVulkan() && PackChain.drawingPack();
        if (active) EquipmentShadowScope.enter();
        try { original.call(camera); }
        finally { if (active) EquipmentShadowScope.exit(); }
    }
}
