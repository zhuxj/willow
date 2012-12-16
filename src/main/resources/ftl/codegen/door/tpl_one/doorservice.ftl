/**
* 版权声明：${codeGenConfig.developerConfig.company!} 版权所有 违者必究
* 日    期：${date!}
*/
package ${codeGenConfig.table.packageVar!}.service;

import com.willow.platform.core.base.dao.BaseDao;
import com.willow.platform.core.base.service.BaseService;
import ${codeGenConfig.table.packageVar!}.dao.${codeGenConfig.table.classVar!}Dao;
import ${codeGenConfig.table.packageVar!}.domain.${codeGenConfig.table.classVar!};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
*
<pre>
 * ${codeGenConfig.table.tableName!}业务类
 * </pre>
 *
 * @author ${codeGenConfig.developerConfig.developer!}
 * @version ${codeGenConfig.developerConfig.version!}
 */
@Service
public class ${codeGenConfig.table.classVar!}Service extends BaseService<${codeGenConfig.table.classVar!}> {
    @Autowired
    private ${codeGenConfig.table.classVar!}Dao ${codeGenConfig.table.classVariable!}Dao;
    @Override
    public BaseDao<${codeGenConfig.table.classVar!}> getDao() {
        return ${codeGenConfig.table.classVariable!}Dao;
    }

    public void set${codeGenConfig.table.classVar!}Dao(${codeGenConfig.table.classVar!}Dao ${codeGenConfig.table.classVariable!}Dao) {
        this.${codeGenConfig.table.classVariable!}Dao = ${codeGenConfig.table.classVariable!}Dao;
    }
}
