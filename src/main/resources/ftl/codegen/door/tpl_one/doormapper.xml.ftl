<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${codeGenConfig.table.packageVar!}.mapper.${codeGenConfig.table.classVar!}Mapper">
<resultMap id="${codeGenConfig.table.classVar!}Map" type="${codeGenConfig.table.packageVar!}.domain.${codeGenConfig.table.classVar!}">
<#list tableClass.fieldColumns as fieldColumn>
    <!--${fieldColumn.propName!}-->
    <result column="${fieldColumn.columnName!}" property="${fieldColumn.javaProperty!}"/>
</#list>
</resultMap>
    <!--列信息-->
    <sql id="column_list">
        <#list tableClass.fieldColumns as fieldColumn>${fieldColumn.columnName!}<#if fieldColumn_has_next>,</#if></#list>
    </sql>
    <!--criteria的动态条件-->
    <sql id="criteria_filters">
        <where>
            <if test="criteria.valid">
                <trim prefix="(" suffix=")" prefixOverrides="and">
                    <foreach collection="criteria.criteria" item="criterion">
                        <choose>
                            <when test="criterion.noValue">
                                and ${r"${criterion.condition}"}
                            </when>
                            <when test="criterion.singleValue">
                                and ${r"${criterion.condition} #{criterion.value}"}
                            </when>
                            <when test="criterion.betweenValue">
                                and ${r"${criterion.condition} #{criterion.value} and #{criterion.secondValue}"}
                            </when>
                            <when test="criterion.listValue">
                                and ${r"${criterion.condition}"}
                                <foreach collection="criterion.value" item="listItem" open="(" close=")"
                                         separator=",">
                                ${r"#{listItem}"}
                                </foreach>
                            </when>
                        </choose>
                    </foreach>
                </trim>
            </if>
        </where>
    </sql>
    <!--动态条件-->
    <sql id="where_filters">
        <where>
        <#list tableClass.fieldColumns as fieldColumn>
            <if test="${fieldColumn.javaProperty!} !=null and ${fieldColumn.javaProperty!} !=''">
                and ${fieldColumn.columnName!}=${r"#{"}${fieldColumn.javaProperty!}${r"}"}
            </if>
        </#list>
        </where>
    </sql>
    <!--保存-->
    <insert id="save" parameterType="${codeGenConfig.table.packageVar!}.domain.${codeGenConfig.table.classVar!}">
        INSERT INTO ${codeGenConfig.table.tableCode!}(<include refid="column_list"/>)
        VALUES (<#list tableClass.fieldColumns as fieldColumn>${r"#{"}${fieldColumn.javaProperty!}${r"}"}<#if fieldColumn_has_next>,</#if></#list>);
    </insert>

    <!--根据主键查询对象数据-->
    <select id="selectByObjId" parameterType="string" resultMap="${codeGenConfig.table.classVar!}Map">
        select
        <include refid="column_list"/>
        from ${codeGenConfig.table.tableCode!}
        where obj_id=${r"#{objId}"}
    </select>

    <!--根据主键删除-->
    <delete id="deleteByObjId" parameterType="string">
        delete from ${codeGenConfig.table.tableCode!} where obj_id=${r"#{objId}"}
    </delete>

    <!--根据条件删除-->
    <delete id="deleteByParam" parameterType="map">
        delete from ${codeGenConfig.table.tableCode!}
        <include refid="where_filters"/>
    </delete>

    <!--更新-->
    <update id="update" parameterType="${codeGenConfig.table.packageVar!}.domain.${codeGenConfig.table.classVar!}">
        update ${codeGenConfig.table.tableCode!}
        <set>
        <#list tableClass.fieldColumns as fieldColumn>
            ${fieldColumn.columnName!}=${r"#{"}${fieldColumn.javaProperty!}${r"}"}<#if fieldColumn_has_next>,</#if>
        </#list>
        </set>
        where obj_id=${r"#{objId}"}
    </update>

    <!--根据主键ID更新不为空的字段-->
    <update id="updateByIdSelective" parameterType="${codeGenConfig.table.packageVar!}.domain.${codeGenConfig.table.classVar!}">
        update ${codeGenConfig.table.tableCode!}
        <set>
        <#list tableClass.fieldColumns as fieldColumn>
            <if test="${fieldColumn.javaProperty!} !=null">
                ${fieldColumn.columnName!}=${r"#{"}${fieldColumn.javaProperty!}${r"}"},
            </if>
        </#list>
        </set>
        where obj_id=${r"#{objId}"}
    </update>

    <!--根据条件查询列表-->
    <select id="queryList" parameterType="map" resultMap="${codeGenConfig.table.classVar!}Map">
        select
        <include refid="column_list"/>
        from ${codeGenConfig.table.tableCode!}
        <include refid="where_filters"/>
        <if test="sortFieldName!=null and sortFieldName!=''">
            ORDER BY
            ${r"${sortFieldName}"}
            <if test="sortType!=null and sortType!=''">
            ${r"${sortType}"}
            </if>
        </if>
        <if test="pageFirst!=null">
            limit ${r"#{pageFirst},#{pageSize}"}
        </if>
    </select>

    <!--根据条件查询数量-->
    <select id="countList" parameterType="map" resultType="int">
        select count(*) from ${codeGenConfig.table.tableCode!}
        <include refid="where_filters"/>
    </select>


    <!--根据条件查询唯一记录-->
    <select id="selectByCondition" parameterType="map" resultMap="${codeGenConfig.table.classVar!}Map">
        select
        <include refid="column_list"/>
        from ${codeGenConfig.table.tableCode!}
        <include refid="where_filters"/>
    </select>


    <!--根据Criteria方式查询列表-->
    <select id="queryListByCriteria" parameterType="map" resultMap="${codeGenConfig.table.classVar!}Map">
        select
        <include refid="column_list"/>
        from ${codeGenConfig.table.tableCode!}
        <include refid="criteria_filters"/>
        <if test="sortFieldName!=null and sortFieldName!=''">
            ORDER BY
            ${r"${sortFieldName}"}
            <if test="sortType!=null and sortType!=''">
            ${r"${sortType}"}
            </if>
        </if>
        <if test="pageFirst!=null">
            limit ${r"#{pageFirst},#{pageSize}"}
        </if>
    </select>

    <!--根据Criteria方式查询数量-->
    <select id="countListByCriteria" parameterType="map" resultType="int">
        select count(*) from ${codeGenConfig.table.tableCode!}
        <include refid="criteria_filters"/>
    </select>


</mapper>