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

package alexander.puschutil.math.matrix;

import alexander.puschutil.math.matrix.vector.Vector3f;
import alexander.puschutil.math.matrix.vector.Vector3i;
import alexander.puschutil.math.matrix.vector.Vector4f;
import alexander.puschutil.math.matrix.vector.Vector4i;

public class DotProduct {
	public static float calculateDotProduct3f(Vector3f vec1, Vector3f vec2){
		return vec1.getX() * vec2.getX() + vec1.getY() * vec2.getY() + vec1.getZ() * vec2.getZ();
	}
	
	public static int calculateDotProduct3i(Vector3i vec1, Vector3i vec2){
		return vec1.getX() * vec2.getX() + vec1.getY() * vec2.getY() + vec1.getZ() * vec2.getZ();
	}
	
	public static float calculateDotProduct4f(Vector4f vec1, Vector4f vec2){
		return vec1.getX() * vec2.getX() + vec1.getY() * vec2.getY() + vec1.getZ() * vec2.getZ() + vec1.getT() * vec2.getT();
	}
	
	public static int calculateDotProduct4i(Vector4i vec1, Vector4i vec2){
		return vec1.getX() * vec2.getX() + vec1.getY() * vec2.getY() + vec1.getZ() * vec2.getZ() + vec1.getT() * vec2.getT();
	}
}
