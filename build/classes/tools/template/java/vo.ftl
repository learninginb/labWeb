package ${table.voPackage};

import ${table.domainQualifiClassName};

public class ${table.voName} extends ${table.domainName} {

	private static final long serialVersionUID = 1L;
	
<#list table.columnInfos as u>
    public static final String ALIAS_${u.columnName?upper_case} = "${u.columnComment}";
</#list>
	
}
