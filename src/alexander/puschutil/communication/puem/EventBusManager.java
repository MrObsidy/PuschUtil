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

import java.util.ArrayList;
import java.util.HashMap;

import alexander.puschutil.communication.generic.SharedResource;
import alexander.puschutil.communication.puem.api.MethodWrapper;

/**
 * 
 * This is the central class of the Event Management system. Events are dispatched
 * to the specific buses here, aswell as callback registering and many more things.
 * 
 * </br>
 * </br>
 * 
 * In order for a method to be suited for callback purposes, it has to be like this:
 * </br>
 * callbackMethodName(String sender, SharedResource);
 * 
 * @author Alexander Pusch
 *
 */
public final class EventBusManager {
	
	private static final HashMap<String, EventBus> EVENT_BUSES = new HashMap<String, EventBus>();
		
	/**
	 * 
	 * Register a Callback function to the Event Bus eventBusName.
	 * 
	 * @param eventBusName - the bus you want to register the Callback to
	 * @param wrap - the MethodWrapper object that contains the Callback Method.
	 */
	public static void registerCallback(String eventBusName, MethodWrapper wrap){
		if(!EVENT_BUSES.containsKey(eventBusName)) {
			EVENT_BUSES.put(eventBusName, new EventBus());
		}
		EVENT_BUSES.get(eventBusName).registerCallbackFunction(wrap);
	}
	
	public static void unregisterCallback(String eventBusName, MethodWrapper wrap){
		EVENT_BUSES.get(eventBusName).removeCallbackFunction(wrap);
	}
	
	public static ArrayList<String> getAvailableEventBusesName(){
		ArrayList<String> names = new ArrayList<String>();
		
		for (HashMap.Entry<String, EventBus> entry : EVENT_BUSES.entrySet()){
			names.add(entry.getKey());
		}
		
		return names;
	}
	
	
	public static void relayEvent(String eventBus, String sender, SharedResource res){
		if(EVENT_BUSES.containsKey(eventBus)) {
			EVENT_BUSES.get(eventBus).relayEvent(sender, res);
		} else {
			//do noting, no point
		}
	}
}
