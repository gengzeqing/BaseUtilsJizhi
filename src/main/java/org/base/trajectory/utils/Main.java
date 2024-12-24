package org.base.trajectory.utils;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 创建变更前的对象
        License oldLicense = new License();
        oldLicense.setLicenseName("合作商A");
        oldLicense.setLicenseCode("00126");
        oldLicense.setActivityType(2);  // 自行车
        //oldLicense.setLicenseType(0);  // 商业许可证
        oldLicense.setSex(66);

        // 创建变更后的对象
        License newLicense = new License();
        newLicense.setLicenseName("合作商B");
        newLicense.setLicenseCode("00126");
       // newLicense.setActivityType(1);  // 火车
        newLicense.setLicenseType(1);  // 个人许可证
        //newLicense.setSex(1);

        // 比较变更前后的对象
        List<String> changes = CompareUtil.compareObjects(oldLicense, newLicense);

        // 打印变更记录
        for (String change : changes) {
            System.out.println(change);
        }
    }
}