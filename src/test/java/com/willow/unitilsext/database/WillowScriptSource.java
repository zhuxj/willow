/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-12
 */
package com.willow.unitilsext.database;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.unitils.core.UnitilsException;
import org.unitils.dbmaintainer.script.Script;
import org.unitils.dbmaintainer.script.impl.DefaultScriptSource;
import org.unitils.util.PropertyUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class WillowScriptSource extends DefaultScriptSource {
    @Override
    protected List<Script> loadAllScripts() {
        List<String> scriptLocations = PropertyUtils.getStringList(PROPKEY_SCRIPT_LOCATIONS, configuration);
        List<Script> scripts = new ArrayList<Script>();
        for (String scriptLocation : scriptLocations) {
            getScriptsAt(scripts, scriptLocation, "");
        }
        return scripts;
    }

    /**
     * @param scripts          脚本文件
     * @param scriptRoot       脚本文件根目录
     * @param relativeLocation 相对路径
     */
    protected void getScriptsAt(List<Script> scripts, String scriptRoot, String relativeLocation) {
        Resource scriptDir = new ClassPathResource(scriptRoot + "/" + relativeLocation); //类路径下
        File currentLocation = null;
        try {
            currentLocation = scriptDir.getFile();
        } catch (IOException e) {
            throw new UnitilsException("File location " + scriptRoot + "/" + relativeLocation +
                    " defined in property " + PROPKEY_SCRIPT_LOCATIONS + " doesn't exist");
        }
        if (currentLocation.isFile() && isScriptFile(currentLocation)) {
            Script script = createScript(currentLocation, relativeLocation);
            scripts.add(script);
            return;
        }
        // recursively scan sub folders for script files
        if (currentLocation.isDirectory()) {
            for (File subLocation : currentLocation.listFiles()) {
                getScriptsAt(scripts, scriptRoot,
                        "".equals(relativeLocation) ? subLocation.getName() : relativeLocation + "/" + subLocation.getName());
            }
        }
    }
}
