package ${table.servicePackage+".impl"};

import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.simulation.common.page.Pagination;
import ${table.domainQualifiClassName};
import ${table.daoQualifiClassName};
import ${table.serviceQualifiClassName};
import ${table.voQualifiClassName};

@Transactional
@Service("${table.serviceName?uncap_first}")
public class ${table.serviceImplName} implements ${table.serviceName}{

	private final static Logger log= Logger.getLogger(${table.serviceImplName}.class);

	@Autowired
	private ${table.daoName} ${table.daoName?uncap_first};

	<#list table.columnInfos?sort_by("order") as being>
	<#if being.isPK>
	
	public ${table.domainName} selectByPrimaryKey(${being.javaDataType} ${being.javaFullName}){
	    return ${table.daoName?uncap_first}.selectByPrimaryKey(${being.javaFullName});
	}

    public void deleteByPrimaryKey(${being.javaDataType} ${being.javaFullName}){
    	this.${table.daoName?uncap_first}.deleteByPrimaryKey(${being.javaFullName}); 
    }
	</#if>
	</#list>

    public void insert(${table.domainName} model) {
    	this.${table.daoName?uncap_first}.insert(model); 
    }
    
    public void insertSelective(${table.domainName} model){
    	this.${table.daoName?uncap_first}.insertSelective(model); 
    }
    
    public void updateByPrimaryKeySelective(${table.domainName} model){
    	this.${table.daoName?uncap_first}.updateByPrimaryKeySelective(model); 
    }

    public void updateByPrimaryKey(${table.domainName} model) {
		this.${table.daoName?uncap_first}.updateByPrimaryKey(model);
    }
    
    public List<${table.domainName}> selectList(${table.domainName} ${table.domainName?uncap_first}){
    	return ${table.daoName?uncap_first}.selectList(${table.domainName?uncap_first});
    }
    
    public List<${table.domainName}> findAll() {
		return ${table.daoName?uncap_first}.findAll();
    }

    public void deleteAll() {
		this.${table.daoName?uncap_first}.deleteAll();
    }

    @Override
    public void deleteIds(String ids){
    	String [] idArr=ids.split(",");
    	if (idArr.length>1) {
			List<Integer> idsList=new ArrayList<Integer>();
			for (int i = 0; i < idArr.length; i++) {
				idsList.add(Integer.valueOf(idArr[i]));
			}
			this.${table.daoName?uncap_first}.deleteByIds(idsList);
		}else {
			this.${table.daoName?uncap_first}.deleteByPrimaryKey(Integer.valueOf(ids));
		}
    }

    @Override
    public Pagination findListByPage(int rows, int page,${table.voName} vo) {
	    Pagination pagination = new Pagination();
	    pagination.setPageNo(page); //当前页码
	    pagination.setPageSize(rows);  //每页显示多少行
	    List<${table.voName}>  list = this.${table.daoName?uncap_first}.findListByPage(vo,pagination);
	    pagination.setList(list);
	    return pagination;
    } 	
}

