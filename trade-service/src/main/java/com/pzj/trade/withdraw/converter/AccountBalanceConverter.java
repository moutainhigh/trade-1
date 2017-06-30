package com.pzj.trade.withdraw.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.exception.ServiceException;
import com.pzj.framework.toolkit.Check;
import com.pzj.settlement.base.entity.AccountBalance;
import com.pzj.settlement.base.entity.vo.AccountBalanceVo;
import com.pzj.settlement.base.service.IAccountService;
import com.pzj.trade.payment.common.AccountButtonEnum;
import com.pzj.trade.withdraw.exception.WithdrawException;

/**
 * 获取可退款的账户.
 * @author YRJ
 *
 */
@Component("accountBalanceConverter")
public class AccountBalanceConverter implements ObjectConverter<Long, Double, Result<AccountBalance>> {

	@Autowired
	private IAccountService accountService;

	@Override
	public Result<AccountBalance> convert(Long accountId, Double cashPostalMoney) {
		//1. 获取返利账户. 数据来源清结算系统.
		List<AccountBalance> accounts = getAccountBalanceByAccountId(accountId);
		if (accounts == null) {
			return new Result<AccountBalance>(43003, "获取资金账户失败");
		}

		//2. 过滤返利账户, 只有返利账户才可提现.
		AccountBalance rebateAccount = getRebateAccount(accounts);

		//3. 判断账户余额是否可提现.
		boolean withdrawable = compareMoney(rebateAccount.getFinalPeriod(), cashPostalMoney);
		if (!withdrawable) {
			throw new WithdrawException(10521, "资金账户余额不足");
		}

		return new Result<AccountBalance>(rebateAccount);
	}

	/**
	 * 生成获取账户余额列表参数.
	 * @param accountId
	 * @return
	 */
	private AccountBalanceVo generateAccountBalanceVo(Long accountId) {
		AccountBalanceVo account = new AccountBalanceVo();
		account.setAccountDictIds(AccountButtonEnum.getDictIds());
		account.setCustomerId(accountId);
		return account;
	}

	/**
	 * 获取账户余额列表.
	 * @param accountId
	 * @return
	 */
	private List<AccountBalance> getAccountBalanceByAccountId(Long accountId) {
		List<AccountBalance> accounts = null;
		try {
			AccountBalanceVo account = generateAccountBalanceVo(accountId);
			accounts = accountService.queryAccountBalance(account);
		} catch (Throwable e) {
			if (e instanceof ServiceException) {
				throw (ServiceException) e;
			}
			throw new WithdrawException(10522, "获取资金账户失败");
		}

		accounts = filterAccount(accounts);
		return accounts;
	}

	/**
	 * 过滤非法账户.
	 * @param accounts
	 */
	private List<AccountBalance> filterAccount(List<AccountBalance> accounts) {
		if (accounts == null) {
			throw new WithdrawException(10521, "获取资金账户失败");
		}

		List<AccountBalance> result = new ArrayList<AccountBalance>();
		for (AccountBalance account : accounts) {
			AccountButtonEnum button = AccountButtonEnum.getAccountButton(account.getAccountDictId());
			if (Check.NuNObject(button)) {
				continue;
			}
			result.add(account);
		}
		return result;
	}

	/**
	 * 获取返利账户.
	 * @param accounts
	 * @return
	 */
	private AccountBalance getRebateAccount(List<AccountBalance> accounts) {
		for (AccountBalance account : accounts) {
			if (account.getAccountDictId() == AccountButtonEnum.rebate.getValue()) {
				return account;
			}
		}
		throw new WithdrawException(10501, "返利账户不存在, 无法进行提现.");
	}

	/**
	 * 比较账户余额是否足以支付提现金额.
	 * @param finalPeriod
	 * @param cashPostalMoney
	 * @return
	 */
	private boolean compareMoney(double finalPeriod, double cashPostalMoney) {
		return finalPeriod >= cashPostalMoney;
	}
}
