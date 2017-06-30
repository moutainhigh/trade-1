/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.model.SeatsModel;
import com.pzj.framework.toolkit.Check;

/**
 * 计算座位
 * @author Administrator
 * @version $Id: SeatsAssemble.java, v 0.1 2017年3月21日 上午10:49:53 Administrator Exp $
 */
@Component
public class SeatsAssemble {
	public SeatsModel computeSeatsOnCreateBook(List<Long> newSeats) {
		SeatsModel seats = new SeatsModel();
		seats.setNeedSeats(newSeats);
		return seats;
	}

	public SeatsModel computeSeatsOnUpdateBook(List<Long> oldSeats, List<Long> newSeats) {
		return computeSeats(oldSeats, newSeats, true);
	}

	public SeatsModel computeSeatsOnCreatePreOrder(List<Long> oldSeats, List<Long> newSeats) {
		return computeSeats(oldSeats, newSeats, false);
	}

	public SeatsModel computeSeatsOnUpdatePreOrder(List<Long> bookSeats, List<Long> preOrderSeats,
			List<Long> newSeats) {
		SeatsModel seats = new SeatsModel();
		seats.setNeedSeats(getNeedSeats(bookSeats, preOrderSeats, newSeats));
		seats.setReleaseSeats(getReleaseSeats(bookSeats, preOrderSeats, newSeats));
		return seats;
	}

	public SeatsModel computeSeatsOnCancelBook(List<Long> preOrderSeats, List<Long> newSeats) {
		SeatsModel seats = new SeatsModel();
		seats.setReleaseSeats(getUnionSet(newSeats, preOrderSeats));
		return seats;

	}

	public SeatsModel computeSeatsOnCancelPreOrder(List<Long> bookSeats, List<Long> newSeats) {
		SeatsModel seats = new SeatsModel();
		seats.setReleaseSeats(getDifferenceSet(bookSeats, newSeats));
		return seats;
	}

	public List<Long> getUnionSet(List<Long> list1, List<Long> list2) {
		if (Check.NuNCollections(list1)) {
			return list2;
		}
		if (Check.NuNCollections(list2)) {
			return list1;
		}
		Set<Long> result = new HashSet<Long>(list1);
		for (Long id : list2) {
			result.add(id);
		}
		return new ArrayList<Long>(result);

	}

	/**
	 * 计算存在与第二个却不存在于第一个集合中的对象
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public List<Long> getDifferenceSet(List<Long> list1, List<Long> list2) {
		List<Long> diff = new ArrayList<Long>();
		if (Check.NuNCollections(list1)) {
			list1 = new ArrayList<Long>();
		}
		if (Check.NuNCollections(list2)) {
			list2 = new ArrayList<Long>();
		}
		Set<Long> olds = new HashSet<Long>(list1);
		Set<Long> news = new HashSet<Long>(list2);
		for (Long seat : news) {
			if (!olds.contains(seat)) {
				diff.add(seat);
			}
		}
		return diff;
	}

	public SeatsModel computeSeatsOnCreateOrder(List<Long> bookSeats, List<Long> preOrderSeats, List<Long> newSeats) {
		List<Long> oldSeats = new ArrayList<Long>();
		oldSeats.addAll(bookSeats);
		oldSeats.addAll(preOrderSeats);
		return computeSeats(oldSeats, newSeats, true);
	}

	/**
	 * 
	 * @param bookSeats
	 * @param preOrderSeats
	 * @param newSeats
	 * @return
	 */
	private List<Long> getReleaseSeats(List<Long> bookSeats, List<Long> preOrderSeats, List<Long> newSeats) {
		List<Long> oldSeats = new ArrayList<Long>();
		oldSeats = getDifferenceSet(bookSeats, preOrderSeats);
		SeatsModel needSeat = computeSeats(oldSeats, newSeats, true);//获取占的
		return needSeat.getReleaseSeats();
	}

	private List<Long> getNeedSeats(List<Long> bookSeats, List<Long> preOrderSeats, List<Long> newSeats) {
		List<Long> oldSeats = new ArrayList<Long>();
		oldSeats.addAll(bookSeats);
		oldSeats.addAll(preOrderSeats);
		SeatsModel needSeat = computeSeats(oldSeats, newSeats, false);//获取占的
		return needSeat.getNeedSeats();
	}

	/**
	 * 筛选需要占的座位和需要释放的座位
	 * 
	 * @param firstSeats 一般为预约单的座位
	 * @param secondSeats 前置订单的座位
	 * @param newSeats	新订单的座位
	 * @param type
	 * @return
	 */
	private SeatsModel computeSeats(List<Long> oldSeats, List<Long> newSeats, boolean isReleaseOld) {
		SeatsModel seats = new SeatsModel();
		if (Check.NuNCollections(newSeats)) {
			newSeats = new ArrayList<Long>();
		}
		if (Check.NuNCollections(oldSeats)) {
			oldSeats = new ArrayList<Long>();
		}
		Set<Long> olds = new HashSet<Long>(oldSeats);
		Set<Long> news = new HashSet<Long>(newSeats);
		for (Long seat : news) {
			if (!olds.contains(seat)) {
				seats.getNeedSeats().add(seat);
			}
		}
		if (isReleaseOld) {
			for (Long seat : olds) {
				if (!news.contains(seat)) {
					seats.getReleaseSeats().add(seat);
				}
			}
		}
		return seats;
	}

}
