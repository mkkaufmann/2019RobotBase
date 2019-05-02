package frc.robot.controlboard.buttons;

import frc.robot.lib.Boolean;

public abstract class HeightButton {
    private double height;
    private Boolean button;

    public HeightButton(double height, Boolean button){
        this.height = height;
        this.button = button;
    }

    public double getHeight() {
        return height;
    }

    public Boolean getButton() {
        return button;
    }
}
