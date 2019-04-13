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

import alexander.puschutil.misc.Wrapper;

public class SharedResource extends Wrapper<String, Object> {
	
	public SharedResource(String name, Object content){
		super(name, content);
	}
	
	public String getName(){
		return this.getWrapper();
	}
	
	public Object getObject(){
		return this.getWrapped();
	}
	
	public Class<?> getObjectClass(){
		return this.getWrapped().getClass();
	}
}
