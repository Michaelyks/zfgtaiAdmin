package com.zfgt.orm;

import java.util.Date;



/**
 * Comments entity. @author MyEclipse Persistence Tools
 */

public class Comments  implements java.io.Serializable {


    // Fields    

     private Long id;//
     private String content;//评论内容
     private Date insertTime;//评论时间
     private String ip;//客户IP
     private Long usersId;//用户id
     private String type;//类型;0:web端; 1:android移动端; 2:IOS移动端;3:微信wap端
     private String name;//用户姓名;针对web端
     private String contact;//联系方式;手机或者邮箱;针对web端
     private Users users;//用户
 
     //临时字段,没有ORM映射
     private String typename;//类型(中文)
         
    // Constructors

    /** default constructor */
    public Comments() {
    }

	/** minimal constructor */
    public Comments(Date insertTime) {
        this.insertTime = insertTime;
    }
    
    /** full constructor */
    public Comments(String content, Date insertTime, String ip, Long usersId, String type) {
        this.content = content;
        this.insertTime = insertTime;
        this.ip = ip;
        this.usersId = usersId;
        this.type = type;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public Date getInsertTime() {
        return this.insertTime;
    }
    
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getIp() {
        return this.ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getUsersId() {
        return this.usersId;
    }
    
    public void setUsersId(Long usersId) {
        this.usersId = usersId;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getTypename() {
    	  switch(Integer.parseInt(type)){
    	  case 0:
    	   return "web端";
    	  case 1:
    	   return "android移动端";
    	  case 2:
    	   return "IOS移动端";
    	  case 3:
       	   return "WAP端";
    	  default:
    	   return "其它";
    	   
    	  }
    	 }

    	 public void setTypename(String typename) {
    	  this.typename = typename;
    	 }

		public Users getUsers() {
			return users;
		}

		public void setUsers(Users users) {
			this.users = users;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getContact() {
			return contact;
		}

		public void setContact(String contact) {
			this.contact = contact;
		}
		
   	 
}