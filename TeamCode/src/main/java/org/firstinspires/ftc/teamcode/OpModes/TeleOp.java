package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.ColorOne;
import org.firstinspires.ftc.teamcode.Mechanisms.ColorThree;
import org.firstinspires.ftc.teamcode.Mechanisms.ColorTwo;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.KickStand;
//import org.firstinspires.ftc.teamcode.Mechanisms.Limelight;
import org.firstinspires.ftc.teamcode.Mechanisms.LimelightConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.Shooter;
import org.firstinspires.ftc.teamcode.Mechanisms.Turret;
import org.firstinspires.ftc.teamcode.Mechanisms.Hood;
import org.firstinspires.ftc.teamcode.Mechanisms.KickConfig;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")

public class TeleOp extends LinearOpMode{
    KickStand kickStand = new KickStand();
    Shooter shooter = new Shooter();
    Turret turret = new Turret();
    Hood hood = new Hood();
    KickConfig kick = new KickConfig();
    MecDrivebase drive = new MecDrivebase();
    IntakeConfig intake = new IntakeConfig();
    ElapsedTime kickTimer = new ElapsedTime();
    LimelightConfig limelight = new LimelightConfig();
    double forward, strafe, rotate;
    ColorOne c1 = new ColorOne();
    ColorTwo c2 = new ColorTwo();
    ColorThree c3 = new ColorThree();
    int step = -1;
    public void autoKick(){
        if(step == -1) return;
        switch(step){

            case 0:
                kick.kickOne();
                kickTimer.reset();
                step = 1;
                break;
            case 1:
                if(kickTimer.milliseconds() >= 200){
                    kick.retractOne();
                    step = 2;
                }
                break;
            case 2:
                kick.kickTwo();
                kickTimer.reset();
                step = 3;
                break;
            case 3:
                if(kickTimer.milliseconds() >= 200){
                    kick.retractTwo();
                    step = 4;
                }
                break;
            case 4:
                kick.kickThree();
                kickTimer.reset();
                step = 5;
                break;
            case 5:
                if(kickTimer.milliseconds() >= 200){
                    kick.retractThree();
                    step = -1;
                }
                break;


        }
    }
    int green = -1;
    int purple = -1;
    public void sortGreen(){
        if(green == -1){ return;}
        switch(green){
            case 0:
                if(c1.getDetectedColor(telemetry).equals(ColorOne.DetectedColor.GREEN)){
                    kick.kickOne();
                    kickTimer.reset();
                    if(kickTimer.milliseconds() >= 200){
                        kick.retractOne();
                    }
                    green = -1;
                }else{
                    kickTimer.reset();
                    green = 1;
                }
                break;
            case 1:
                if(c2.getDetectedColor(telemetry).equals(ColorTwo.DetectedColor.GREEN)){
                    kick.kickTwo();
                    kickTimer.reset();
                    if(kickTimer.milliseconds() >= 200){
                        kick.retractTwo();
                    }
                    green = -1;
                }else{
                    kickTimer.reset();
                    green = 2;
                }
                break;
            case 2:
                if(c3.getDetectedColor(telemetry).equals(ColorThree.DetectedColor.GREEN)){
                    kick.kickThree();
                    kickTimer.reset();
                    if(kickTimer.milliseconds() >= 200){
                        kick.retractThree();
                    }
                    green = -1;
                }else{
                    kickTimer.reset();
                    telemetry.addLine("ERROR");
                    green = -1;
                }
        }
    }
    public void sortPurple() {
        if (green == -1) {
            return;
        }
        switch (green) {
            case 0:
                if (c1.getDetectedColor(telemetry).equals(ColorOne.DetectedColor.PURPLE)) {
                    kick.kickOne();
                    kickTimer.reset();
                    if (kickTimer.milliseconds() >= 200) {
                        kick.retractOne();
                    }
                    purple = -1;
                } else {
                    kickTimer.reset();
                    purple = 1;
                }
                break;
            case 1:
                if (c2.getDetectedColor(telemetry).equals(ColorTwo.DetectedColor.PURPLE)) {
                    kick.kickTwo();
                    kickTimer.reset();
                    if (kickTimer.milliseconds() >= 200) {
                        kick.retractTwo();
                    }
                    purple = -1;
                } else {
                    kickTimer.reset();
                    purple = 2;
                }
                break;
            case 2:
                if (c3.getDetectedColor(telemetry).equals(ColorThree.DetectedColor.PURPLE)) {
                    kick.kickThree();
                    kickTimer.reset();
                    if (kickTimer.milliseconds() >= 200) {
                        kick.retractThree();
                    }
                    purple = -1;
                } else {
                    kickTimer.reset();
                    telemetry.addLine("ERROR");
                    purple = -1;
                }
        }
    }


    @Override
    public void runOpMode() throws InterruptedException {
        intake.init(hardwareMap);
        kick.init(hardwareMap);
        shooter.init(hardwareMap);
        turret.init(hardwareMap);
        hood.init(hardwareMap);
        kickStand.init(hardwareMap);
        drive.init(hardwareMap);
        limelight.init(hardwareMap);
        c1.init(hardwareMap);
        c2.init(hardwareMap);
        c3.init(hardwareMap);

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            forward = gamepad1.left_stick_y;
            strafe = -gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;
            drive.driveFieldRelative(forward, strafe, rotate);

            if(gamepad2.left_trigger >= 0.7){
                intake.IntakeMotorMax();
            } else if (gamepad2.dpad_up){
                intake.OutIntake();
            } else {
                intake.IntakeMotorStop();
            }

            limelight.alignTurret(gamepad2.left_bumper);



            if(gamepad1.right_trigger >= 0.7){
                shooter.shooterMax();
            } else {
                shooter.shooterStop();
            }

            if(gamepad2.right_stick_x > 0.0) {
                turret.Right();
            } else if(gamepad2.right_stick_x < 0.0){
                turret.Left();
            } else {
                turret.Stop();
            }

            if(-gamepad2.left_stick_y == 1.0) {
                hood.hoodHigh();
            } else if(-gamepad2.left_stick_y == -1.0){
                hood.hoodLow();
            } else{
                hood.hoodMed();
            }

            if(gamepad1.y){
                kickStand.kickStandMax();
            } else {
                kickStand.kickStandStop();
            }

            if(gamepad1.dpad_up) {
                drive.imu.resetYaw();
            }

            if(gamepad2.dpad_left && green == -1){
                green = 0;
            }

            if(gamepad2.dpad_right && purple == -1){
                purple = 0;
            }

            if(gamepad2.y && step == -1) {
                step = 0;
            }

            autoKick();
            sortGreen();
            sortPurple();

            telemetry.update();
        }
    }
}
