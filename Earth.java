// Earth: Vf = Vi + at
public class Earth extends KinematicEquation {
   public double solveVelocityInitial() {
      return getVelocityFinal() - (getAcceleration() * getTime());
   }
   
   public double solveVelocityFinal() {
      return getVelocityInitial() + (getAcceleration() * getTime()); 
   }
   
   public double solveTime() {
      return (getVelocityFinal() - getVelocityInitial()) / getAcceleration();
   }
   
   public double solveAcceleration() {
      return (getVelocityFinal() - getVelocityInitial()) / getTime();
   }
   
   public double solveDisplacement() {
      throw new IllegalArgumentException("ERROR: Equation does not include displacement");
   }
}