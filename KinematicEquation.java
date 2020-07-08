public abstract class KinematicEquation {
   
   private double velocityInitial; 
   private double velocityFinal;
   private double time;
   private double acceleration;
   private double displacement;
   
   // Accessor Methods for all fields
   public double getVelocityInitial() {
      return this.velocityInitial;
   }
   
   public double getVelocityFinal() {
      return this.velocityFinal;
   }
   
   public double getTime() {
      return this.time;
   }
   
   public double getAcceleration() {
      return this.acceleration;
   }
   
   public double getDisplacement() {
      return this.displacement;
   }
   
   // Mutator methods for all fields
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