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
import java.util.HashMap;

import alexander.puschutil.communication.generic.SharedResource;
import alexander.puschutil.communication.puem.api.MethodWrapper;
import alexander.puschutil.errors.exception.InvalidKeyException;
import alexander.puschutil.logging.Logger;

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
 * </br>
 * 
 * Naming convention of the EventBuses: ALLCAPS.
 * </br>
 * Pre-defined EventBuses:
 * </br>
 * -TICK for Regular ticking purposes. Please bear in mind that you have to provide a TickProvider yourself!
 * </br>
 * 
 * -ERROR for Publishing errors.
 * @author Alexander Pusch
 *
 */
public class EventBusManager {
	
	private static final HashMap<String, EventBus> EVENT_BUSES = new HashMap<String, EventBus>();
	private static boolean initialized;
	
	/**
	 * Call this at the beginning of the program. It initializes
	 * 
	 * @return If this is the first time that this method is called.
	 */
	public static boolean initialize(){
		if(!initialized){
			EVENT_BUSES.put("TICK", new TickBus());
			return true;
		} else {
			Logger.log("Was already initialized.");
			return false;
		}
	}
	
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
		
		if (eventBusName.equalsIgnoreCase("TICK")) {
			EVENT_BUSES.get("TICK").registerCallbackFunction(wrap);
			//This is pointless but I keep it for me to make the code more readable for me
			//since I have to maintain it, right?
		} else {
			EVENT_BUSES.get(eventBusName).registerCallbackFunction(wrap);
		}
	}
	
	/**
	 * 
	 * Unregister a callback Function from the Event Bus eventBusName.
	 * 
	 * @param eventBusName  - the bus you want to unregister the Callback from
	 * @param wrap - the MethodWrapper object that contains the Callback method.
	 */
	public static void unregisterCallback(String eventBusName, MethodWrapper wrap){
		EVENT_BUSES.get(eventBusName).removeCallbackFunction(wrap);
	}
	
	/**
	 * Gets the names of all currently registered EventBuses.
	 * 
	 * @return
	 */
	public static ArrayList<String> getAvailableEventBusesName(){
		ArrayList<String> names = new ArrayList<String>();
		
		for (HashMap.Entry<String, EventBus> entry : EVENT_BUSES.entrySet()){
			names.add(entry.getKey());
		}
		
		return names;
	}
	
	/**
	 * 
	 * Relay an event.
	 * 
	 * @param eventBus - the EventBus you want to dispatch the Event to.
	 * @param sender - the name of the sender (I recommend Object.toString();)
	 * @param res - the SharedResource containing the Event Data.
	 * @return
	 */
	public static boolean relayEvent(String eventBus, String sender, SharedResource res){
		if(EVENT_BUSES.containsKey(eventBus)) {
			//To avoid smartasses push an event to the TickBus even though only one ticker is permitted
			if (EVENT_BUSES.get(eventBus) instanceof TickBus) return false;
			//To avoid smartasses push an event to a SecureBus even though you need to be a registered Event sender
			if (EVENT_BUSES.get(eventBus) instanceof SecureBus) return false;
			
			EVENT_BUSES.get(eventBus).relayEvent(sender, res);
			return true;
			
		} else {
			return false;
		}
	}
	
	/**
	 * Send a tick event to all TickBus subscribers. Please bear in mind that to be able to tick
	 * this, you must be the registered ticker. To become the ticker, see {@link EventBusManager.setTicker()}.
	 * 
	 * @param tickerKey - the key you get when you requested to be the ticker.
	 * @param tickContents - any information you want to relay when ticking
	 * @return If you have the permission to tick this. (AKA your tickerKey is correct and the tick was relayed).
	 * @throws IllegalStateException - when you call this before {@link EventBusManager.initialize()}
	 * @throws InvalidKeyException - when you provide an invalid key
	 * 
	 */
	public static boolean tick(long tickerKey, Object tickContents) throws IllegalStateException, InvalidKeyException {
		if (EVENT_BUSES.get("TICK") instanceof TickBus) {
			TickBus tick = (TickBus) EVENT_BUSES.get("TICK");
			if(tickerKey == tick.getKey()){
				tick.tick(tickContents);
				return true;
			}
			
			throw new InvalidKeyException("This key was invalid: " + tickerKey);
			
		} else {
			throw new IllegalStateException("EventBusManager is not initialized yet, still is being called");
		}
	}
	
	/**
	 * Call this method if you wish to become the ticker.
	 * 
	 * @param requestTickerChange - the wrapper for the method in your class that gets called whenever setTicker() is invoked to ask for permission to change the ticker (This is so to avoid an unauthorized ticker change). This Method must contain no arguments and return a boolean (true for permission, false for denial,)
	 * @return The tickerKey that you need in order to call tick() (so don't lose it), if the return value is 0L it means your attempt was denied.
	 * @throws IllegalAccessException - when construction fails
	 * @throws IllegalArgumentException - when construction fails
	 * @throws InvocationTargetException - when construction fails
	 * @throws IllegalStateException - when you call this before {@link EventBusManager.initialize()}
	 * 
	 */
	public static long setTicker(MethodWrapper requestTickerChange) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IllegalStateException {
		if (EVENT_BUSES.get("TICK") instanceof TickBus){
			TickBus tick = (TickBus) EVENT_BUSES.get("TICK");
			return tick.setTicker(requestTickerChange);
		} else {
			throw new IllegalStateException("EventBusManager is not initialized yet, still is being called");
		}
	}
	
	/**
	 * This method requests adding you to the list of permitted event relayers.
	 * 
	 * @param bus - the SecureBus that you want to register to
	 * @param permissionAsker - the Method that you want to get called back if somebody else wants to be added to the list of relayers.
	 * @return the Key you need in order to push an event to a secure key.
	 * @throws IllegalAccessException - when construction fails
	 * @throws IllegalArgumentException - when construction fails
	 * @throws InvocationTargetException - when construction fails
	 */
	public static long addSecurePusher(String bus, MethodWrapper permissionAsker) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if (EVENT_BUSES.get(bus) instanceof SecureBus){
			SecureBus secbus = (SecureBus) EVENT_BUSES.get(bus);
			return secbus.addEventPusher(permissionAsker);
		} else {
			return 0L;
		}
	}
	
	/**
	 * Push an event to the secureBus bus.
	 * 
	 * @param sender - 
	 * @param bus
	 * @param res
	 * @param key
	 * @return
	 * @throws InvalidKeyException
	 */
	public static boolean pushToSecureBus(String sender, String bus, SharedResource res, long key) throws InvalidKeyException {
		if(EVENT_BUSES.get(bus) instanceof SecureBus){
			SecureBus secbus = (SecureBus) EVENT_BUSES.get(bus);
			if(secbus.getKeys().contains(key)){
				secbus.relayEvent(sender, res);
				return true;
			} else {
				throw new InvalidKeyException("Invalid SecureBus key: " + key);
			}
		} else {
			return false;
		}
	}
}
