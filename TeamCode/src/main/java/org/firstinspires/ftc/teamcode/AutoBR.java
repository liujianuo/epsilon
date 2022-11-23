//-------------------------------------------------------------------------------------------//
//                              Introduction to the Autonomous                               //
//                                    It's not done...                                       //
//                                                                                           //
//                                 https://ibb.co/F3mdNWW                                    //
//-------------------------------------------------------------------------------------------//
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="BR")
public class AutoBR extends LinearOpMode{
    // Define all motors
    DcMotor rightback;
    DcMotor leftback;
    DcMotor rightfront;
    DcMotor leftfront;
    // defined constants
    protected static final double GOBILDA = 537.7; // tick/rev of gobilda and tetrix dc's
    protected static final double TETRIX = 1440;
    protected static final double GRIPOPEN = 0; // values for opening and closing servos
    protected static final double GRIPCLOSED = 1;

    //------------------------------------------------//
    //This Function is to apply drive values to motors//
    //------------------------------------------------//

    void RunDrive(double y, double x, double rotation, long time ){
        double lf = 0;
        double rf = 0;
        double lb = 0;
        double rb = 0;
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

        // Wait time for work
        sleep(time);

        // Reset Values
        leftfront.setPower(0);
        leftback.setPower(0);
        rightfront.setPower(0);
        rightback.setPower(0);
    }

    public void runOpMode(){
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
        // Initialise arm motors
        //gripper.setDirection(Servo.Direction.FORWARD);
        // Define Motor Values

        waitForStart(); // waiting for start;

        RunDrive(0.6, 0, 0, 800);

    }
}
