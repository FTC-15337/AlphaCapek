package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Kronos.IntakeConfig;
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
    IntakeConfig intake = new IntakeConfig();
    MecDrivebase drive = new MecDrivebase();

    double forward, strafe, rotate;

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

            if(gamepad1.left_trigger >= 0.7){
                intake.IntakeMotorMax();
            } else if (gamepad1.dpad_up){
                intake.OutIntake();
            } else {
                intake.IntakeMotorStop();
            }

            if(gamepad1.a){
                kick.kickOne();
            } else {
                kick.retractOne();
            }

            if(gamepad1.b){
                kick.kickTwo();
            } else {
                kick.retractTwo();
            }

            if(gamepad1.x){
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

//            public void kick(){
//
//            }
        }
    }
}
