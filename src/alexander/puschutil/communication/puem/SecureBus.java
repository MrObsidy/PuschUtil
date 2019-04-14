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
import java.util.ArrayList;

import alexander.puschutil.communication.puem.api.MethodWrapper;
import alexander.puschutil.security.KeyUtil;

public final class SecureBus extends EventBus {
	
	private final ArrayList<MethodWrapper> requestAdditionOfEventPusher = new ArrayList<MethodWrapper>();
	private final ArrayList<Long> keys = new ArrayList<Long>();
	
	SecureBus(){
		//don't let anybody instantiate this class (except the EventBusManager)
	}
	
	ArrayList<Long> getKeys(){
		return this.keys;
	}
	
	long addEventPusher(MethodWrapper permissionAsker) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		boolean success = false;
		
		if (keys.size() != 0){
			
			ArrayList<Boolean> stuff = new ArrayList<Boolean>();
			
			for(MethodWrapper wrapper : this.requestAdditionOfEventPusher){
				stuff.add((Boolean) wrapper.getMethod().invoke(wrapper.getObject()));
			}
			
			if (!stuff.contains(false)) {
				success = true;
			}
			
		} else {
			success = true;
		}
		
		if(success){
			long key = KeyUtil.generateKey();
			keys.add(key);
			requestAdditionOfEventPusher.add(permissionAsker);
			return key;
		} else {
			return 0L;
		}
	}
}
