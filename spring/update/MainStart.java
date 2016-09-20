package update;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MainStart {

	public static void main(String[] args) {
		System.out.println("1");
		ApplicationContext context=new ClassPathXmlApplicationContext(
				"beans_all.xml");
/*		ObjectDao objectDao=(ObjectDao) context.getBean("ObjectDao");
		String sql="insert SKJQANDGROUP(id,skjqId,skjqGroupId,insert_time,type,status) " +
					"SELECT newid(),id,skjqGroupId,getDate(),0,0  from SKJQS";
		objectDao.excuteBySql(sql);*/
		//String sql="alter table SKJQLog alter column skjqGroupId varchar(500) ";
		System.out.println("ok");
	
	}
}
