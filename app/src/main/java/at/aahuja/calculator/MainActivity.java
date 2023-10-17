package at.aahuja.calculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SharedMemory;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Nullable
    @Override
    public View onCreateView(@Nullable View view, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(view, name, context, attrs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("msSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        EditText Wert1EditText = findViewById(R.id.Wert1);
        EditText Wert2EditText = findViewById(R.id.Wert2);
        TextView resultTextView = findViewById(R.id.result);

        findViewById(R.id.calculateBtn).setOnClickListener((View v) -> {
            int selectedOperationId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        String selectedOperation = ((RadioButton) findViewById(selectedOperationId)).getText().toString();


            try {
                float val1 = Float.parseFloat(Wert1EditText.getText().toString());
                float val2 = Float.parseFloat(Wert2EditText.getText().toString());

                float result = -1;
                switch (selectedOperation) {
                    case "+":
                        result = val1 + val2;
                        break;
                    case "-":
                        result = val1 - val2;
                        break;
                    case "*":
                        result = val1 * val2;
                        break;
                    case "/":
                        result = val1 / val2;
                        break;
                }
                resultTextView.setText(String.valueOf(result));
            } catch (NumberFormatException e) {
                resultTextView.setText(R.string.invalid_input);
            }
        });

        findViewById(R.id.memoryStoreBtn).setOnClickListener((View v) -> {
            editor.putString("result", resultTextView.getText().toString());
            Toast myToast = Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT);
            myToast.show();
            editor.apply();
        });

        findViewById(R.id.memoryRestoreBtn).setOnClickListener((View v) -> {
            resultTextView.setText(sharedPreferences.getString("result", "not_found"));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        RadioGroup rg = findViewById(R.id.radioGroup);

        for(int i = 0; i < rg.getChildCount(); i++) {
            rg.getChildAt(i).setEnabled(true);
        }
    }
}