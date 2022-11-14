package com.example.sinhvienfirebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SinhVienAdapter extends FirebaseRecyclerAdapter<SinhVienModel,SinhVienAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SinhVienAdapter(@NonNull FirebaseRecyclerOptions<SinhVienModel> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull SinhVienModel model) {
        holder.txtName.setText(model.getHoTen());
        holder.txtNameProvince.setText(model.getLop());
        holder.txtPrice.setText(model.getMaSV());

        Glide.with(holder.img.getContext())
                .load(model.getAnh())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_sinhvien))
                        .setExpanded(true,1200)
                        .create();
                //dialogPlus.show();
                View view = dialogPlus.getHolderView();

                EditText anh = view.findViewById(R.id.editT_anh_ud);
                EditText name = view.findViewById(R.id.editT_name_ud);
                EditText tenTinh = view.findViewById(R.id.editT_tentinh_ud);
                EditText gia = view.findViewById(R.id.editT_gia_ud);


                Button btnUpdate = view.findViewById(R.id.button_Update);

                anh.setText(model.getAnh());
                name.setText(model.getHoTen());
                tenTinh.setText(model.getLop());
                gia.setText(model.getMaSV());

                dialogPlus.show();
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("anh",anh.getText().toString());
                        map.put("hoTen",name.getText().toString());
                        map.put("lop",tenTinh.getText().toString());
                        map.put("maSV",gia.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("svien")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.txtName.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.txtName.getContext(), "Error while Updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.txtName.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Delete data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("svien")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.txtName.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView txtName,txtNameProvince, txtPrice ;
        ImageButton btnEdit,btnDelete;

        public  myViewHolder(@NonNull View itemView){
            super(itemView);

            img=(CircleImageView)  itemView.findViewById(R.id.img1);
            txtName = (TextView)  itemView.findViewById(R.id.textView_tenTour);
            txtNameProvince = (TextView)  itemView.findViewById(R.id.textView_tenTinh);
            txtPrice = (TextView)  itemView.findViewById(R.id.textView_gia);
            btnEdit=(ImageButton) itemView.findViewById(R.id.button_edit);
            btnDelete=(ImageButton) itemView.findViewById(R.id.button_delete);
        }
    }
}
