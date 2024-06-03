package tr.com.nuritiras.sinavuygulamasi;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class UrunAdapter extends RecyclerView.Adapter<UrunAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView urunAdi, fiyat, adet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urunAdi = itemView.findViewById(R.id.list_item_adi);
            fiyat = itemView.findViewById(R.id.list_item_fiyat);
            adet = itemView.findViewById(R.id.list_item_adet);
        }
    }

    Context context;
    ArrayList<Urun> arrayList;

    public UrunAdapter(Context context, ArrayList<Urun> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public UrunAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UrunAdapter.ViewHolder holder, int position) {
        holder.urunAdi.setText("Ürün Adı: " + arrayList.get(position).getUrunAdi());
        holder.fiyat.setText("Fiyat: " + arrayList.get(position).getFiyat());
        holder.adet.setText("Adet: " + arrayList.get(position).getAdet());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
