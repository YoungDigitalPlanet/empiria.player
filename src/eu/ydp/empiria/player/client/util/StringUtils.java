package eu.ydp.empiria.player.client.util;

import java.util.Set;

public class StringUtils {
	public static String reverse(String source) {
		int i, len = source.length();
	    StringBuffer dest = new StringBuffer(len);

	    for (i = (len - 1); i >= 0; i--)
	      dest.append(source.charAt(i));
	    return dest.toString();
	}
	
	public static String combine(String[] s, String glue)
	{
	  int k=s.length;
	  if (k==0)
	    return null;
	  StringBuilder out=new StringBuilder();
	  out.append(s[0]);
	  for (int x=1;x<k;++x)
	    out.append(glue).append(s[x]);
	  return out.toString();
	}
	
	public static String setToStringShort(Set<String> set1){
		String[] arr = set1.toArray(new String[0]);
		String all = "";
		for (int i = 0 ; i < arr.length ; i ++){
			all += arr[i];
			if (i < arr.length-1)
				all += ";";
		}
		return all;
	}
}
