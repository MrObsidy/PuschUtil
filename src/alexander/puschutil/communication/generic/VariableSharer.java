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

package alexander.puschutil.communication.generic;

import java.util.HashMap;

public class VariableSharer {
	private static final HashMap<String, SharedResource> OBJECTS = new HashMap<String, SharedResource>();
	
	public static void createSharedResource(String name, Object content){
		OBJECTS.put(name, new SharedResource(name, content));
	}
	
	public static SharedResource getSharedResource(String name){
		return OBJECTS.get(name);
	}
}
