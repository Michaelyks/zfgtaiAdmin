package com.zfgt.thread.action;



public class TestThread {

	public static void main(String[] args) {
		System.out.println("开始读取Spring 所需 jar 包");
		System.out.println(System.getProperty("user.dir"));
		String path= System.getProperty("user.dir");
		path=path.substring(0,path.indexOf("wgtz"));
		System.out.println("000"+path);
		ClassLoaderUtil.loadJarPath(path+"/wgtz/WEB-INF/lib");
		System.out.println("ok");
		Test.updateDB();
	}
}
