package org.base.trajectory.enum1;

import lombok.Getter;

/**
 * @author 耿
 */
public class DataEnum {

    @Getter
    public enum ActivityType {
        CAR(0, "汽车"),
        TRAIN(1, "火车"),
        BICYCLE(2, "自行车");

        private final int value;
        private final String description;

        ActivityType(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public static ActivityType fromValue(int value) {
            for (ActivityType type : ActivityType.values()) {
                if (type.getValue() == value) {
                    return type;
                }
            }
            return null;
        }
    }



    @Getter
    public enum LicenseType {
        BUSINESS(0, "商业许可证"),
        INDIVIDUAL(1, "个人许可证");

        private final int value;
        private final String description;

        LicenseType(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public static LicenseType fromValue(int value) {
            for (LicenseType type : LicenseType.values()) {
                if (type.getValue() == value) {
                    return type;
                }
            }
            return null;
        }
    }

}
