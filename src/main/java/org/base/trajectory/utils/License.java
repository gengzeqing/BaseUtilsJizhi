package org.base.trajectory.utils;

import lombok.Data;
import org.base.trajectory.annotation.EnumField;
import org.base.trajectory.annotation.FieldDescription;
import org.base.trajectory.enum1.DataEnum;

/**
 * @author 耿
 */
@Data
public class License {

    @FieldDescription("营业执照名称")
    private String licenseName;

    @FieldDescription("营业执照号")
    private String licenseCode;

    /* 经营类型，映射到 ActivityType 枚举*/
    @FieldDescription("经营类型")
    @EnumField(enumClass = DataEnum.ActivityType.class)
    private Integer activityType;

    /* 许可证类型，映射到 LicenseType 枚举*/
    @FieldDescription("许可证类型")
    @EnumField(enumClass = DataEnum.LicenseType.class)
    private Integer licenseType;

    @FieldDescription("性别")
    private Integer sex;

}
