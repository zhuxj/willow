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
    //${fieldColumn.propName!}
    <#if fieldColumn.columnType=="VARCHAR" || fieldColumn.columnType=="CHAR">
    private String ${fieldColumn.javaProperty!};
    </#if>
    <#if fieldColumn.columnType=="INT" || fieldColumn.columnType=="INT UNSIGNED">
    private Integer ${fieldColumn.javaProperty!};
    </#if>
</#list>

<#list tableClass.fieldColumns as fieldColumn>
    <#if fieldColumn.columnType=="VARCHAR"  || fieldColumn.columnType=="CHAR">
    public String get${fieldColumn.gsJavaProperty!}() {
    return ${fieldColumn.javaProperty!};
    }
    public void set${fieldColumn.gsJavaProperty!}(String ${fieldColumn.javaProperty!}) {
    this.${fieldColumn.javaProperty!} = ${fieldColumn.javaProperty!};
    }
    </#if>
    <#if fieldColumn.columnType=="INT" || fieldColumn.columnType=="INT UNSIGNED">
    public Integer get${fieldColumn.gsJavaProperty!}() {
    return ${fieldColumn.javaProperty!};
    }
    public void set${fieldColumn.gsJavaProperty!}(Integer ${fieldColumn.javaProperty!}) {
    this.${fieldColumn.javaProperty!} = ${fieldColumn.javaProperty!};
    }
    </#if>
</#list>
}
