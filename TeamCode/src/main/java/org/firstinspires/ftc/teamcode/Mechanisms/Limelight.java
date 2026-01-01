//// ========== LimelightHelper.java ==========
//package org.firstinspires.ftc.teamcode.Mechanisms;
//
//import com.qualcomm.hardware.limelightvision.LLResult;
//import com.qualcomm.hardware.limelightvision.Limelight3A;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//public class Limelight {
//    private Limelight3A limelight;
//    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
//    NetworkTableEntry ty = table.getEntry("ty");
//    double targetOffsetAngle_Vertical = ty.getDouble(0.0);
//
//    // how many degrees back is your limelight rotated from perfectly vertical?
//    double limelightMountAngleDegrees = 25.0;
//
//    // distance from the center of the Limelight lens to the floor
//    double limelightLensHeightInches = 20.0;
//
//    // distance from the target to the floor
//    double goalHeightInches = 60.0;
//
//    double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
//    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
//    double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);
//
//    public void init(HardwareMap hardwareMap) {
//        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//        limelight.pipelineSwitch(0);
//        limelight.start();
//    }
//}