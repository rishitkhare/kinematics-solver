# kinematics-solver
Solves kinematics equations given enough variables by the user.

# Rough plan
(Hisham, please edit this whenever and wherever necessary)

This program will use user input (System.in) to get three variables out of the five:
 * Velocity initial
 * Velocity final
 * Acceleration
 * Displacement
 * Time

After gettting at least three of the above, it should use the kinematics equations to solve for any of the missing values:
 * Vf = Vi + (a * t)
 * Vf^2 = Vi^2 + (2 * a * x)
 * d = (Vi * t) + (0.5 * a * t^2)
 * d = [ (Vi + Vf) / 2 ] + t
 
 (There is one more kinematic, but it won't be necessary I think)

# Necessary Modules
I've detailed some of the parts of this project that we will need. I'm not sure yet how to structure it optimally in java. There are multiple ways to go about this.

Algebra : ( [possibly static??? I dunno, go ask Mark Brown] method/class)
 * The system will run on a basic algebra system. It won't need to be able to handle anything beyond simple 1-variable equations, maybe quadratics at some point, but for now we can handle those with a special case. This is the system that will carry out the actual calculations of the problem. It will need to be able parse an Equation and solve it.
 
Equation.java : (class)
 * Non-primitive data type for storing all kinds of equations. Constructor will likely take an Array/ArrayList of operands that can be parsed. Might combine this with Algebra.java later, idk. Might even separate into subclasses of SingleVariableEQ.java and MultiVariableEQ.java, we'll see.

EquationSet.java : (INTERFACE)
 * We might use an interface. Hopefully, any specific information pertaining to the kinematic equations is stored only in KinematicVariableSet.java (which will implement this interface). That way, if we want to implement a new set of equations (rotation, electromagnetism, gravity), we will have a strong and generalized base to work from.

KinematicVariableSet.java : (class [implements EquationSet.java])
 * Non-primitive data type for storing the knowns and unknowns of a problem. Should contain information (as class constants of type Equation) about the kinematic equations (listed way above), and be able to convert a specific set of variables into a simple Equation

kinematicToSingleVariableEQ() : (method of KinematicVariableSet.java)
 * Takes a kinematicVariableSet and converts it into a single-variable solvable Equation, which will then be fed into some method of Algebra.java to solve and then return to user.
