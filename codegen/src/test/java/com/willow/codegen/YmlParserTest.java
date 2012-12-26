/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen;

import com.willow.codegen.model.codegenconfig.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class YmlParserTest {

    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(YmlParserTest.class);

    @Test
    public void ymlParserTest() {
        Yaml yaml = new Yaml();
        String document = "hello: 25";
        Map map = (Map) yaml.load(document);
        assertEquals("{hello=25}", map.toString());
        assertEquals(25, map.get("hello"));
    }

    @Test
    public void ymlParserTest1() throws Exception {
        Yaml yaml = new Yaml();
        InputStream input = new ClassPathResource("/com/willow/codegen/DoorCodeGenConfigTest.yml").getInputStream();
        CodeGenConfig config = (CodeGenConfig) yaml.load(input);
        logger.info(config.toString());
        System.out.println(yaml.dump(config));
    }

    @Test
    public void domainTest() {
        DatabaseConfig databaseConfig = new DatabaseConfig("aa", "book", "book");
        DeveloperConfig developerConfig = new DeveloperConfig();
        developerConfig.setCompany("company");
        developerConfig.setDeveloper("zhuxj");
        CodeGenConfig codeGenConfig = new CodeGenConfig();
        codeGenConfig.setDatabaseConfig(databaseConfig);
        codeGenConfig.setDeveloperConfig(developerConfig);
        List<TemplateConfig> templateConfigs = new ArrayList<TemplateConfig>();
        TemplateConfig templateConfig = new TemplateConfig("mamper", "mapper");
        templateConfigs.add(templateConfig);
        FtlConfig ftlConfig = new FtlConfig("ff", templateConfigs);
        codeGenConfig.setFtlConfig(ftlConfig);
        Yaml yaml = new Yaml();
        String dump = yaml.dump(codeGenConfig);
        System.out.println(dump);

    }


}
