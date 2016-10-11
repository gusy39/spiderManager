package com.ecmoho.common.web.esayui;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyUI DataGrid模型
 *
 * @author liox
 */
public class GridPage implements java.io.Serializable {

    private Integer total = 0;
    private List rows = new ArrayList();

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public GridPage() {
    }

    public GridPage(Integer total, List rows) {
        this.total = total;
        this.rows = rows;
    }
}
