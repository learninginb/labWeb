package com.simulation.vo.tree;

import java.util.List;
import java.util.Map;


public class TreeNode {
	
	private String id;
	
	private String text;// 树节点名称
	
	private String icon;// 前面的小图标
	
	private Boolean isParent = true ;//是否含有子节点，false显示叶子
	
	private Boolean checked = false;// 是否勾选状态
	
	private Map<String, Object> attributes;// 其他参数
	
	private List<TreeNode> children;// 子节点
	
	private String state = "open";// 是否展开(open,closed)
	
	private boolean open = false; //是否展开
	
	private String pId; //父节点

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
}