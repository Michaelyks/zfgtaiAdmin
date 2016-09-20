package com.zfgt.test.action;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.action.BaseAction;

@ParentPackage("struts-default")
@Namespace("/Product")
@Results({ @Result(name = "newFile", value = "/NewFile1.jsp")
})
public class DjjjAction extends  BaseAction{
	
	public String getJ(){
		System.out.println("newasdf");
		return "newFile";
	}
}
