/**
* 版权声明：${codeGenConfig.developerConfig.company!} 版权所有 违者必究
* 日    期：${date!}
*/
package ${codeGenConfig.table.packageVar!}.dao;

import com.willow.platform.core.base.dao.BaseDao;
import ${codeGenConfig.table.packageVar!}.domain.${codeGenConfig.table.classVar!};
import ${codeGenConfig.table.packageVar!}.mapper.${codeGenConfig.table.classVar!}Mapper;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *   ${codeGenConfig.table.tableName!}持久层
 * </pre>
 *
 * @author ${codeGenConfig.developerConfig.developer!}
 * @version ${codeGenConfig.developerConfig.version!}
 */
@Repository
public class ${codeGenConfig.table.classVar!}Dao extends BaseDao<${codeGenConfig.table.classVar!}> {
    @Override
    public Class getMapperClass() {
    return ${codeGenConfig.table.classVar!}Mapper.class;
    }
}
