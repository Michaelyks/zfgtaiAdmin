package com.zfgt.thread.action;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
  
/**  
 * @author obullxl  
 *  
 * email: obullxl@163.com  MSN: obullxl@hotmail.com  QQ: 303630027  
 *  
 * Blog: http://obullxl.iteye.com  
 */  
public final class ClassLoaderUtil {   
    /** URLClassLoader锟斤拷addURL锟斤拷锟斤拷 */  
    private static Method addURL = initAddMethod();   
    private Logger log = Logger.getLogger(ClassLoaderUtil.class);
    /** 锟斤拷始锟斤拷锟斤拷锟斤拷 */  
    private static final Method initAddMethod() {   
        try {   
            Method add = URLClassLoader.class  
                .getDeclaredMethod("addURL", new Class[] { URL.class });   
            add.setAccessible(true);   
            return add;   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
        return null;   
    }   
  
    private static URLClassLoader system = (URLClassLoader) ClassLoader.getSystemClassLoader();   
  
    /**  
     * 循锟斤拷锟斤拷锟斤拷目录锟斤拷锟揭筹拷锟斤拷锟叫碉拷JAR锟斤拷  
     */  
    private static final void loopFiles(File file, List<File> files) {   
        if (file.isDirectory()) {   
            File[] tmps = file.listFiles();   
            for (File tmp : tmps) {   
                loopFiles(tmp, files);   
            }   
        } else {   
            if (file.getAbsolutePath().endsWith(".jar") || file.getAbsolutePath().endsWith(".zip")) {   
            	System.out.println(file.getName());
            	files.add(file);   
            }   
        }   
    }   
  
    /**  
     * <pre>  
     * 锟斤拷锟斤拷JAR锟侥硷拷  
     * </pre>  
     *  
     * @param file  
     */  
    public static final void loadJarFile(File file) {   
        try {   
            addURL.invoke(system, new Object[] { file.toURI().toURL() });   
            System.out.println("锟斤拷锟斤拷JAR锟斤拷" + file.getAbsolutePath());   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
  
    /**  
     * <pre>  
     * 锟斤拷一锟斤拷目录锟斤拷锟斤拷锟斤拷锟斤拷JAR锟侥硷拷  
     * </pre>  
     *  
     * @param path  
     */  
    public static final void loadJarPath(String path) {   
        List<File> files = new ArrayList<File>();   
        File lib = new File(path);
        System.out.println(lib.isDirectory());
        System.out.println(lib.getAbsolutePath());
        loopFiles(lib, files);   
        for (File file : files) {  
        	System.out.println(file.getName());
            loadJarFile(file);   
        }   
    }   
}  
