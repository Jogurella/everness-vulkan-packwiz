package com.viris.mixin.compat.everness;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.viris.compat.everness.EquipmentShadowScope;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import org.spongepowered.asm.mixin.Mixin;

/** Keep the real texture and alpha, omit the combined glint only in the shadow submission. */
@Mixin(EquipmentLayerRenderer.class)
public abstract class EnchantedEquipmentShadowMixin {
    @WrapMethod(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V")
    private void everness$shadowBase(EquipmentClientInfo.LayerType type, ResourceKey<?> asset, Model<?> model, Object state, ItemStack stack, PoseStack pose, SubmitNodeCollector collector, int light, Identifier texture, int outline, int order, Operation<Void> original) {
        ItemStack drawn = stack;
        if (EquipmentShadowScope.active() && stack.hasFoil()) {
            drawn = stack.copy();
            drawn.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false);
            EquipmentShadowScope.recordBaseShadow();
        }
        original.call(type, asset, model, state, drawn, pose, collector, light, texture, outline, order);
    }
}
