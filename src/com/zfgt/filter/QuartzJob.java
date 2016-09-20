package com.zfgt.filter;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;

import com.zfgt.orm.SystemConfig;
import com.zfgt.thread.action.CleanAllMCoinThread;
import com.zfgt.thread.dao.ThreadDAO;

public class QuartzJob extends QuartzJobBean {

	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
	}
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		CleanAllMCoinThread cleanAllMCoin = (CleanAllMCoinThread) getBean("cleanAllMCoinThread");
		ThreadDAO threadDAO = (ThreadDAO)getBean("threadDAO");
		List<SystemConfig> list=(List<SystemConfig>) threadDAO.getStartCleanMcoin();
		if(list.get(0).getIsCleanMcoin().equals("1"))
		new Thread(cleanAllMCoin).start();

	}
}



