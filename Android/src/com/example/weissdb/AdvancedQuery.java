package com.example.weissdb;

import java.util.ArrayList;

public class AdvancedQuery
{
	String m_name = null;
	String m_rarity = null;
	String m_color = null;
	String m_side = null;
	String m_levelComparator = null;
	String m_level = null;
	String m_costComparator = null;
	String m_cost = null;
	String m_powerComparator = null;
	String m_power = null;
	String m_soulComparator = null;
	String m_soul = null;
	String m_trait1 = null;
	String m_trait2 = null;
	String m_triggers = null;
	String m_flavor = null;
	String m_text = null;
	
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
	
	// Returns where statement
	public WhereInfo createSelection()
	{	
		String selection = "";
		ArrayList<String> ArgsList = new ArrayList<String>();
		if (m_name != null)
		{
			selection += " AND name LIKE ? COLLATE NOCASE";
			ArgsList.add("%" + m_name + "%");
		}
		if (m_rarity != null)
		{
			selection += " AND rarity = ?";
			ArgsList.add(m_rarity);
		}
		if (m_color != null)
		{
			selection += " AND color = ?";
			ArgsList.add(m_color);
		}
		if (m_side != null)
		{
			selection += " AND side = ?";
			ArgsList.add(m_side);
		}
		if (m_level != null)
		{
			selection += " AND m_level " + m_levelComparator + " ?";
			ArgsList.add(m_level);
		}
		if (m_cost != null)
		{
			selection += " AND cost " + m_costComparator + " ?";
			ArgsList.add(m_cost);
		}
		if (m_power != null)
		{
			selection += " AND m_power " + m_powerComparator + " ?";
			ArgsList.add(m_power);
		}
		if (m_soul != null)
		{
			selection += " AND soul " + m_soulComparator + " ?";
			ArgsList.add(m_soul);
		}
		if (m_trait1 != null)
		{
			selection += " AND trait1 LIKE ? COLLATE NOCASE";
			ArgsList.add("%" + m_trait1 + "%");
		}
		if (m_trait2 != null)
		{
			selection += " AND trait2 LIKE ? COLLATE NOCASE";
			ArgsList.add("%" + m_trait2 + "%");
		}
		if (m_triggers != null)
		{
			selection += " AND triggers = ?";
			ArgsList.add(m_triggers);
		}
		if (m_flavor != null)
		{
			selection += " AND flavor LIKE ? COLLATE NOCASE";
			ArgsList.add("%" + m_flavor + "%");
		}
		if (m_text != null)
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
