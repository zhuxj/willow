/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-14
 */
package com.willow.codegen;

import com.willow.codegen.db.DataSourceConfig;
import com.willow.codegen.db.DataSourceConfigUtil;
import com.willow.codegen.db.DataSourceFactory;
import com.willow.codegen.model.FieldColumn;
import com.willow.codegen.model.TableClass;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class CodeGenTest {
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(CodeGenTest.class);

    @BeforeClass
    public void setUp() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig("jdbc:mysql://localhost:3306/platform?useUnicode=true&characterEncoding=UTF-8",
                "book", "book");
        DataSourceConfigUtil.setDataSourceConfig(dataSourceConfig);
    }

    @Test
    public void DataSourceTest() throws Exception {
        DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance();
        DataSource dataSource = dataSourceFactory.createDataSource();
        Connection connection = DataSourceUtils.getConnection(dataSource);

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getColumns(null, null,
                "sys_menu", null);
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
            System.out.print(columnName + "\t");
            System.out.print(FieldPropertyConvert.fieldToProperty(columnName) + "\t");
            System.out.print(rs.getString("TYPE_NAME") + "\t");
            System.out.print(rs.getInt("COLUMN_SIZE") + "\t");
            System.out.print(rs.getInt("DECIMAL_DIGITS") + "\t");
            System.out.print(rs.getString("REMARKS") + "\t");
            System.out.print(rs.getString("COLUMN_DEF") + "\t");
            System.out.println();
        }
    }

    @Test
    public void getFieldColumnListTest() throws Exception {
        TableClassManager tableClassManager = new TableClassManager();
        List<FieldColumn> fieldColumns = tableClassManager.getFieldColumnList("sys_menu");
        for (FieldColumn fieldColumn : fieldColumns) {
            System.out.println(fieldColumn.getColumnName()
                    + "\t\t" + fieldColumn.getJavaProperty() + "\t\t" + fieldColumn.getPropName());
        }

    }

    @Test
    public void getTableClassTest() throws Exception {
        TableClassManager tableClassManager = new TableClassManager();
        TableClass tableClass = new TableClass("sys_menu", "系统菜单", "SysMenu", "com.willow.platform");
        TableClass result = tableClassManager.getTableClass(tableClass);
        assertNotNull(result);
        List<FieldColumn> fieldColumns = result.getFieldColumns();
        for (FieldColumn fieldColumn : fieldColumns) {
            System.out.println(fieldColumn.getPropName()
                    + "\t" + fieldColumn.getColumnName() + "\t" + fieldColumn.getJavaProperty());
        }
    }

    @Test
    public void FreeMarkerConfigurerBuilderTest() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:ftl/");
        freeMarkerConfigurer.setDefaultEncoding("UTF-8");
        Properties properties = new Properties();
        properties.put("template_update_delay", 5);
        properties.put("locale", "zh_CN");
        freeMarkerConfigurer.setFreemarkerSettings(properties);
    }

    @Test
    public void buildFreeMarkerConfigurerTest1() {
        Configuration cfg = new Configuration();
        cfg.setDefaultEncoding("UTF-8");
        try {
            cfg.setDirectoryForTemplateLoading(new ClassPathResource("ftl/").getFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        try {
            Template template = cfg.getTemplate("codegen/door/tpl_one/doordomain.ftl");
            FreeMarkerTemplateUtils.processTemplateIntoString(template, new HashMap());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
