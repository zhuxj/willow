/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-16
 */
package com.willow.platform.codegen;

import com.willow.platform.codegen.model.codegenconfig.CodeGenConfig;
import com.willow.platform.codegen.model.codegenconfig.CodeGenFileConfig;
import com.willow.platform.codegen.model.codegenconfig.OutFileConfig;
import com.willow.platform.utils.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 代码生成器文件生成管理器
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class CodegenFileGenerater {
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(CodegenFileGenerater.class);
    private CodeGenConfig codeGenConfig;
    private Map<String, String> contentMap;

    public CodegenFileGenerater() {
    }

    public CodegenFileGenerater(CodeGenConfig codeGenConfig, Map<String, String> contentMap) {
        this.codeGenConfig = codeGenConfig;
        this.contentMap = contentMap;
    }

    /**
     * 生成xxxMapper.java,xxx.mamper.xml,xxxDao.java,xxxService,xxx.java等
     */
    public void generateFiles() {
        CodeGenFileConfig codeGenFileConfig = codeGenConfig.getCodeGenFileConfig();
        List<OutFileConfig> outFileConfigs = codeGenFileConfig.getOutFileConfigs();
        String baseDir = codeGenFileConfig.getBaseDir();
        for (OutFileConfig outFileConfig : outFileConfigs) {
            String refTemplate = outFileConfig.getRefTemplate();
            String fileName = StringUtils.replace(outFileConfig.getFileName(), CodegenConst.CLASS_VAR, codeGenConfig.getTable().getClassVar());
            String dir = StringUtils.replace(outFileConfig.getDir(), CodegenConst.SIMPLE_PACKAGE_VAR, codeGenConfig.getTable().getSimplePackageVar());
            File pathFile = new File(baseDir + dir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            String willowFileName = baseDir + dir + "\\" + fileName;
            File willowFile = new File(willowFileName);
            if (willowFile.exists()) {
                if (outFileConfig.getOverride()) {
                    FileUtil.saveFile(willowFileName, contentMap.get(refTemplate), "UTF-8");
                    logger.info("生成" + willowFileName + "成功");
                }
            } else {
                FileUtil.saveFile(willowFileName, contentMap.get(refTemplate), "UTF-8");
                logger.info("生成" + willowFileName + "成功");
            }


        }
    }


    public CodeGenConfig getCodeGenConfig() {
        return codeGenConfig;
    }

    public void setCodeGenConfig(CodeGenConfig codeGenConfig) {
        this.codeGenConfig = codeGenConfig;
    }

    public Map<String, String> getContentMap() {
        return contentMap;
    }

    public void setContentMap(Map<String, String> contentMap) {
        this.contentMap = contentMap;
    }
}
