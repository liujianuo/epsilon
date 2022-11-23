package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="TeleOpOne")
public class Manualteleop extends OpMode {
    // Define all motors
    DcMotor rightback;
    DcMotor leftback;
    DcMotor rightfront;
    DcMotor leftfront;
    boolean boost = false;
    // defined constants
    protected static final double GOBILDA = 537.7; // tick/rev of gobilda and tetrix dc's
    protected static final double TETRIX = 1440;
    protected static final double GRIPOPEN = 0; // values for opening and closing servos
    protected static final double GRIPCLOSED = 0.75;
    protected static final double POSDOWN = 0; // values for opening and closing servos
    protected static final double POSUP = 1;

    public void init(){
        // Map all motors
        rightback = hardwareMap.dcMotor.get("rightback");
        leftback = hardwareMap.dcMotor.get("leftback");
        rightfront = hardwareMap.dcMotor.get("rightfront");
        leftfront = hardwareMap.dcMotor.get("leftfront");
        // Negate direction for opposite drive motors
        leftback.setDirection(DcMotorSimple.Direction.FORWARD);
        leftfront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightback.setDirection(DcMotorSimple.Direction.REVERSE);
        rightfront.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void loop(){
        //---------------------------------//
        //   Initialisation of variables   //
        //---------------------------------//
        double x = gamepad1.left_stick_y; // Variable for f/b motion
        double y = gamepad1.left_stick_x; // Variable for r/l motion
        double rotation = -0.5*gamepad1.right_stick_x; // Variable for rotation around center
        double lf = 0; // Initial variables for power set to each wheel
        double lb = 0;
        double rf = 0;
        double rb = 0;

        //---------------------------------//
        //     Drive Train Calculations    //
        //---------------------------------//
        
        if(gamepad1.a){ boost = !boost;}
        telemetry.addData("boost", boost);
        telemetry.update();
        if(!boost){
          x*=0.6;
          y*=0.3;
        }

        // Strafing
        if (y == 0 || Math.abs(x/y) > 0.7){
            lf = x;
            rb = x;
            lb = -x;
            rf = -x;
        }
        // Driving Forward and Backwards
        else if (x == 0 || Math.abs(y/x) > 0.7){
            lf = y;
            rb = y;
            lb = y;
            rf = y;
        }

        // Compensate for Rotation
        lf += rotation;
        lb -= rotation;
        rf += rotation;
        rb -= rotation;

        // Finally, assign power values to motors.
        leftfront.setPower(lf);
        leftback.setPower(lb);
        rightfront.setPower(rf);
        rightback.setPower(rb);
    }

    public void stop(){

    }
}

