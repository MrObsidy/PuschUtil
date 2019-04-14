/**
 * 
 *  PuschUtil
 *  Copyright (C) 2018-2019 MrObsidy
 *  
 *  
 *  This file is part of PuschUtil.
 *
 *  PuschUtil is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  PuschUtil is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with PuschUtil.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package alexander.puschutil.communication.puem;

import java.lang.reflect.InvocationTargetException;

import alexander.puschutil.communication.generic.SharedResource;
import alexander.puschutil.communication.puem.api.MethodWrapper;
import alexander.puschutil.security.KeyUtil;

public final class TickBus extends EventBus {
	private MethodWrapper requestTickerChange;
	private long key = 0L;
	
	TickBus(){
		//don't let anybody instantiate this class other than the EventBusManager
	}
	
	void tick(Object tickContent){
		this.relayEvent("TickBus", new SharedResource("TickContent" , tickContent));
	}
	
	long setTicker(MethodWrapper requestTickerChange) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		boolean success = false;
		
		if(key != 0L){
			success = (boolean) this.requestTickerChange.getMethod().invoke(this.requestTickerChange.getObject());
		} else {
			success = true;
		}
		
		if (success) {
			this.requestTickerChange = requestTickerChange;
			long key = KeyUtil.generateKey();
			this.key = key;
			return key;
		} else {
			return 0L;
		}
	}
	
	long getKey(){
		return this.key;
	}
}
