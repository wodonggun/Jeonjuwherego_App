package com.example.racos2.jeonju_where_go.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


public class ProgressDlg extends AsyncTask<Void, Void, Void> {

	private ProgressDialog mDlg;
	private Context mContext;

	public ProgressDlg(Context context) {
		mContext = context;
	}

	@Override
	protected void onPreExecute() {
		mDlg = new ProgressDialog(mContext);
		mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mDlg.setMessage("Loading...");
		mDlg.show();

		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {


		//publishProgress("max", Integer.toString(taskCnt));

		for (int i = 0; i < 5; ++i) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


		return null;
	}
	@Override
	protected void onPostExecute(Void result) {
		mDlg.dismiss();
		super.onPostExecute(result);
	}






}