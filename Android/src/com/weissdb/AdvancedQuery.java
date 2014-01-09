package com.weissdb;

import java.util.ArrayList;

public class AdvancedQuery
{
	private String m_name = "";
	private String m_rarity = "Any Rarity";
	private String m_color = "Any Color";
	private String m_side = "Any Side";
	private String m_levelComparator = "<";
	private String m_level = "";
	private String m_costComparator = "<";
	private String m_cost = "";
	private String m_powerComparator = "<";
	private String m_power = "";
	private String m_soulComparator = "<";
	private String m_soul = "";
	private String m_trait1 = "";
	private String m_trait2 = "";
	private String m_triggers = "Any Trigger";
	private String m_flavor = "";
	private String m_text = "";
	
	public AdvancedQuery()
	{	
	}
	
	public AdvancedQuery(String name, String rarity, String color, String side, String levelComparator, String level,
						 String costComparator, String cost, String powerComparator, String power, String soulComparator,
						 String soul, String trait1, String trait2, String triggers, String flavor, String text)
	{	
		if (!name.equals(""))
		{
			m_name = name;
		}
		if (!rarity.equals("Any Rarity"))
		{
			m_rarity = rarity;
		}
		if (!color.equals("Any Color"))
		{
			m_color = color;
		}
		if (!side.equals("Any Side"))
		{
			m_side = side;
		}
		if (!level.equals(""))
		{
			m_levelComparator = levelComparator;
			m_level = level;
		}
		if (!cost.equals(""))
		{
			m_costComparator = costComparator;
			m_cost = cost;
		}
		if (!power.equals(""))
		{
			m_powerComparator = powerComparator;
			m_power = power;
		}
		if (!soul.equals(""))
		{
			m_soulComparator = soulComparator;
			m_soul = soul;
		}
		if (!trait1.equals(""))
		{
			m_trait1 = trait1;
		}
		if (!trait2.equals(""))
		{
			m_trait2 = trait2;
		}
		if (!triggers.equals("Any Trigger"))
		{
			m_triggers = triggers;
		}
		if (!flavor.equals(""))
		{
			m_flavor = flavor;
		}
		if (!text.equals(""))
		{
			m_text = text;
		}
	}
	
	public String[] getParams()
	{
		return new String[] {m_name, m_rarity, m_color, m_side, m_levelComparator, m_level, m_costComparator,
							 m_cost, m_powerComparator, m_power, m_soulComparator, m_soul, m_trait1, m_trait2, 
							 m_triggers, m_flavor, m_text};
	}
	
	// Returns where statement
	public WhereInfo createSelection()
	{	
		String selection = "";
		ArrayList<String> ArgsList = new ArrayList<String>();
		if (!m_name.equals(""))
		{
			selection += " AND name LIKE ? COLLATE NOCASE";
			ArgsList.add("%" + m_name + "%");
		}
		if (!m_rarity.equals("Any Rarity"))
		{
			selection += " AND rarity = ?";
			ArgsList.add(m_rarity);
		}
		if (!m_color.equals("Any Color"))
		{
			selection += " AND color = ?";
			ArgsList.add(m_color);
		}
		if (!m_side.equals("Any Side"))
		{
			selection += " AND side = ?";
			ArgsList.add(m_side);
		}
		if (!m_level.equals(""))
		{
			selection += " AND m_level " + m_levelComparator + " ?";
			ArgsList.add(m_level);
		}
		if (!m_cost.equals(""))
		{
			selection += " AND cost " + m_costComparator + " ?";
			ArgsList.add(m_cost);
		}
		if (!m_power.equals(""))
		{
			selection += " AND m_power " + m_powerComparator + " ?";
			ArgsList.add(m_power);
		}
		if (!m_soul.equals(""))
		{
			selection += " AND soul " + m_soulComparator + " ?";
			ArgsList.add(m_soul);
		}
		if (!m_trait1.equals(""))
		{
			selection += " AND trait1 LIKE ? COLLATE NOCASE";
			ArgsList.add("%" + m_trait1 + "%");
		}
		if (!m_trait2.equals(""))
		{
			selection += " AND trait2 LIKE ? COLLATE NOCASE";
			ArgsList.add("%" + m_trait2 + "%");
		}
		if (!m_triggers.equals("Any Trigger"))
		{
			selection += " AND triggers = ?";
			ArgsList.add(m_triggers);
		}
		if (!m_flavor.equals(""))
		{
			selection += " AND flavor LIKE ? COLLATE NOCASE";
			ArgsList.add("%" + m_flavor + "%");
		}
		if (!m_text.equals(""))
		{
			selection += " AND text LIKE ? COLLATE NOCASE";
			ArgsList.add("%" + m_text + "%");
		}
		
		// No parameters. Dump out the entire database I guess
		if (selection.length() == 0)
		{
			return new WhereInfo(null, null);
		}
		else
		{
			selection = selection.substring(5);
			String[] selectionArgs = ArgsList.toArray(new String[0]);
			return (new WhereInfo(selection, selectionArgs));
		}
	}
}
