package com.zfgt.admin.product.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.thread.action.AutoSendRatesThread;
import com.zfgt.thread.action.AutoShiftTohread;
import com.zfgt.thread.action.AutoStatsOperateThread;
import com.zfgt.thread.action.ChannelOperateThread;
import com.zfgt.thread.action.FinishCouponThread;
import com.zfgt.thread.action.FinishInterestCouponThread;
import com.zfgt.thread.action.IphoneOperateThread;
import com.zfgt.thread.action.LianLianCzThread;
import com.zfgt.thread.action.MeowCurrencyReportThread;
import com.zfgt.thread.action.SendCoinPurseRatesThread;
import com.zfgt.thread.action.SendFreshmanMessegeThread;
import com.zfgt.thread.action.SendInviteEarnThread;
import com.zfgt.thread.action.SendMcoinThread;
import com.zfgt.thread.action.SendProfitThread;
import com.zfgt.thread.action.SendVIPGradeThread;
import com.zfgt.thread.action.TxQueryThread;
import com.zfgt.thread.action.UpdateProductThread;
import com.zfgt.thread.action.UpdateUsersInfoThread;

/**
 * 后台管理--启动线程
 * 
 * @author qwy
 *
 * @createTime 2015-4-27下午3:51:34
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
// 发布产品页面
@Results({ @Result(name = "thread", value = "/Product/Admin/functionManager/function.jsp") })
public class StartThreadAction extends BaseAction {
	/**
	 * 结算理财产品;
	 */
	@Resource
	private UpdateProductThread updateProductThread;
	/**
	 * 扫描过期投资券;
	 */
	@Resource
	private FinishCouponThread finishCouponThread;
	/**
	 * 扫描过期投资券;
	 */
	@Resource
	private FinishInterestCouponThread finishInterestCouponThread;
	/**
	 * 发放收益;
	 */
	@Resource
	private SendProfitThread sendProfitThread;

	/**
	 * 发放邀请投资奖励
	 */
	@Resource
	private SendInviteEarnThread sendInviteEarnThread;

	@Resource
	private SendMcoinThread sendMcoinThread;

	@Resource
	private MeowCurrencyReportThread sendMeowCurrency;

	/**
	 * 提现查询接口;
	 */
	@Resource
	private TxQueryThread txQueryThread;
	/**
	 * 更新用户信息
	 */
	@Resource
	private UpdateUsersInfoThread updateUsersInfoThread;

	/**
	 * 自动计算收益线程
	 */
	@Resource
	private AutoSendRatesThread autoSendRatesThread;

	/**
	 * 自动发放收益
	 */
	@Resource
	private SendCoinPurseRatesThread coinPurseRatesThread;

	/**
	 * 自动转入
	 */
	@Resource
	private AutoShiftTohread autoShiftTohread;
	/**
	 * 发送新手投资到期短信
	 */
	@Resource
	private SendFreshmanMessegeThread sendFreshmanMessegeThread;
	/**
	 * 启动处理（连连）充值异步返回信息的线程
	 */
	@Resource
	private LianLianCzThread lianLianCzThread;

	/**
	 * 每日统计线程
	 */
	@Resource
	private AutoStatsOperateThread autoStatsOperateThread;

	/**
	 * 每日单个渠道数据统计线程（安卓）
	 */
	@Resource
	private ChannelOperateThread channelOperateThread;
	/**
	 * 每日单个渠道数据统计线程（安卓）
	 */
	@Resource
	private IphoneOperateThread iphoneOperateThread;

	/**
	 * 启动所有线程;
	 * 
	 * @return
	 */
	public String startAllThread() {
		String json = "";
		try {
			// 更新理财产品;包括:更新之后,结算常规的理财产品和新手理财产品;
			new Thread(updateProductThread).start();
			Thread.sleep(2000);
			// 扫描过期投资券;
			new Thread(finishCouponThread).start();
			Thread.sleep(1000);
			// 扫描过期加息券;
			new Thread(finishInterestCouponThread).start();
			Thread.sleep(1000);
			// 发放收益;
			new Thread(sendProfitThread).start();
			// 提现查询接口;
			new Thread(txQueryThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 启动更新理财产品和结算理财产品的线程
	 * 
	 * @return
	 */
	public String startUpdateProductThread() {
		String json = "";
		try {
			// 更新理财产品;包括:更新之后,结算常规的理财产品和新手理财产品;
			new Thread(updateProductThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送瞄币的线程
	 * 
	 * @return
	 */

	public String sendMcoinThread() {
		String json = "";
		try {
			// 更新理财产品;包括:更新之后,结算常规的理财产品和新手理财产品;
			new Thread(sendMcoinThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 邀请好友投资奖励
	 * 
	 * @return
	 */

	public String sendInviteEarnThread() {
		String json = "";
		try {
			new Thread(sendInviteEarnThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public String meowCurrencyReport() {
		String json = "";
		try {
			new Thread(sendMeowCurrency).start();
			json = QwyUtil.getJSONString("ok", "启动成功");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 启动发放收益线程
	 * 
	 * @return
	 */
	public String startSendProfitThread() {
		String json = "";
		try {
			// 发放收益;
			new Thread(sendProfitThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 启动扫描过期投资券线程
	 * 
	 * @return
	 */
	public String startFinishCouponThread() {
		String json = "";
		try {
			// 扫描过期投资券;
			new Thread(finishCouponThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 启动扫描过期投资券线程
	 * 
	 * @return
	 */
	public String startFinishInterestCouponThread() {
		String json = "";
		try {
			// 扫描过期投资券;
			new Thread(finishInterestCouponThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 启动查询接口记录的线程;
	 * 
	 * @return
	 */
	public String startTxQueryThread() {
		String json = "";
		try {
			// 提现查询接口;
			new Thread(txQueryThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 启动更新用户信息的线程
	 * 
	 * @return
	 */
	public String startUpdateUsersInfoThread() {
		String json = "";
		try {
			// 更新用户信息；包括用户性别，年龄，生日
			new Thread(updateUsersInfoThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 自动计算收益线程
	 * 
	 * @return
	 */
	public String startAutoSendRatesThread() {
		String json = "";
		try {
			// 更新用户信息；包括用户性别，年龄，生日
			new Thread(autoSendRatesThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 启动 自动发放收益的线程
	 * 
	 * @return
	 */
	public String startSendCoinPurseRatesThread() {
		String json = "";
		try {
			// 更新用户信息；包括用户性别，年龄，生日
			new Thread(coinPurseRatesThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 自动转入
	 * 
	 * @return
	 */
	public String startAutoShiftTohread() {
		String json = "";
		try {
			// 更新用户信息；包括用户性别，年龄，生日
			new Thread(autoShiftTohread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送新手投资到期短信
	 * 
	 * @return
	 */
	public String startSendFreshmanMessegeThread() {
		String json = "";
		try {
			// 发动短信
			new Thread(sendFreshmanMessegeThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询连连支付充值返回信息
	 * 
	 * @return
	 */
	public String startLianLianCzThread() {
		String json = "";
		try {
			// 更新用户信息；包括用户性别，年龄，生日
			new Thread(lianLianCzThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String startCurrEveryDayStatsOperate() {
		String json = "";
		try {
			autoStatsOperateThread.setInDayStr("today");
			// 更新用户信息；包括用户性别，年龄，生日
			new Thread(autoStatsOperateThread).start();
			json = QwyUtil.getJSONString("ok", "启动更新成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动更新成功");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String startEveryDayStatsOperate() {
		String json = "";
		try {
			autoStatsOperateThread.setInDayStr("all");
			// 更新用户信息；包括用户性别，年龄，生日
			new Thread(autoStatsOperateThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 启动更新单个渠道数据统计线程（安卓）
	 * 
	 * @return
	 */
	public String startEveryDayChannelOperate() {
		String json = "";
		try {
			// 更新单个渠道数据统计线程
			new Thread(channelOperateThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 启动更新单个渠道数据统计线程（IOS）
	 * 
	 * @return
	 */
	public String startEveryDayIphoneOperate() {
		String json = "";
		try {
			// 更新单个渠道数据统计线程(IOS)
			new Thread(iphoneOperateThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
