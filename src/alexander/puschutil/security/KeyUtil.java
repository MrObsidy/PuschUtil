package alexander.puschutil.security;

import java.util.Random;

public class KeyUtil {
	public static long generateKey(){
		Random rand = new Random();
		long key = rand.nextLong();
		return (key != 0L) ? key : key + 1L;
	}
}
