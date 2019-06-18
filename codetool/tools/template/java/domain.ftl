package ${table.domainPackage};

import java.util.Date;
import java.io.Serializable;

public class ${table.domainName} implements Serializable{

	private static final long serialVersionUID = 1L;

<#list table.columnInfos as u>
 	/**
 	* ${u.columnComment}
 	*/
   	private ${u.javaDataType} ${u.javaFullName};
</#list>

 <#list table.columnInfos as u>

   	public void set${u.javaFirstUpCase}${u.javaOhterLowCase}(${u.javaDataType} ${u.javaFullName}){
   		this.${u.javaFullName} = ${u.javaFullName};
   	}

   	public ${u.javaDataType} get${u.javaFirstUpCase}${u.javaOhterLowCase}(){
		return ${u.javaFullName};
	}
 </#list>    
}

