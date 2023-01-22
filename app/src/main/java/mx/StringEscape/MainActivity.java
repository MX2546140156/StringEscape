package mx.StringEscape;

import android.app.Activity;
import android.os.Bundle;
import android.content.ClipboardManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener, AdapterView.OnItemSelectedListener, MenuItem.OnMenuItemClickListener {

	Toast toast;
	EditText editText;
	Button button, button1;
	ImageButton imageButton, imageButton1;
	TextView textView;
	Spinner spinner;
	int mode;
	ClipboardManager clipboardmanager;

	StringBuilder estr = new StringBuilder("\\$()*+.[]?^{}|"),
					epstr = new StringBuilder("\\\"'$()*+.[]?^{}|@"),
					exstr = new StringBuilder("&<>\"'@");

	String xstr[] = {"&amp;", "&lt;", "&gt;", "&quot;", "&apos;", "\\@"},
					pstr[] = {"\\$", "\\(", "\\)", "\\*", "\\+", "\\.", "\\[", "\\]", "\\?", "\\^", "\\{", "\\}", "\\|"},
					xestr[] = {"\\\"", "\\\'", "\\@"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		clipboardmanager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

		setContentView(R.layout.mx0000);
		editText = findViewById(R.id.mxet0000);
		button = findViewById(R.id.mxb0000);
		button1 = findViewById(R.id.mxb0001);
		imageButton = findViewById(R.id.mxib0000);
		imageButton1 = findViewById(R.id.mxib0001);
		textView = findViewById(R.id.mxtv0000);
		spinner = findViewById(R.id.mxs0000);

		button.setOnClickListener(this);
		button.setOnLongClickListener(this);
		button.setOnTouchListener(this);

		button1.setOnClickListener(this);
		button1.setOnLongClickListener(this);
		button1.setOnTouchListener(this);

		imageButton.setOnClickListener(this);
		imageButton.setOnLongClickListener(this);
		imageButton.setOnTouchListener(this);

		imageButton1.setOnClickListener(this);
		imageButton1.setOnLongClickListener(this);
		imageButton1.setOnTouchListener(this);

		spinner.setOnItemSelectedListener(this);

	}


	@Override
	public void onClick(View view) {
		String text = editText.getText().toString();
		switch (view.getId()) {
			case R.id.mxb0000:
				if (noString(text)) {
					toast(R.string.mx000a);
				} else {
					switch (mode) {
						case 0:
							text = toEscape(text, estr);
							break;
						case 1:
							text = toEscape(text, epstr);
							break;
						case 2:
							text = toEscape(text, new StringBuilder());
							for (int i = 0; i < exstr.length(); i++) {
								String key = String.valueOf(exstr.charAt(i));
								if (text.contains(key)) {
									text = text.replace(key, xstr[i]);
								}
							}
							break;
					}
					textView.setText(text);
				}
				break;
			case R.id.mxb0001:
				if (noString(text)) {
					toast(R.string.mx000a);
				} else {
					switch (mode) {
						case 0:
							text = toStrings(text, pstr);
							break;
						case 1:
							text = toStrings(text, pstr);
							text = toStrings(text, xestr);
							break;
						case 2:
							for (int i = 0; i < xstr.length; i++) {
								if (text.contains(xstr[i])) {
									text = text.replace(xstr[i], String.valueOf(exstr.charAt(i)));
								}
							}
							text = toStrings(text, xestr);
							break;
					}
					textView.setText(text);
				}
				break;
			case R.id.mxib0000:
				paste();
				break;
			case R.id.mxib0001:
				copyResult();
				break;
		}
	}

	@Override
	public boolean onLongClick(View view) {
		switch (view.getId()) {
			case R.id.mxb0000:
				toast(R.string.mx0001);
				break;
			case R.id.mxb0001:
				toast(R.string.mx0002);
				break;
			case R.id.mxib0000:
				toast(R.string.mx000b);
				break;
			case R.id.mxib0001:
				toast(R.string.mx0007);
				break;
		}
		return true;
	}

	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case android.R.id.button1:
				textView.setText(R.string.mx0010);
				break;
			case android.R.id.button2:
				CrashHandler.killApp();
				break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, android.R.id.button1, 0, R.string.mx000e).setOnMenuItemClickListener(this).setIcon(R.drawable.mx0006).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(1, android.R.id.button2, 1, R.string.mx000f).setOnMenuItemClickListener(this).setIcon(R.drawable.mx0007).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		;
		return true;
	}

	public String toStrings(String text, String unescape[]) {
		for (String s : unescape) {
			if (text.contains(s))
				text = text.replace(s, String.valueOf(s.charAt(s.length() - 1)));
		}
		text = text.replace("\\\\", "\\");
		return text;
	}

	public String toEscape(String text, StringBuilder escape) {
		if (!TextUtils.isEmpty(text)) {
			for (int i = 0; i < escape.length(); i++) {
				String key = String.valueOf(escape.charAt(i));
				String x = String.valueOf(escape.charAt(0)) + String.valueOf(escape.charAt(i));
				if (text.contains(key)) {
					text = text.replace(key, x);
				}
			}
			if (text.contains("\n")) {
				text = text.replace("\n", "\\n");
			}
		}
		return text;
	}

	public boolean noString(CharSequence text) {
		if (text == null || text.length() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void paste() {
		CharSequence paste = clipboardmanager.getText();
		if (noString(paste)) {
			toast(R.string.mx000c);
		} else {
			editText.setText(paste);
		}
	}

	public void copyResult() {
		CharSequence result = textView.getText();
		if (noString(result)) {//判断结果有没有内容
			toast(R.string.mx0008);
		} else {
			try {
				clipboardmanager.setText(result);
			} catch (Exception e) {
				textView.setText(getString(R.string.mx0011) + e);
			}
			if (result.length() != clipboardmanager.getText().length()) {//利用内容长度判断是否复制成功
				toast(R.string.mx0009);
			} else {//复制成功
				toast(R.string.mx000d);
			}
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		int dur = 100;
		float xy = 0.95f;
		switch (motionEvent.getAction()) {
			case MotionEvent.ACTION_DOWN:
				switch (view.getId()) {
					case R.id.mxib0000:
					case R.id.mxib0001:
						xy = 0.8f;
						break;
				}
				view.animate().scaleX(xy).scaleY(xy).setDuration(dur).start();
				break;
			case MotionEvent.ACTION_UP:
				view.animate().scaleX(1).scaleY(1).setDuration(dur).start();
				break;
		}
		return false;
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		mode = i;
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
	}

	public void toast(int resId) {
		try {
			toast(getString(resId));
		} catch (Exception exception) {
			toast(String.valueOf(resId));
		}
	}

	public void toast(CharSequence str) {
		if (toast != null) {
			toast.cancel();
			toast = null;
		}
		toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
		toast.show();
	}
}
