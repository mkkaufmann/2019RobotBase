package frc.robot.lib;

public class GState {

    private LatchedBoolean pressed;
    private LatchedBoolean released;

    public class StateValues {
        public boolean pressed;
        public boolean released;
        public StateValues(boolean pressed, boolean released){
            this.pressed = pressed;
            this.released = released;
        }

    }

    public GState(){
        pressed = new LatchedBoolean();
        released = new LatchedBoolean();
    }

    public StateValues update(boolean held){
        return new StateValues(pressed.update(held), released.update(!held));
    }
}
