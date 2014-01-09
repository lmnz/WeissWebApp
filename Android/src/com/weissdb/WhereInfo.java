package com.weissdb;

public class WhereInfo
{
	private String m_selection = null;
	private String[] m_selectionArgs = null;
	
	public WhereInfo(String selection, String[] selectionArgs)
	{
		m_selection = selection;
		m_selectionArgs = selectionArgs;
	}
	
	public String getSelection()
	{
		return m_selection;
	}
	
	public String[] getSelectionArgs()
	{
		return m_selectionArgs;
	}
}
