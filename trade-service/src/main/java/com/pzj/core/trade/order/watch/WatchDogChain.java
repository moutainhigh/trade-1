package com.pzj.core.trade.order.watch;

import java.util.ArrayList;
import java.util.List;

import com.pzj.core.trade.order.engine.model.MerchModel;

/**
 * 狗链, 将狗串联为一条链. 不等同于拴狗的铁链.
 * 
 * @author YRJ
 *
 */
public class WatchDogChain {

	/**
	 * 一群狗.
	 */
	private List<WatchDog> dogs = new ArrayList<WatchDog>();

	public List<WatchDog> getDogs() {
		return dogs;
	}

	public void setDogs(List<WatchDog> dogs) {
		this.dogs = dogs;
	}

	public void watch(MerchModel merch) {
		int length = dogs.size();
		int index = 0;
		boolean lose = true;
		do {
			WatchDog dog = dogs.get(index);
			try {
				lose = dog.watch(merch);
			} catch (Throwable e) {

			}
		} while (((index++) < length) && lose);
	}
}
