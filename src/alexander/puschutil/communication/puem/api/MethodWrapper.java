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

package alexander.puschutil.communication.puem.api;

import java.lang.reflect.Method;

import org.eclipse.jdt.annotation.Nullable;

import alexander.puschutil.misc.Wrapper;

public final class MethodWrapper extends Wrapper<Object, Method> {
	
	/**
	 * 
	 * Create a new Method Wrapper.
	 * 
	 * @param obj The object, to which the Method belongs (null for static methods)
	 * @param met The method that is being wrapped
	 */
	public MethodWrapper(@Nullable Object obj, Method met){
		super(obj, met);
	}
	
	/**
	 * 
	 * Get the wrapped Method
	 * 
	 * @return the Method
	 */
	@Nullable
	public Method getMethod(){
		return this.getWrapped();
	}
	
	/**
	 * 
	 * Get the wrapping object (null for static methods)
	 * 
	 * 
	 * @return the Object
	 */
	@Nullable
	public Object getObject(){
		return this.getWrapper();
	}
}
