package tr.com.nuritiras.sinavuygulamasi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import tr.com.nuritiras.sinavuygulamasi.databinding.ActivityKullaniciKayitBinding;

public class KullaniciKayitActivity extends AppCompatActivity {

    ActivityKullaniciKayitBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityKullaniciKayitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public void onClickKayitOl(View view) {

        String txtEposta=binding.editKayitTextEmailAddress.getText().toString().trim();
        String txtParola=binding.editKayitTextPassword.getText().toString();
        String txtReParola=binding.editKayitTextRePassword.getText().toString();

        if(!txtEposta.isEmpty()){
            if(!txtParola.isEmpty()){
                if(!txtReParola.isEmpty()){
                    if(txtParola.equals(txtReParola)){
                        // Firestore Kullanıcı kayıtları
                        FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(txtEposta,txtParola)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        ///
                                        Toast.makeText(KullaniciKayitActivity.this, "Kayıt başarılı.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(KullaniciKayitActivity.this, "Kullanıcı oluşturulamadı.", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    } else Toast.makeText(this, "Parolalar uyuşmuyor.", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(this, "Parola tekrar boş geçilemez", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, "Parola boş geçilemez.", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "E-Posta boş geçilemez", Toast.LENGTH_SHORT).show();

    }

    public void onClickGirisYap(View view) {
        finish();
        startActivity(new Intent(KullaniciKayitActivity.this, GirisActivity.class));
    }
}