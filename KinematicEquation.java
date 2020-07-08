public abstract class KinematicEquation {
   
   private double velocityInitial; 
   private double velocityFinal;
   private double time;
   private double acceleration;
   private double displacement;
   
   public void setVelocityInitial(double velocityInitial) {
      this.velocityInitial = velocityInitial;
   }
   
   public void setVelocityFinal(double velocityFinal) {
      this.velocityFinal = velocityFinal;
   }
   
   public void setTime(double time) {
      this.time = time;
   }
   
   public void setAcceleration(double acceleration) {
      this.acceleration = acceleration;
   }
   
   public void setDisplacement(double displacement) {
      this.displacement = displacement;
   }
   
   public abstract double solveVelocityInitial();
   public abstract double solveVelocityFinal();
   public abstract double solveTime();
   public abstract double solveAcceleration();
   public abstract double solveDisplacement();
    
}