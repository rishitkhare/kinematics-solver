// Water: ΔX = 1/2(Vf + Vi)t
public class Water extends KinematicEquation {
   public double solveVelocityInitial() {
      return (2 * getDisplacement() / getTime()) - getVelocityFinal();
   }
   
   public double solveVelocityFinal() {
      return (2 * getDisplacement() / getTime()) - getVelocityInitial();
   }
   
   public double solveTime() {
      return (2 * getDisplacement()) / (getVelocityFinal() + getVelocityInitial());
   }
   
   public double solveAcceleration() {
      throw new IllegalArgumentException("ERROR: Equation does not include acceleration");
   }
   
   public double solveDisplacement() {
      return 0.5 * (getVelocityFinal() + getVelocityInitial()) * getTime();
   }
}