package com.yunyan.downloader.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.dd.CircularProgressButton;
import com.squareup.picasso.Picasso;
import com.yunyan.downloader.R;
import com.yunyan.downloader.entity.FileInfo;
import com.yunyan.downloader.service.DownloadService;
import com.yunyan.downloader.util.Const;

import java.util.List;

/**
 * 下载文件列表适配器
 *
 * @author gzc
 */
public class FileListAdapter extends CommonAdapter<FileInfo> {

	/**
	 * 构造函数
	 */
	public FileListAdapter(Context context, List<FileInfo> datas,int itemLayoutResId) {
		super(context, datas, itemLayoutResId);

	}

	@Override
	public void convert(final ViewHolder viewHolder, final FileInfo fileInfo) {

		CircularProgressButton btn_Download = ((CircularProgressButton) viewHolder.getView(R.id.btn_Download));
		Picasso.with(context).load("").into((ImageView)viewHolder.getView(R.id.btn_stop));
		//btn_Download.setIndeterminateProgressMode(true);//播放轮转进度

		btn_Download.setProgress(fileInfo.getFinished());
		// 设置按钮事件监听
		btn_Download.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 通过intent启动service，在service启动多线程下载文件
				Intent intent = new Intent(context, DownloadService.class);
				intent.setAction(DownloadService.ACTION_START);
				intent.putExtra(Const.FILE_INFO_KEY, fileInfo);
				// 启动service，注意，不要忘记在XML里注册service
				context.startService(intent);
			}
		});

	}

	// =========================================

	/**
	 * 更新该下载文件的下载进度
	 *
	 * @param id
	 *            下载文件的ID
	 * @param progress
	 */
	public void updateProgress(int id, int progress) {
		FileInfo fileInfo = this.datas.get(id);
		fileInfo.setFinished(progress);
		Log.e(FileListAdapter.class.getSimpleName(),"正在下载："+id+" 下载进度："+progress);
		// 该方法可以在修改适配器绑定的数组后不用重新刷新activity，通知activity更新ListView
		// 调用notifyDataSetChanged函数后，getView回调函数会重新被调用一遍
		this.notifyDataSetChanged();
	}

}
