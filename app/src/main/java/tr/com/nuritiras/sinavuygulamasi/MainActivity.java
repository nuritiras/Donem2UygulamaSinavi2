package tr.com.nuritiras.sinavuygulamasi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Objects;

import tr.com.nuritiras.sinavuygulamasi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseApp.initializeApp(MainActivity.this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recycler);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(MainActivity.this, GirisActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        else
        {
            db.collection("urunler").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<Urun> arrayList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Urun urun = document.toObject(Urun.class);
                            urun.setId(document.getId());
                            arrayList.add(urun);
                        }
                        UrunAdapter adapter = new UrunAdapter(MainActivity.this, arrayList);
                        recyclerView.setAdapter(adapter);

                    } else {
                        Toast.makeText(MainActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void onClickCikis(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, GirisActivity.class));
    }

    public void onClickUrunKayit(View view) {
        finish();
        startActivity(new Intent(this, UrunActivity.class));
    }
}