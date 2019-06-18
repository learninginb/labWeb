<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${table.daoQualifiClassName}">
	<!-- Result Map -->
	<resultMap id="BaseResultMap" type="${table.domainQualifiClassName}">
		<#list table.columnInfos?sort_by("order") as u>
		<#if u.isPK>
		<id column="${u.columnName?lower_case}" property="${u.javaFullName}" jdbcType="${u.myBatisDataType}" />
		<#else>
		<result column="${u.columnName?lower_case}" property="${u.javaFullName}" jdbcType="${u.myBatisDataType}" />
		</#if>
		</#list>
	</resultMap>
    <resultMap id="BaseResultVoMap" extends="BaseResultMap" type="${table.voQualifiClassName}">

    </resultMap>

	<sql id="Base_Column_List">
		<#list table.columnInfos?sort_by("order") as u><#if u.isPK>${u.columnName?lower_case}<#else>,${u.columnName?lower_case}</#if></#list>
	</sql>

	 <!-- 分页数据 -->    
	<select id="findListByPage" resultMap="BaseResultVoMap" parameterType="${table.voQualifiClassName}">
  		select 
  		<include refid="Base_Column_List" />
  	      from ${table.tableName} where 1=1 
  	      <#list table.columnInfos?sort_by("order") as u>
  	        <#if "${u.myBatisDataType}"=="VARCHAR">
  	        <if test="vo.${u.javaFullName} != null and '' != vo.${u.javaFullName} ">and ${u.columnName?lower_case} = ${"#\{"} vo.${u.javaFullName} }</if>
		  	<#else>
		  	<if test="vo.${u.javaFullName} != null ">and ${u.columnName?lower_case} = ${"#\{"} vo.${u.javaFullName} }</if>
		  	</#if>
 		  </#list>
  	</select>      
  
	<!-- 根据主键获取对象 -->  
	<#list table.columnInfos?sort_by("order") as u>
	<#if u.isPK>
	<select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.${u.javaDataType}" >
	  select 
	  <include refid="Base_Column_List" />
	  from ${table.tableName}
	  where ${u.columnName?lower_case} = #${"{"+u.javaFullName},jdbcType=${u.myBatisDataType?upper_case+"}"}
	</select>
	
	<!-- 获取表所有数据 -->    
    <select id="findAll" resultMap="BaseResultMap" parameterType="java.lang.${u.javaDataType}" >
      select
      <include refid="Base_Column_List" />
      from ${table.tableName}
    </select>

	<!-- 根据主键删除 -->
	<delete id="deleteByPrimaryKey" parameterType="java.lang.${u.javaDataType}" >
	  delete from ${table.tableName}
	  where ${u.columnName?lower_case} = #${"{"+u.javaFullName},jdbcType=${u.myBatisDataType?upper_case+"}"}
	</delete>
	</#if>  
	</#list>

	<!-- 保存对象 -->
	<insert id="insert" parameterType="${table.domainQualifiClassName}" useGeneratedKeys="true" keyProperty="id">
       insert into ${table.tableName} (
		<#list table.columnInfos?sort_by("order") as u><#if u.isPK>${u.columnName?lower_case}<#else>,${u.columnName?lower_case}</#if></#list>)values ( 
		<#list table.columnInfos?sort_by("order") as u><#if u.isPK>#${"{"+u.javaFullName},jdbcType=${u.myBatisDataType?upper_case+"}"}<#else>,#${"{"+u.javaFullName},jdbcType=${u.myBatisDataType?upper_case+"}"}</#if></#list>)
	</insert>

	<!-- 根据对象字段保存 -->
	<insert id="insertSelective" parameterType="${table.domainQualifiClassName}" useGeneratedKeys="true" keyProperty="id" >
	   insert into ${table.tableName}
 		<trim prefix="(" suffix=")" suffixOverrides="," >
		<#list table.columnInfos?sort_by("order") as u>
	  		<if test="${u.javaFullName} != null ">${u.columnName?lower_case},</if>
		</#list>
 		</trim>
 		<trim prefix="values (" suffix=")" suffixOverrides="," >
		<#list table.columnInfos?sort_by("order") as u>
	  		<if test="${u.javaFullName} != null ">#${"{"+u.javaFullName},jdbcType=${u.myBatisDataType?upper_case+"}"},</if>
		</#list>
 		</trim>
	</insert>
	
	<!-- 更新记录 -->
	<update id="updateByPrimaryKeySelective" parameterType="${table.domainQualifiClassName}" >
	   update ${table.tableName}
		<set >
	    <#list table.columnInfos?sort_by("order") as u>
			<if test="${u.javaFullName} != null ">
		  	${u.columnName?lower_case} = #${"{"+u.javaFullName},jdbcType=${u.myBatisDataType?upper_case+"}"},
			</if>
		</#list>
	    </set>
	 <#list table.columnInfos?sort_by("order") as u>
	<#if u.isPK>
	   where ${u.columnName?lower_case} = #${"{"+u.javaFullName},jdbcType=${u.myBatisDataType?upper_case+"}"}
	</#if>
	</#list>
	</update>

	 <!-- 根据对象主键更新 -->    
  	<update id="updateByPrimaryKey" parameterType="${table.domainQualifiClassName}" >
       update ${table.tableName}
       set 
	      <#list table.columnInfos?sort_by("order") as u>
	      ${u.columnName?lower_case} = #${"{"+u.javaFullName},jdbcType=${u.myBatisDataType?upper_case+"}"}<#if u_has_next>,</#if>
	      </#list>
          <#list table.columnInfos?sort_by("order") as u>
	   <#if u.isPK>
	   where ${u.columnName?lower_case} = #${"{"+u.javaFullName},jdbcType=${u.myBatisDataType?upper_case+"}"}
	   </#if>
	</#list>
  	</update>
	
	<!--  查找记录-->
	<select id="selectList" parameterType="${table.domainQualifiClassName}" resultMap="BaseResultMap">
		select <include refid="Base_Column_List"/>
		from ${table.tableName} where 1=1
		<#list table.columnInfos?sort_by("order") as u>
		<#if "${u.myBatisDataType}"=="VARCHAR">
			<if test="${u.javaFullName} != null and '' != ${u.javaFullName} ">
			AND  ${u.columnName?lower_case} = #${"{"+u.javaFullName},jdbcType=${u.myBatisDataType?upper_case+"}"}
			</if>
		<#else>
			<if test="${u.javaFullName} != null ">
			AND  ${u.columnName?lower_case} = #${"{"+u.javaFullName},jdbcType=${u.myBatisDataType?upper_case+"}"}
			</if>
		</#if>
		</#list>
	</select>
	
	<!--批量删除-->
	<delete id="deleteByIds" >
	   delete from ${table.tableName}
	   <#list table.columnInfos?sort_by("order") as u>
	   <#if u.isPK>
	   where ${u.columnName?lower_case} in
	      <foreach collection="ids" item="id" index="index"
             open="(" close=")" separator=",">
              #${"{id"},jdbcType=${u.myBatisDataType?upper_case+"}"}
          </foreach>
	   </#if>
	   </#list>
	</delete>
	
</mapper>   
