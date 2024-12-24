package org.base.pdf;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @author wh
 * @Description 投诉信息实体类
 */

@Data
@TableName("tb_complaint")
@Alias("complaint")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ColumnWidth(20)
@ExcelIgnoreUnannotated
public class ComplaintVo {

    /**
     * 合作商Code
     */
    @ExcelProperty(index = 0, value = "合作商Code")
    private String partnerCode;
    private String occurrenceDate;
    private String proposedDefaultHandling;
    private String documentDate;
    /**
     * 合作商名称
     */
    private String partnerName;

    /**
     * 合同号
     */
    @ExcelProperty(index = 4, value = "合同编号")
    private String contractNo;

    /**
     * 客户名称
     */
    private String custName;

    /**
     * 经销商名称
     */
    @TableField(exist = false)
    @ExcelProperty(index = 1, value = "经销商")
    private String dealerName;
    /**
     * 消息id
     */
    @TableField(exist = false)
    private Integer messageId;

    /**
     * 受理时间
     */
    @ExcelProperty(index = 8, value = "投诉时间")
    private String acceptTime;

    /**
     * 处理时限
     */
    private String processLimit;

    /**
     * 投诉原因
     */
    private String complaintReason;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 工单类型
     */
    private String complaintType;

    @TableField(exist = false)
    @ExcelProperty(index = 7, value = "工单类型")
    private String complaintTypes;
    /**
     * 1告知函待处理2违约待处理3决议待处理4凭证待处理
     */
    @TableField(exist = false)
    private Integer complaintStatus;

    private String complaintTypeName;

    /**
     * 申诉类型申诉类型  1申诉 2不申诉
     */
    private Integer appealType;

    /**
     * 申诉备注
     */
    private String appealRemark;

    /**
     * 决议结果 1无异常2违约处理3其他
     */
    private Integer resolutionResult;

    @TableField(exist = false)
    private String resolutionResults;

    /**
     * 决议结果URL
     */
    private String resolutionResultUrl;

    /**
     * 扣款方式类型1 履约金风险金扣除 2 转账汇款
     */
    private Integer deductionMethodType;

    /**
     * 决议结果备注
     */
    private String resolutionResultRemark;

    /**
     * 收款凭证URL
     */
    private String paymentReceiptUrls;

    /**
     * 合作商状态状态0 处理状态1待处理5已处理
     */
    private Integer partnerStatus;
    /**
     * 合作商ID
     */
    private Integer partnerId;

    /**
     * 处理状态1待发起5告知函待处理10决议待处理15违约待处理20凭证待处理25已结案
     */
    private Integer status;

    @TableField(exist = false)
    private String resultStatusValue;

    /**
     * 处理任务目标日期
     */
    private String targetDate;

    /**
     * 合作商处理处罚目标日期
     */
    private String partnerTargetDate;
    @TableField(exist = false)
    private Integer complaintId;
    @TableField(exist = false)
    private String startDate;
    @TableField(exist = false)
    private String endDate;
    @TableField(exist = false)
    private Long startTime;
    @TableField(exist = false)
    private Long endTime;

    @TableField(exist = false)
    private String proStartDate;
    @TableField(exist = false)
    private String proEndDate;
    @TableField(exist = false)
    private Long startTime1;
    @TableField(exist = false)
    private Long endTime1;


    @TableField(exist = false)
    private List<Integer> partnerList;
    /**
     * 是否是销售支持
     */
    @TableField(exist = false)
    private Integer isSaleSupport;
    /**
     * 区域经理姓名
     */
    @TableField(exist = false)
    private String regionalManagerNames;

    /**
     * 贷款金额
     */
    @TableField(exist = false)
    @ExcelProperty(index = 6, value = "贷款金额")
    private String financedAmt;


    /**
     * 推送区域经理姓名
     */
    private String regionalManagerPushName;

    /**
     * 剩余天数
     */
    @TableField(exist = false)
    private Long remainDays;


    /**
     * 页数
     */
    @TableField(exist = false)
    private Integer current;

    /**
     * 数量
     */
    @TableField(exist = false)
    private Integer pageSize;

    /**
     * 修改时间
     */
    @TableField(exist = false)
    private String modifyTime;

    /**
     * 是否任务逾期处理0否1是
     */
    private Integer isOverdueHandle;


    /**
     * 合作商决议是否逾期处理0否1是
     */
    private Integer isPartnerOverdueHandle;


    /**
     * 告知函违约类型
     */
    private String noticeDefaultType;

    /**
     * 告知函类型
     */
    private String noticeType;


    /**
     * 处决发生日期
     */
    private String resolutionOccurrenceDate;
    /**
     * 处决文件日期
     */
    private String resolutionDocumentDate;

    /**
     * 决议不规范类型
     */
    private String resolutionDefaultType;

    /**
     * 决议类型不能为空
     */
    private String resolutionHandlingType;

    private String resolutionHandlingAmount;

    /**
     * 结案说明
     */
    private String caseClosureExplanation;

    /**
     * 结案URL
     */
    private String caseClosureUrl;


    /**
     * 告知函件URL
     */
    private String noticeLetterUrl;


    /**
     * 申诉凭证url
     */
    private String appealUrl;

    /**
     * 投诉表单ID
     */
    private String fdId;

    /**
     * 是否处理按钮 0未处理1已处理
     */
    private Integer showHandleBtn;

    /**
     * 是否文件
     */
    @TableField(exist = false)
    private Integer isFileId;

    @TableField(exist = false)
    private String createTime;
    /**
     * 渠道名称
     */
    @TableField(exist = false)
    @ExcelProperty(index = 2, value = "子渠道")
    private String channelName;

    @TableField(exist = false)
    private Integer count;

    /**
     * 合同申请日期
     */
    @TableField(exist = false)
    private String contractCreatDte;

    /**
     * 放款金额
     */
    @TableField(exist = false)
    private String loanAmountTotal;

    /**
     * 放款日期
     */
    @TableField(exist = false)
    @ExcelProperty(index = 5, value = "放款日期")
    private String contractActivityDte;

    /**
     * 见证人姓名
     */
    @TableField(exist = false)
    @ExcelProperty(index = 3, value = "金融服务人员")
    private String applicationWitnessName;
    /**
     * 代办类型 0消息 1待办  2抄送我的 3已完成
     */
    @TableField(exist = false)
    private Integer dutyType;

    /**
     * 阅读状态0未读 1已读
     */
    @TableField(exist = false)
    private Integer readStatus;

}
