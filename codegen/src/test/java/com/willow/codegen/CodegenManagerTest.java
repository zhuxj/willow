/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-15
 */
package com.willow.codegen;

import org.testng.annotations.Test;

/**
 * <pre>
 * 通过java来使用代码生成器生成文件
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class CodegenManagerTest {
    @Test
    public void generateTest() {
        CodegenManager codegenManager = new CodegenManager();
        String codegenConfigPath = "/com/willow/codegen/DoorCodeGenConfigTest.yml";
        try {
            codegenManager.generateFromJava(codegenConfigPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
