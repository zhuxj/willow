/**
 * 版权声明：中图一购网络科技有限公司 版权所有 违者必究 2011 
 * 日    期：11-6-1
 */
package com.willow.unitilsext.dbunit;

import org.apache.commons.lang.StringUtils;
import org.dbunit.dataset.*;
import org.unitils.core.UnitilsException;

/**
 * <pre></pre>
 *
 * @author 朱贤俊
 * @version 1.0
 */
public class XslTableWrapper extends AbstractTable {
    private ITable table;
    private String tableName;

    public XslTableWrapper(String tableName, ITable table) {
        this.tableName = tableName;
        this.table = table;
    }

    public int getRowCount() {
        return table.getRowCount();
    }

    public ITableMetaData getTableMetaData() {
        ITableMetaData meta = table.getTableMetaData();
        try {
            return new DefaultTableMetaData(tableName, meta.getColumns(), meta.getPrimaryKeys());
        } catch (DataSetException e) {
            throw new UnitilsException("Don't get the meta info from  " + meta, e);
        }
    }

    private static final String NULL_VALUE = "null";

    public Object getValue(int row, String column) throws DataSetException {
        Object delta = table.getValue(row, column);
        if (delta instanceof String) {
            if (StringUtils.isEmpty((String) delta) || NULL_VALUE.equalsIgnoreCase((String) delta)) {
                return null;
            }
        }
        return delta;
    }
}
