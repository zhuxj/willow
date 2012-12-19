/**
* 版权声明：${codeGenConfig.developerConfig.company!} 版权所有 违者必究
* 日    期：${date!}
*/
package ${codeGenConfig.table.packageVar!}.domain;

import com.willow.platform.core.base.domian.BaseObject;

/**
 * <pre>
 *   ${codeGenConfig.table.tableName!}领域对象
 * </pre>
 * @author ${codeGenConfig.developerConfig.developer!}
 * @version ${codeGenConfig.developerConfig.version!}
 */
public class ${codeGenConfig.table.classVar!} extends BaseObject{
<#list tableClass.fieldColumns as fieldColumn>
    <#if !fieldColumn.isIncludeField>
    //${fieldColumn.propName!}
    private ${fieldColumn.jdbcType!} ${fieldColumn.javaProperty!};
    </#if>
</#list>

<#list tableClass.fieldColumns as fieldColumn>
    <#if  !fieldColumn.isIncludeField>
    public ${fieldColumn.jdbcType!} get${fieldColumn.gsJavaProperty!}() {
        return ${fieldColumn.javaProperty!};
    }
    public void set${fieldColumn.gsJavaProperty!}(${fieldColumn.jdbcType!} ${fieldColumn.javaProperty!}) {
        this.${fieldColumn.javaProperty!} = ${fieldColumn.javaProperty!};
    }
    </#if>
</#list>
}
