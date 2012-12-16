/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-12
 */
package com.willow.unitilsext.dbunit;

import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.excel.XlsDataSet;
import org.unitils.core.UnitilsException;
import org.unitils.dbunit.datasetfactory.DataSetFactory;
import org.unitils.dbunit.util.MultiSchemaDataSet;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class MultiSchemaXmlDataSetFactory implements DataSetFactory {
    //模式名和表名分隔符
    private static String SPLITOR_REGEXP = "\\.";

    //默认的模式名
    protected String defaultSchemaName = "platform";

    public void init(Properties configuration, String defaultSchemaName) {
        this.defaultSchemaName = defaultSchemaName;
    }

    /**
     * 从Excel文件中读取多个数据值
     *
     * @param dataSetFiles
     * @return
     */
    public MultiSchemaDataSet createDataSet(File... dataSetFiles) {
        Map<String, List<ITable>> tableMap = getTables(dataSetFiles);
        MultiSchemaDataSet dataSets = new MultiSchemaDataSet();
        for (Map.Entry<String, List<ITable>> entry : tableMap.entrySet()) {
            List<ITable> tables = entry.getValue();
            try {
                DefaultDataSet ds = new DefaultDataSet(tables.toArray(new ITable[]{}));
                dataSets.setDataSetForSchema(entry.getKey(), ds);
            } catch (Exception e) {
                throw new UnitilsException(String.format("使用指定表[%s]重新构造DataSet失败", tables), e);
            }
        }
        return dataSets;
    }

    /**
     * 每个Sheet对应一个表
     *
     * @param dataSetFiles
     * @return
     */
    private Map<String, List<ITable>> getTables(File... dataSetFiles) {
        Map<String, List<ITable>> tableMap = new HashMap<String, List<ITable>>();
        try {
            for (File file : dataSetFiles) {
                IDataSet dataSet = new XlsDataSet(new FileInputStream(file));
                String[] tableNames = dataSet.getTableNames();
                for (String fullTableNames : tableNames) {
                    String schema = null;
                    String tableName;
                    String[] temp = fullTableNames.split(SPLITOR_REGEXP);
                    if (temp.length == 2) {
                        schema = temp[0];
                        tableName = temp[1];
                    } else {
                        schema = this.defaultSchemaName;
                        tableName = fullTableNames;
                    }
                    ITable table = dataSet.getTable(fullTableNames);
                    if (!tableMap.containsKey(schema)) {
                        tableMap.put(schema, new ArrayList<ITable>());
                    }
                    tableMap.get(schema).add(new XslTableWrapper(tableName, table));
                }
            }
        } catch (Exception e) {
            throw new UnitilsException("Unable to create DbUnit dataset for data set files: "
                    + Arrays.toString(dataSetFiles), e);
        }
        return tableMap;
    }

    public String getDataSetFileExtension() {
        return "xls";
    }
}