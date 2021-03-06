package net.synthrose.artofalchemy.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.synthrose.artofalchemy.blockentity.BlockEntitySynthesizer;
import net.synthrose.artofalchemy.item.AoAItems;

public class BlockSynthesizer extends Block implements BlockEntityProvider {
	
	public static BooleanProperty LIT = Properties.LIT;
	public static final Settings SETTINGS = FabricBlockSettings
			.of(Material.STONE)
			.hardness(5.0f).resistance(6.0f)
			.nonOpaque()
			.build();
	
	public static Identifier getId() {
		return Registry.BLOCK.getId(AoABlocks.SYNTHESIZER);
	}

	public BlockSynthesizer() {
		super(SETTINGS);
		setDefaultState(getDefaultState().with(LIT, false));
	}
	
	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}
	
	@Override
	public int getLuminance(BlockState state) {
		if (state.get(LIT)) { 
			return 15;
		} else {
			return 0;
		}
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx);
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockHitResult hit) {
		
		ItemStack inHand = player.getStackInHand(hand);
		
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null && blockEntity instanceof BlockEntitySynthesizer) {
			if (AoAItems.ESSENTIA_VESSELS.containsValue(inHand.getItem())) {
				ItemUsageContext itemContext = new ItemUsageContext(player, hand, hit);
				ActionResult itemResult = inHand.useOnBlock(itemContext);
				if (itemResult != ActionResult.PASS) {
					return itemResult;
				}
			}
			if (!world.isClient()) {
				ContainerProviderRegistry.INSTANCE.openContainer(getId(), player,
						(packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
			}
			return ActionResult.SUCCESS;
		} else {
			return ActionResult.PASS;
		}
		
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return new BlockEntitySynthesizer();
	}

	@Override
	public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BlockEntitySynthesizer) {
				ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
			}

			super.onBlockRemoved(state, world, pos, newState, moved);
		}
	}

}
