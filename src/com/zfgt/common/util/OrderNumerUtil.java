package com.zfgt.common.util;

import org.joda.time.DateTime;


/**生成流水号工具
 * @author 传虎欧巴
 * @createTime 2015-11-17下午4:42:32
 */
public class OrderNumerUtil {

    private static int num = (int) (Math.random() * 100);

    public static String generateRequestId() {
        int i = (int) (Math.random() * 100);
        StringBuilder sb = new StringBuilder();
        sb.append(DateTime.now().toDate().getTime() - DateTime.parse("2015-10-15").toDate().getTime()).append(String.format("%03d", num)).append(String.format("%03d", i));
        num++;
        if (num > 999) {
            num = (int) (Math.random() * 100);
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
    	System.out.println(generateRequestId());
	}


}
