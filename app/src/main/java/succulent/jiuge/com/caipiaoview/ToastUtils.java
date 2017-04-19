package succulent.jiuge.com.caipiaoview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtils {
	//主线程弹框
	public static void toast(Context context,String str){
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
	//子线程弹框
	public static void toastInThread(Context context, String str) {
		Looper.prepare();
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
		Looper.loop();
	}

	/**
	 * 弹吐司
	 * @param message
   */
	public static void showToast(final Context context,final String message){
		Handler handler=new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {

				toast(context,message);
			}
		}) ;

	}
}