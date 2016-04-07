package trittimo.tankperipherals;

import java.util.Set;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TankMethods {

	public static Object[] getTankCount(IFluidHandler tank, IComputerAccess computer, ILuaContext context,
			Object[] args) {
		CallMethodHandler.checkArguments(new Object[] { "UP" }, args);
		EnumFacing direction = EnumFacing.byName((String) args[0]);
		return new Object[] { tank.getTankInfo(direction).length };
	}

	public static Object[] canFillWith(IFluidHandler tank, IComputerAccess computer, ILuaContext context,
			Object[] args) {
		CallMethodHandler.checkArguments(new Object[] { "UP", "water" }, args);
		EnumFacing direction = EnumFacing.byName((String) args[0]);
		Fluid fluid = FluidRegistry.getFluid((String) args[1]);
		return new Object[] { tank.canFill(direction, fluid) };
	}

	public static Object[] canDrainFrom(IFluidHandler tank, IComputerAccess computer, ILuaContext context,
			Object[] args) {
		CallMethodHandler.checkArguments(new Object[] { "UP", "water" }, args);
		EnumFacing direction = EnumFacing.byName((String) args[0]);
		Fluid fluid = FluidRegistry.getFluid((String) args[1]);
		return new Object[] { tank.canDrain(direction, fluid) };
	}

	public static Object[] getFluidTypeInTank(IFluidHandler tank, IComputerAccess computer,
			ILuaContext context, Object[] args) {
		CallMethodHandler.checkArguments(new Object[] { "UP", 0 }, args);
		EnumFacing direction = EnumFacing.byName((String) args[0]);
		FluidTankInfo info = tank.getTankInfo(direction)[((Double) args[1]).intValue()];
		try {
			return new Object[] { info.fluid.getFluid().getName() };
		} catch (NullPointerException e) {
			return new Object[] { false };
		}
	}

	public static Object[] getFluidAmount(IFluidHandler tank, IComputerAccess computer, ILuaContext context,
			Object[] args) {
		CallMethodHandler.checkArguments(new Object[] { "UP", 0 }, args);
		EnumFacing direction = EnumFacing.byName((String) args[0]);
		FluidTankInfo info = tank.getTankInfo(direction)[((Double) args[1]).intValue()];
		try {
			return new Object[] { info.fluid.amount };
		} catch (NullPointerException e) {
			return new Object[] { false };
		}
	}

	public static Object[] getCapacity(IFluidHandler tank, IComputerAccess computer, ILuaContext context,
			Object[] args) {
		CallMethodHandler.checkArguments(new Object[] { "UP", new Integer(0) }, args);
		EnumFacing direction = EnumFacing.byName((String) args[0]);
		FluidTankInfo info = tank.getTankInfo(direction)[((Double) args[1]).intValue()];
		try {
			return new Object[] { info.capacity };
		} catch (NullPointerException e) {
			return new Object[] { false };
		}
	}

	public static Object[] fill(IFluidHandler tank, IComputerAccess computer, ILuaContext context,
			Object[] args) {
		return new Object[] {}; // TODO
	}

	public static Object[] drain(IFluidHandler tank, IComputerAccess computer, ILuaContext context,
			Object[] args) {
		return new Object[] {}; // TODO
	}

	public static Object[] getFluidTypes(IFluidHandler tank, IComputerAccess computer, ILuaContext context,
			Object[] args) {
		Set keySet = FluidRegistry.getRegisteredFluids().keySet();
		return keySet.toArray(new Object[keySet.size()]);
	}
}
