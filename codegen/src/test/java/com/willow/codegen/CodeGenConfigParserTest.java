/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen;


import com.willow.codegen.config.CodeGenConfigParser;
import com.willow.codegen.model.codegenconfig.CodeGenConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

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
public class CodeGenConfigParserTest {
    @Test
    public void parserCodeGenConfigTest() {
        CodeGenConfigParser codeGenConfigParser = new CodeGenConfigParser();
        String codegenConfigPath = "/com/willow/codegen/DoorCodeGenConfigTest.yml";
        CodeGenConfig codeGenConfig = codeGenConfigParser.parserCodeGenConfigFromClassPath(codegenConfigPath);
        assertNotNull(codeGenConfig);
        Assert.assertEquals(codeGenConfig.getTable().getTableCode(), "sys_user");

    }

}
