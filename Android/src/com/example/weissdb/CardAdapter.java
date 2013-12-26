package com.example.weissdb;

import java.util.List;

import android.content.Context;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

public class CardAdapter extends BaseAdapter /* implements OnClickListener */
{

	/*
	 * private class OnItemClickListener implements OnClickListener{ private int
	 * mPosition; OnItemClickListener(int position){ mPosition = position; }
	 * public void onClick(View arg0) { Log.v("ddd", "onItemClick at position" +
	 * mPosition); } }
	 */

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

		// v.setBackgroundColor((position % 2) == 1 ? Color.rgb(50,50,50) :
		// Color.BLACK);

		/* v.setOnClickListener(new OnItemClickListener(position)); */
		return v;
	}

	/*
	 * public void onClick(View v) { Log.v(LOG_TAG, "Row button clicked"); }
	 */
}