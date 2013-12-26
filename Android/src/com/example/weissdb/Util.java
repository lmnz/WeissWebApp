package com.example.weissdb;

import java.io.IOException;
import java.io.InputStream;

import android.widget.EditText;
import android.widget.Spinner;

public class Util
{
	public static int count(InputStream filename) throws IOException
	{
		try
		{
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = filename.read(c)) != -1)
			{
				empty = false;
				for (int i = 0; i < readChars; ++i)
				{
					if (c[i] == '\n')
					{
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		}
		finally
		{
			filename.close();
		}
	}
	
	public static String parseCardNumber(String cardBlurb)
	{
		String[] tokens = cardBlurb.split("Card No.: ");
		String cardNo = (tokens[1].split("[\n]"))[0];
		return cardNo;
	}
}
