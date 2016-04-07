package trittimo.tankperipherals;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.google.common.collect.ImmutableMap;

import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = TankPeripherals.MODID, version = TankPeripherals.VERSION,
		dependencies = "required-after:ComputerCraft@[1.79,]")
public class TankPeripherals {
	public static final String MODID = "trittimo.tankperipherals";
	public static final String VERSION = "1.0";

	@EventHandler
	public void init(FMLInitializationEvent event) {
		CallMethodHandler methodHandler = new CallMethodHandler(TankMethods.class);
		ComputerCraftAPI.registerPeripheralProvider(new TankHandler(methodHandler));
	}

	private class TankHandler implements IPeripheralProvider {

		private CallMethodHandler methodHandler;

		public TankHandler(CallMethodHandler methodHandler) {
			this.methodHandler = methodHandler;
		}

		@Override
		public IPeripheral getPeripheral(World world, BlockPos pos, EnumFacing side) {
			TileEntity entity = world.getTileEntity(pos);
			if (entity instanceof IFluidHandler) {
				return new TankPeripheral((IFluidHandler) entity, methodHandler);
			}
			return null;
		}

	}

	private class TankPeripheral implements IPeripheral {
		private Class[] methodSignature = new Class[] { IFluidHandler.class, IComputerAccess.class,
				ILuaContext.class, Object[].class };

		private IFluidHandler tank;
		private CallMethodHandler handler;

		public TankPeripheral(IFluidHandler tank, CallMethodHandler methodHandler) {
			this.tank = tank;
			this.handler = methodHandler;
		}

		@Override
		public String getType() {
			return "Tank";
		}

		@Override
		public String[] getMethodNames() {
			return handler.getMethods();
		}

		@Override
		public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method,
				Object[] arguments) throws LuaException, InterruptedException {
			try {
				Object[] args = new Object[] { tank, computer, context, arguments };
				return handler.callMethod(method, methodSignature, args);
			} catch (RuntimeException e) {
				return new Object[] { e.getMessage() };
			} catch (Exception e) {
				return new Object[] { e.toString() };
			} catch (Throwable e) {
				return new Object[] { e.getMessage() };
			}
		}

		@Override
		public void attach(IComputerAccess computer) {

		}

		@Override
		public void detach(IComputerAccess computer) {

		}

		@Override
		public boolean equals(IPeripheral other) {
			return false;
		}

	}
}
