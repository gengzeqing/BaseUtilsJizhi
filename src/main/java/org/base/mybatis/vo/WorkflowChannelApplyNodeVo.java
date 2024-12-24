package org.base.mybatis.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 申请表审批流节点
 */
@Data
@TableName("tb_workflow_apply_node")
public class WorkflowChannelApplyNodeVo {


    private Integer id;
    private Integer applyId;

    /**
     * 该审批的标题名字
     */
    private String name;

    /**
     * 类别(approver是审批人,notifier是抄送人)
     */
    private String type;

    /**
     * 多人审批的类型(ALL会签(须所有审批人同意),ONE_BY_ONE依次审批,FROM_OF_ALL或签(一名审批人同意或拒绝即可))
     */
    private String activateType;

    /**
     * 审批人ids
     */
    private String approvals;

    /**
     * 审批状态(1审批中2通过3拒绝4撤销)
     */
    private Integer status;

    /**
     * 审批流模板id
     */
    private Integer workflowAutoId;
    private Integer partnerId;

    /**
     * 是否是直接主管审批
     */
    private Integer isDirectManagement;
    /**
     * 节点
     */
    private Integer levelNode;
}