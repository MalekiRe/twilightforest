package twilightforest.entity.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import twilightforest.entity.RovingCubeEntity;

import java.util.EnumSet;

import net.minecraft.world.entity.ai.goal.Goal.Flag;

/**
 * This is a task that runs when we are near a symbol and have stopped pathfinding, but are not centered on the symbol.
 * <p>
 * The goal of this task is to center on a symbol.
 *
 * @author benma_000
 */
public class CubeCenterOnSymbolGoal extends Goal {
	private final RovingCubeEntity myCube;
	private final double speed;

	private double xPosition;
	private double yPosition;
	private double zPosition;

	public CubeCenterOnSymbolGoal(RovingCubeEntity entityTFRovingCube, double d) {
		this.myCube = entityTFRovingCube;
		this.xPosition = this.myCube.symbolX;
		this.yPosition = this.myCube.symbolY;
		this.zPosition = this.myCube.symbolZ;
		this.speed = d;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		this.xPosition = this.myCube.symbolX;
		this.yPosition = this.myCube.symbolY;
		this.zPosition = this.myCube.symbolZ;


		if (!this.myCube.getNavigation().isDone()) {
			return false;
		} else {
			return isCloseToSymbol();
		}
	}

	@Override
	public boolean canContinueToUse() {
		// inch towards it
		this.myCube.getMoveControl().setWantedPosition(this.xPosition + 0.5F, this.yPosition, this.zPosition + 0.5F, this.speed);
		return this.distanceFromSymbol() > 0.1F && this.isCourseTraversable();
	}

	private boolean isCourseTraversable() {
		return this.distanceFromSymbol() < 100;
	}

	private boolean isCloseToSymbol() {
		double dist = this.distanceFromSymbol();
		return dist > 0.25F && dist < 10F;
	}

	private double distanceFromSymbol() {
		double dx = this.xPosition - this.myCube.getX() + 0.5F;
		double dy = this.yPosition - this.myCube.getY();
		double dz = this.zPosition - this.myCube.getZ() + 0.5F;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

}
