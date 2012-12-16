/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-12
 */
package com.willow.unitilsext.database;

import org.unitils.dbmaintainer.script.ScriptContentHandle;
import org.unitils.dbmaintainer.script.ScriptParser;
import org.unitils.dbmaintainer.script.impl.DefaultScriptRunner;
import org.unitils.util.PropertyUtils;

import java.io.Reader;
import static org.unitils.dbmaintainer.util.DatabaseModuleConfigUtils.PROPKEY_DATABASE_DIALECT;
import static org.unitils.core.util.ConfigUtils.getInstanceOf;
import static org.unitils.thirdparty.org.apache.commons.io.IOUtils.closeQuietly;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class WillowScriptRunner extends DefaultScriptRunner {
    /**
     * Executes the given script.
     * <p/>
     * All statements should be separated with a semicolon (;). The last statement will be
     * added even if it does not end with a semicolon.
     *
     * @param scriptContentHandle The script as a string, not null
     */
    public void execute(ScriptContentHandle scriptContentHandle) {

        Reader scriptContentReader = null;
        try {
            // get content stream
            scriptContentReader = scriptContentHandle.openScriptContentReader();

            // create a parser
            ScriptParser scriptParser = createScriptParser();
            scriptParser.init(configuration, scriptContentReader);

            // parse and execute the statements
            String statement;
            while ((statement = scriptParser.getNextStatement()) != null) {
                sqlHandler.executeUpdateAndCommit(statement);
            }
        } finally {
            closeQuietly(scriptContentReader);
        }
    }


    /**
     * Creates a script parser.
     *
     * @return The parser, not null
     */
    protected ScriptParser createScriptParser() {
        String databaseDialect = PropertyUtils.getString(PROPKEY_DATABASE_DIALECT, configuration);
        return getInstanceOf(ScriptParser.class, configuration, databaseDialect);
    }
}
