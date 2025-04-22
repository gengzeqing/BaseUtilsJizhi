package org.base.download.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 耿
 * @since 2025-01-07
 */
@Data
@Alias("riskFundTask")
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("TB_RISK_FUND_TASK")
public class RiskFundTaskVo {



    /**
     * 合作商ID
     */
    private Integer id;
    private Integer partnerId;

    /**
     * 合作商CODE
     */
    private String partnerCode;
    private String num;

    /**
     * 补缴退还类型
     */
    private Integer repaymentType;

    /**
     * 补缴通知书URL
     */
    private String repaymentNoticeUrl;

    /**
     * 截止时间
     */
    private String deadline;

    /**
     * 补缴金额
     */
    private String repaymentAmount;

    /**
     * 补缴金额合计
     */
    private String totalRepaymentAmount;

    /**
     * 实际补缴金额
     */
    private String actualRepaymentAmount;

    /**
     * 汇款凭证URL
     */
    private String remittanceVoucherUrl;

    /**
     * 补缴说明
     */
    private String repaymentDescription;

    /**
     * 补缴确认说明  or QA 退还确认说明
     */
    private String repaymentConfirmationDescription;

    /**
     * 补缴确认附件URL or QA 退还确认附件URL
     */
    private String repaymentConfirmationAttachmentUrl;

    /**
     * 退款金额
     */
    private String refundAmount;
    /**
     * 申请人
     */
    private String applicant;
    /**
     * 款项内容
     */
    private String paymentContent;
    /**
     * 收款单位名称
     */
    private String payee;
    /**
     * 退还时间
     */
    private String refundDate;

    /**
     * 任务状态 	0 待补缴	1 审核中	2 已确认	3 超时未反馈
     */
    private Integer taskStatus;
    /**
     * 是否超时 0 否 1是
     */
    private Integer timeOutStatus;
    /**
     * 汇总实缴（补缴确认）
     */
    private String totalActualContribution;

    @TableField(exist = false)
    private Integer taskId;
    @TableField(exist = false)
    private String partnerName;
    @TableField(exist = false)
    private List<Integer> partnerFunIdList;
    //归属片区Id
    @TableField(exist = false)
    private String belongingAreaId;

    @TableField(exist = false)
    private String createTime;
    @TableField(exist = false)
    private String updateTime;
    @TableField(exist = false)
    private String repaymentValue;
    /**
     * 合同号
     */
    @TableField(exist = false)
    private String contractNo;

    /**
     * 申请编号
     */
    @TableField(exist = false)
    private String applicationNo;
    private String processNumber; // 流程编号


    /**
     * 更新时间开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(exist = false)
    private Date updateTimeStart;
    @TableField(exist = false)
    private Long updateTimeStartLong;
    private String imageUrl;
    public String getProcessNumber() {
        if (this.getId()!= null) {
            processNumber = String.format("%03d", this.getId());
        }
        return processNumber;
    }



}
