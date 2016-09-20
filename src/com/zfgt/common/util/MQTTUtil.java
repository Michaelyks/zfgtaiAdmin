package com.zfgt.common.util;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * MQTT工具类
 * @author 曾礼强
 *2015年4月7日 14:17:04
 */
public class MQTTUtil {
	/**
	 * 连接服务端
	 * @param type 类型
	 */
	public static MqttClient connect(String type) {
		try {
			//host为主机名，type为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存  
			MqttClient client=new MqttClient(PropertiesUtil.getProperties("MQTThost").toString(), type,	new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(false);
			options.setUserName(PropertiesUtil.getProperties("userName").toString());
			options.setPassword(PropertiesUtil.getProperties("passWord").toString().toCharArray());
			// 设置超时时间
			options.setConnectionTimeout(10);
			// 设置会话心跳时间
			options.setKeepAliveInterval(10);
	
				client.setCallback(new MqttCallback() {
	
					@Override
					public void connectionLost(Throwable cause) {
                        //连接丢失后，一般在这里面进行重连  
						System.out.println("connectionLost----------");  
					}
					/** 
				       * 消息连接丢失 
				       */  
					@Override
					public void deliveryComplete(IMqttDeliveryToken token) {
						   //publish后会执行到这里  
				        System.out.println("deliveryComplete---------"  
				                + token.isComplete());  
					}
					/** 
				       * 接收到消息的回调的方法 
				       */  
					@Override
					public void messageArrived(String topic, MqttMessage message)
							throws Exception {
				        System.out.println("messageArrived----------");  
				       
				       System.out.println("messageArrived:"+message.toString());
					}
				});
			
				client.connect(options);
				return client;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
