package com.zfgt.common.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Modul;
import com.zfgt.orm.RolesRight;
import com.zfgt.orm.SystemConfig;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = -4522508376651310198L;
	protected static Logger log = Logger.getLogger(BaseAction.class);
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected String url;

	public BaseAction() {
		this.request = ServletActionContext.getRequest();
		this.response = ServletActionContext.getResponse();

	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public String getContextPath() {
		return getRequest().getContextPath();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public String goError(List<String> Error) throws IOException {
		getRequest().setAttribute("errors", Error);

		return "error";

	}

	/**
	 * 导出报表
	 * 
	 * @param list
	 * @param page
	 * @param name
	 */
	public void doIreport(List<JasperPrint> list, String name) {
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd_hh_mm_ss");
		// String time=sdf.format(new Date());
		String filePath = "";
		if (list != null) {
			filePath = excel(list, name);
			System.out.println(filePath);
			try {
				SystemConfig sys = (SystemConfig) request.getServletContext().getAttribute("systemConfig");
				String path = sys.getHttpUrl() + sys.getFileName() + "/file/" + name + ".xls";
				response.getWriter().write(path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			System.out.println("js为空");
		}
	}

	/*
	 * 以excel格式生成文件
	 * 
	 */
	public String excel(List<JasperPrint> list, String name) {
		String fileName = name + ".xls";
		// String path="/mnt/apache-tomcat-7.0.64_backend/webapps/Images/file/";
		SystemConfig sys = (SystemConfig) request.getServletContext().getAttribute("systemConfig");
		// String path="/Y/apache-tomcat-7.0.64_backend/webapps/Images/file/";
		String path = sys.getFileUrl() + "/file/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		File file2 = new File(path + fileName);
		JRExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, list);
		/* exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, page); */
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, file2);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		try {
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	/**
	 * 判断用户权限是否存在
	 * 
	 * @param modulName
	 * @param usersAdminId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected boolean isExistsQX(String modulName, long usersAdminId) {

		List<Modul> listModul = (List<Modul>) request.getServletContext().getAttribute("listModul");
		List<RolesRight> listRolesRight = (List<RolesRight>) request.getServletContext().getAttribute("listRolesRight");
		if (listModul != null) {
			Long id = null;
			for (Object object1 : listModul) {
				Modul modul = (Modul) object1;
				if (modulName.equals(modul.getModulName())) {// 判断该模块是否存在
					id = modul.getId();
				}
				;
			}
			Long rightId = null;
			if (listRolesRight != null && id != null) {
				for (Object object2 : listRolesRight) {
					RolesRight rolesRight = (RolesRight) object2;
					if (rolesRight.getUsersAdminId().longValue() == usersAdminId && rolesRight.getModulId().longValue() == id && "0".equals(rolesRight.getStatus())) {
						rightId = rolesRight.getId();
					}
				}
				if (!QwyUtil.isNullAndEmpty(rightId)) {
					return false;
				}
			} else {
				return true;
			}

		} else {
			return true;
		}

		return true;
	}

}
