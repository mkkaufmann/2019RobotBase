package frc.robot.states;

public class SuperstructureState {
    public double height = SuperstructureConstants.kElevatorMinHeight;
    public boolean projectorExtended = false;
    public boolean clawOpen = false;

    public boolean hasCargo = false;
    public boolean hasHatch = false;

    public SuperstructureState(double height, boolean projectorExtended, boolean clawExtended){
        this.height = height;
        this.projectorExtended = projectorExtended;
        this.clawOpen = clawExtended;
    }

    public SuperstructureState(double height){
        this(height, false, false);
    }

    public SuperstructureState(SuperstructureState other){
        this.height = other.height;
        this.projectorExtended = other.projectorExtended;
        this.clawOpen = other.clawOpen;
    }

    public SuperstructureState(){
        this(SuperstructureConstants.kElevatorMinHeight, false, false);
    }

}
