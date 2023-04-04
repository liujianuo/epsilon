package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="TensorFlow")
public class Manualteleop extends OpMode {
    // Define all motors
    DcMotor rightback;
    DcMotor leftback;
    DcMotor rightfront;
    DcMotor leftfront;
    DcMotor arm;
    Servo grip1;
    Servo grip2;
    boolean boost = false;
    // defined constants
    protected static final double GOBILDA = 537.7; // tick/rev of gobilda and tetrix dc's
    protected static final double TETRIX = 1440;
    protected static final double GRIPOPEN = 0; // values for opening and closing servos
    protected static final double GRIPCLOSED = 0.75;
    protected static final double POSDOWN = 0; // values for opening and closing servos
    protected static final double POSUP = 1;
    protected static final double leftopen = 0.7;
    protected static final double rightopen = 0.7;
    protected static final double leftclosed = 0.9;
    protected static final double rightclosed = 0.9;
    protected static final double armdown = -1;
    protected static final double armup = 30500;

    public void init(){
        // Map all motors
        rightback = hardwareMap.dcMotor.get("rightback");
        leftback = hardwareMap.dcMotor.get("leftback");
        rightfront = hardwareMap.dcMotor.get("rightfront");
        leftfront = hardwareMap.dcMotor.get("leftfront");
        arm = hardwareMap.dcMotor.get("armo");
        grip1 = hardwareMap.servo.get("servo1");
        grip2 = hardwareMap.servo.get("servo2");
        // Negate direction for opposite drive motors
        leftback.setDirection(DcMotorSimple.Direction.REVERSE);
        leftfront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightback.setDirection(DcMotorSimple.Direction.REVERSE);
        rightfront.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setDirection(DcMotorSimple.Direction.FORWARD);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        grip2.setDirection(Servo.Direction.FORWARD);
        grip1.setDirection(Servo.Direction.REVERSE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
    }

    public void loop(){
        //---------------------------------//
        //   Initialisation of variables   //
        //---------------------------------//
        double x = 0.6*gamepad1.left_stick_y; // Variable for f/b motion
        double y = 0.6*gamepad1.left_stick_x; // Variable for r/l motion
        double rotation = -0.5*gamepad1.right_stick_x; // Variable for rotation around center
        double armeth = -gamepad2.right_stick_y; // arm
        boolean grip = gamepad2.a; //servo
        double lf = 0; // Initial variables for power set to each wheel
        double lb = 0;
        double rf = 0;
        double rb = 0;
        
        if(grip){
            grip1.setPosition(leftopen);
            grip2.setPosition(rightopen);
            telemetry.addData("grip", "closed");
        }
        else{
            grip1.setPosition(leftclosed);
            grip2.setPosition(rightclosed);
            telemetry.addData("grip", "open");
        }

        //---------------------------------//
        //     Drive Train Calculations    //
        //---------------------------------//

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
        
        telemetry.addData("lf", lf);
        telemetry.addData("rf", rf);
        telemetry.addData("lb", lb);
        telemetry.addData("rb", rb);
        telemetry.addData("arm", arm.getCurrentPosition());
        
        telemetry.update();
        
        leftfront.setPower(lf);
        leftback.setPower(lb);
        rightfront.setPower(rf);
        rightback.setPower(rb);
        
        //if((armeth > 0.1 && arm.getCurrentPosition() > armup) || (armeth < -0.1 && arm.getCurrentPosition() < armdown)){
        //    arm.setPower(armeth);
            
        //}
        
        //arm.setPower(armeth);
        if((armeth>0.0 && arm.getCurrentPosition() < armup) || (armeth<0.0 && arm.getCurrentPosition() > armdown)){
            arm.setPower(armeth);
            telemetry.addData("armque", true);
        }
        else{
            arm.setPower(0);
            telemetry.addData("armque", false);
        } 
    }

    public void stop(){

    }
}


if (tfod != null) {
    tfod.activate();

    // The TensorFlow software will scale the input images from the camera to a lower resolution.
    // This can result in lower detection accuracy at longer distances (> 55cm or 22").
    // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
    // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
    // should be set to the value of the images used to create the TensorFlow Object Detection model
    // (typically 16/9).
    tfod.setZoom(2.5, 16.0/9.0);
}