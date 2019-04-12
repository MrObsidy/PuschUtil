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
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import alexander.puschutil.communication.generic.SharedResource;
import alexander.puschutil.communication.puem.api.MethodWrapper;

/**
 * 
 * An Event Bus. Since it's functionality is internal (the real work is done by
 * the EventBusManager), I'll provide little documentation.
 * 
 * @author Alexander Pusch
 *
 */
public class EventBus {
	private final ArrayList<MethodWrapper> CALLBACKS;
	
	EventBus(){
		CALLBACKS = new ArrayList<MethodWrapper>();
	}
	
	void registerCallbackFunction(MethodWrapper map){
		CALLBACKS.add(map);
	}
	
	void removeCallbackFunction(MethodWrapper map){
		CALLBACKS.remove(map);
	}
	
	void relayEvent(String sender, SharedResource contents){
		for (MethodWrapper map : CALLBACKS){
			if (Modifier.isStatic(map.getMethod().getModifiers())) {
				try {
					map.getMethod().invoke(null, sender, contents);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} else {
				try {
					map.getMethod().invoke(map.getObject(), sender, contents);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
}