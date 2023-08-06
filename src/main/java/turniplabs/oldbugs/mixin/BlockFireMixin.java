package turniplabs.oldbugs.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFire;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = BlockFire.class, remap = false)
final class BlockFireMixin {
	@ModifyConstant(method = "tickRate", constant = @Constant(intValue = 40))
	private int changeTickRate(int a) {
		return 10;
	}


	@Redirect(method = "tryToCatchBlockOnFire", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/core/world/World;setBlockAndMetadataWithNotify(IIIII)Z"))
	private boolean infiniteSpreading(World world, int x, int y, int z, int id, int meta) {
		world.setBlockAndMetadataWithNotify(x, y, z, Block.fire.id, 0);

		return false;
	}

	@Redirect(method = "tryToCatchBlockOnFire", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/core/block/BlockFire;setBurnResult(Lnet/minecraft/core/world/World;III)V"))
	private void cancelSetBurnResult(BlockFire instance, World world, int x, int y, int z) {

	}
}
