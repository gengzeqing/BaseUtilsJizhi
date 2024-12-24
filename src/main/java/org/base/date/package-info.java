package org.base.date;
/**
 *
 *  @DateTimeFormat(pattern = "yyyy-MM-dd")
 *      注解 可以用来将字符串转换为日期类型，前端传递接收数据时使用

 *  @DateTimeFormat(pattern="yyyy-MM-dd")
 *  @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8") 注解 可以用来将日期类型，格式化传递给前端使用
 *  private Date createDate;
 *
 * getMaxTime() 方法用来获取当前时间的最大值  比如：获取传入时间的最大值 2024-12-23 -> 2024-12-23 23:59:59
 * DateUtil.getMaxTime
 *
 * getLastWorkingDayOfMonth 方法用来获取当月最后一个工作日
 *
 * daysToReachTargetWorkingDate 计算指定日期 几天 后的工作日
 *
 */