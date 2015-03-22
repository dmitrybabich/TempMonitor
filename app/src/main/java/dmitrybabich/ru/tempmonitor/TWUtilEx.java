package dmitrybabich.ru.tempmonitor;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.tw.john.TWUtil;
import android.util.Log;


import java.lang.reflect.Type;

public class TWUtilEx {
	private Context context;
	private Handler mHandler;

	public static boolean isTWUtilAvailable() {
		try {
			Type type = TWUtil.class;
			String name = type.getClass().getName();
			Log.d("TWUtilEx", "PASS: TWUtil is available!");
			return true;
		}
		catch (Throwable ex){
			Log.d("TWUtilEx", "ERROR: TWUtil is not available");
			return false;
		}
	}

    private static final short[] twutil_contexts = new short[]{
            TWUtilConst.TW_CODE_Climat,                   // 1281
    };


	private TWUtil mTWUtil;

	protected static final String TWUTIL_HANDLER = "TWUtilHandler";


	protected boolean isTWUtilOpened;
	private Handler mTWUtilHandler;


	public TWUtilEx(Context context) {

		this.context = context;
		mHandler = new Handler();

		mTWUtil = null;
		this.mTWUtilHandler = null;
		isTWUtilOpened = false;
		mTWUtilHandler = new MyTWUtilHandler(context);
	}

	public void Init() {
		Log.d ("TWUtilEx", "Init ");
		mTWUtil = new TWUtil();
		int result = mTWUtil.open(twutil_contexts);
		if ( result == 0) {
			isTWUtilOpened = true;
			mTWUtil.start ();
			mTWUtil.addHandler (TWUTIL_HANDLER, mTWUtilHandler);
		}

	}

	public void Destroy() {
		Log.d ("TWUtil", "Destroy()");
		if ( isTWUtilOpened ) {
			mTWUtil.removeHandler ( TWUTIL_HANDLER );
			mTWUtil.stop ();
			mTWUtil.close ();
			mTWUtil = null;
			isTWUtilOpened = false;
		}
	}



	public static String GetDeviceID () {
		Log.d ("TWUtilEx", "GetDeviceID ");
        if ( ! TWUtilEx.isTWUtilAvailable() ) return "<Unknown>";
		TWUtil mTW = new TWUtil ();
		if (mTW.open (new short[]{(short) 65521}) == 0) {
			try {
				mTW.start ();
				int res = mTW.write (65521);
				mTW.stop ();
				mTW.close ();
                String rstr = "";
                switch (res) {
                    case 17:
                    case 14:
                        rstr = String.format("Kaier (ID: %d)", res);
                        break;
                    case 1:
                        rstr = String.format("Create (ID: %d)", res);
                        break;
                    case 3:
                        rstr = String.format("Anstar (ID: %d)", res);
                        break;
                    case 7:
                    case 48:
                        rstr = String.format("Waybo (ID: %d)", res);
                        break;
                    case 6:
                        rstr = String.format("Waybo (ID: %d)", res);
                        break;
                    case 22:
                        rstr = String.format("Infidini (ID: %d)", res);
                        break;
                    default:
                        rstr = String.format("<Unknown> (ID: %d)", res);
                        break;
                }
				return rstr;
			} catch ( Exception e ) {
				return "<Unknown>";
			}
		} else { return "<Unknown>"; }
	}

}
