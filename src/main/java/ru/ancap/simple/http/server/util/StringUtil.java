//- Copyright ©2009 Micah Martin.  All Rights Reserved
//- MMHTTP and all included source files are distributed under terms of the GNU LGPL.

package ru.ancap.simple.http.server.util;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Arrays;

public class StringUtil
{
    public static String join(List strings, String delimiter)
  {
    if (strings.isEmpty())
      return "";

    Iterator i = strings.iterator();
    StringBuffer joined = new StringBuffer((String) i.next());

    for (/* declared above */; i.hasNext();)
    {
      String eachLine = (String) i.next();
      joined.append(delimiter);
      joined.append(eachLine);
    }

    return joined.toString();
  }

	public static String[] combineArrays(String[] first, String[] second)
	{
		List combinedList = new LinkedList();
		combinedList.addAll(Arrays.asList(first));
		combinedList.addAll(Arrays.asList(second));
		return (String[]) combinedList.toArray(new String[combinedList.size()]);
	}
}
