package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//销售出库单
@Setter
@Getter
public class StockOutcomeBill extends BaseDomain {

    public static final int NORMAL = 0;//未审核
    public static final int AUDIT = 1;//已审核
    //出库单编号
    private String sn;
    //出库单日期
    private Date vdate;
    //单据状态 0 代表未审核 1 代表审核
    private int status = StockOutcomeBill.NORMAL;//未审核
    //出库总金额
    private BigDecimal totalAmount;
    //出库总数量
    private BigDecimal totalNumber;
    //审核时间
    private Date auditTime;
    //录入时间
    private Date inputTime;
    //制单人
    private Employee inputUser;
    //审核人
    private Employee auditor;
    //仓库
    private Depot depot;
    //客户
    private Client client;
    //关联采购出库单明细
    private List<StockOutcomeBillItem> items=new ArrayList<>();
}
