package frc.robot.lib;

public class FlippedBoolean implements Boolean {

    private Boolean b;

    public FlippedBoolean(Boolean b){
        this.b = b;
    }

    @Override
    public boolean get() {
        return !b.get();
    }
}
