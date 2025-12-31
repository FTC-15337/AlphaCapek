package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.KickStand;
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
    double forward, strafe, rotate;

    static int step = -1;

    public void kickKickKick(){

        if (step == -1) return;

        switch (step) {
            case 0:
                kickTimer.reset();
                step = 1;
                break;
            case 1:
                kick.kickOne();
                kickTimer.reset();
                step = 2;
                break;
            case 2:
                if (kickTimer.milliseconds() > 350) {
                    kick.retractOne();
                    kickTimer.reset();
                    step = 3;
                }
                break;
            case 3:
                if(kickTimer.milliseconds() > 200) {
                    kick.kickTwo();
                    kickTimer.reset();
                    step = 4;
                }
                break;
            case 4:
                if (kickTimer.milliseconds() > 350) {
                    kick.retractTwo();
                    kickTimer.reset();
                    step = 5;
                }
                break;
            case 5:
                if (kickTimer.milliseconds() > 350) {
                    kick.kickThree();
                    kickTimer.reset();
                    step = 6;
                }
                break;
            case 6:
                if (kickTimer.milliseconds() > 350) {
                    kick.retractThree();
                    kickTimer.reset();
                    step = -1;
                }
                break;
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

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            forward = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;
            drive.driveFieldRelative(forward, strafe, rotate);

            if(gamepad2.left_trigger >= 0.7){
                intake.IntakeMotorMax();
            } else if (gamepad2.dpad_up){
                intake.OutIntake();
            } else {
                intake.IntakeMotorStop();
            }

            if(gamepad2.a){
                kick.kickOne();
            } else {
                kick.retractOne();
            }

            if(gamepad2.b){
                kick.kickTwo();
            } else {
                kick.retractTwo();
            }

            if(gamepad2.x){
                kick.kickThree();
            } else {
                kick.retractThree();
            }

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

            if(gamepad2.y){
                kickKickKick();
            }
        }
    }
}
