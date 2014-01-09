package com.weissdb;

import java.util.List;

import android.content.Context;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CardAdapter extends BaseAdapter
{
	private Context context;
	private List<Spanned> cardList;

	public CardAdapter(Context context, List<Spanned> cardList)
	{
		this.context = context;
		this.cardList = cardList;
	}

	public int getCount()
	{
		return cardList.size();
	}

	public Object getItem(int position)
	{
		return cardList.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		Spanned card = cardList.get(position);
		TextView cardInfo = new TextView(context);
		cardInfo.setText(card);
	    
		View v = cardInfo;
		return v;
	}
}