package tr.com.nuritiras.sinavuygulamasi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tr.com.nuritiras.sinavuygulamasi.databinding.ActivityUrunBinding;

public class UrunActivity extends AppCompatActivity {

    ActivityUrunBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUrunBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public void onClickKaydet(View view) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String textUrunAdi = binding.editUrunTextAdi.getText().toString().trim();
        String textFiyat = binding.editUrunDecimalFiyat.getText().toString();
        String textAdet = binding.editUrunNumberAdet.getText().toString();

        if (!textUrunAdi.isEmpty() && !textFiyat.isEmpty() && !textAdet.isEmpty()) {

            double decFiyat = Double.parseDouble(textFiyat);
            int intAdet = Integer.parseInt(textAdet);

            Map<String, Object> urun = new HashMap<>();
            urun.put("urunAdi", Objects.requireNonNull(textUrunAdi));
            urun.put("fiyat", Objects.requireNonNull(decFiyat));
            urun.put("adet", Objects.requireNonNull(intAdet));

            db.collection("urunler")
                    .add(urun).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Toast.makeText(UrunActivity.this, "Ürün Başarıyla Eklendi", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(UrunActivity.this, MainActivity.class));
                        }
                    }).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            binding.textViewUrunDurum.setTextColor(Color.parseColor("#ff1744"));
                            binding.textViewUrunDurum.setText("Ürünler eklenirken bir hata oluştu");

                        }
                    });
        } else {
            binding.textViewUrunDurum.setTextColor(Color.parseColor("#ff1744"));
            binding.textViewUrunDurum.setText("Lütfen Boş Alanları Doldurunuz");
        }

    }
}