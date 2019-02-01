package frc.robot.states;

public class SuperstructureState {
    public double height = SuperstructureConstants.kElevatorMinHeight;
    public boolean armExtended = false;
    public boolean clawOpen = false;

    public boolean hasCargo = false;
    public boolean hasHatch = false;

    public SuperstructureState(double height, boolean armExtended, boolean clawExtended){
        this.height = height;
        this.armExtended = armExtended;
        this.clawOpen = clawExtended;
    }

    public SuperstructureState(double height){
        this(height, false, false);
    }

    public SuperstructureState(SuperstructureState other){
        this.height = other.height;
        this.armExtended = other.armExtended;
        this.clawOpen = other.clawOpen;
    }

    public SuperstructureState(){
        this(SuperstructureConstants.kElevatorMinHeight, false, false);
    }

}
