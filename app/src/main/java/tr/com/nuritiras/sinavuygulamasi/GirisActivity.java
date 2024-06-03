package tr.com.nuritiras.sinavuygulamasi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import tr.com.nuritiras.sinavuygulamasi.databinding.ActivityGirisBinding;

public class GirisActivity extends AppCompatActivity {

    ActivityGirisBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGirisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public void onClickGirisYap(View view) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String textEposta = binding.editGirisTextEmailAddress.getText().toString().trim();
        String textParola = binding.editGirisTextPassword.getText().toString();
        if (!textEposta.isEmpty()) {
            if (!textParola.isEmpty()) {

                firebaseAuth
                        .signInWithEmailAndPassword(textEposta, textParola)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Toast.makeText(GirisActivity.this, "Başarı ile giriş yaptınız.", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(GirisActivity.this, MainActivity.class));

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                binding.textViewDurum.setTextColor(Color.parseColor("#ff1744"));
                                binding.textViewDurum.setText("Giriş başarısız\n" + e.getLocalizedMessage());

                            }
                        });


            } else binding.textViewDurum.setText("Şifre boş olamaz");
        } else binding.textViewDurum.setText("E-posta boş olamaz");
    }

    public void onClickSifremiUnuttum(View view) {
        String eposta = binding.editGirisTextEmailAddress.getText().toString().trim();
        if (eposta.isEmpty()) {
            binding.textViewDurum.setTextColor(Color.parseColor("#ff1744"));
            binding.textViewDurum.setText("E-posta boş olamaz");

        }
        else
        {
            FirebaseAuth.getInstance()
                    .sendPasswordResetEmail(eposta)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            binding.textViewDurum.setTextColor(Color.parseColor("#2e7d32"));
                            binding.textViewDurum.setText("Hatırlatma e-postası gönderildi");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            binding.textViewDurum.setTextColor(Color.parseColor("#ff1744"));
                            binding.textViewDurum.setText("Hatırlatma e-postası gönderilemedi\n" + e.getLocalizedMessage());

                        }
                    });
        }
    }

    public void onClickKayitOl(View view) {
        finish();
        startActivity(new Intent(GirisActivity.this, KullaniciKayitActivity.class));
    }
}