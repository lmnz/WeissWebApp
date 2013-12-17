package com.example.weissdb;

public class WeissCard
{
	private String m_name;
	private String m_cardNo;
	private String m_rarity;
	private String m_color;
	private String m_side;
	private String m_level;
	private String m_cost;
	private String m_power;
	private String m_soul;
	private String m_trait1;
	private String m_trait2;
	private String m_triggers;
	private String m_flavor;
	private String m_text;

	// Default constructor
	public WeissCard()
	{
	}

	// Constructor
	public WeissCard(String name, String cardNo, String rarity, String color, String side,
					 String level, String cost, String power, String soul, String trait1, 
					 String trait2, String triggers, String flavor, String text)
	{
		m_name = name;
		m_cardNo = cardNo;
		m_rarity = rarity;
		m_color = color;
		m_side = side;
		m_level = level;
		m_cost = cost;
		m_power = power;
		m_soul = soul;
		m_trait1 = trait1;
		m_trait2 = trait2;
		m_triggers = triggers;
		m_flavor = flavor;
		m_text = text;
	}

	// Getters and setters
	public String getName()
	{
		return m_name;
	}

	public void setName(String name)
	{
		m_name = name;
	}

	public String getCardNo()
	{
		return m_cardNo;
	}

	public void setCardNo(String cardNo)
	{
		m_cardNo = cardNo;
	}

	public String getRarity()
	{
		return m_rarity;
	}

	public void setRarity(String rarity)
	{
		m_rarity = rarity;
	}

	public String getColor()
	{
		return m_color;
	}

	public void setColor(String color)
	{
		m_color = color;
	}

	public String getSide()
	{
		return m_side;
	}
	
	public void setSide(String side)
	{
		m_side = side;
	}
	
	public String getLevel()
	{
		return m_level;
	}

	public void setLevel(String level)
	{
		m_level = level;
	}
	
	public String getCost()
	{
		return m_cost;
	}

	public void setCost(String cost)
	{
		m_cost = cost;
	}

	
	public String getPower()
	{
		return m_power;
	}

	public void setPower (String power)
	{
		m_power = power;
	}

	public String getSoul()
	{
		return m_soul;
	}

	public void setSoul(String soul)
	{
		m_soul = soul;
	}
	
	public String getTrait1()
	{
		return m_trait1;
	}

	public void setTrait1 (String trait1)
	{
		m_trait1 = trait1;
	}
	
	public String getTrait2()
	{
		return m_trait2;
	}

	public void setTrait2(String trait2)
	{
		m_trait2 = trait2;
	}
	
	public String getTriggers()
	{
		return m_triggers;
	}

	public void setTriggers(String triggers)
	{
		m_triggers = triggers;
	}

	public String getFlavor()
	{
		return m_flavor;
	}

	public void setFlavor (String flavor)
	{
		m_flavor = flavor;
	}

	public String getText()
	{
		return m_text;
	}

	public void setText(String text)
	{
		m_text = text;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString()
	{
		return m_name;
	}
}
