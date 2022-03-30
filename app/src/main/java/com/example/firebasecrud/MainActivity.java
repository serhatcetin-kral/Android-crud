package com.example.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    EditText input_ad,input_email;
    ListView lst_data;
    ProgressBar circulatProgresBar;

  FirebaseDatabase firebaseDatabase;
  DatabaseReference listeVeri;
  private List<Kullanici> list_kullanicilar=new ArrayList<>();
  private  Kullanici seciliKullanici;// listede secili kullaniciya tikladigimizda kullaniciyi tutacak




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//       Toolbar toolbar=findViewById(R.id.menu_toolbar);
//        toolbar.setTitle("FireBaseCRUD");
//        setSupportActionBar(toolbar); bunlar olmuyo burada ve xml de toolbar a dikkat et hangisini sectigine
        Toolbar toolbar=findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("FireBaseCRUDneresi");//burasi ana ekranda gosukuyo yanlis olabilir

        input_ad=findViewById(R.id.name);
        input_email=findViewById(R.id.email);

        lst_data=findViewById(R.id.list_data);
        circulatProgresBar=findViewById(R.id.circular_progress);


        //fire base
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        listeVeri=firebaseDatabase.getReference();


        //progress
        circulatProgresBar.setVisibility(View.VISIBLE);
        lst_data.setVisibility(View.INVISIBLE);

  //guncellemenin uzerine tiklayinca
        lst_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Kullanici listedekiKullanici = (Kullanici)parent.getItemAtPosition(position);
                seciliKullanici=listedekiKullanici;
                input_ad.setText(listedekiKullanici.getAd());
                input_email.setText(listedekiKullanici.getEmail());
            }
        });




        //FIREBASE LISTENERE
        listeVeri.child("kullanicilar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(list_kullanicilar.size()>0)
                    list_kullanicilar.clear();

                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Kullanici kullanici=postSnapshot.getValue(Kullanici.class);
                    list_kullanicilar.add(kullanici);

                }
                ListViewAdaptor adaptor=new ListViewAdaptor(MainActivity.this,list_kullanicilar);
                lst_data.setAdapter(adaptor);
                circulatProgresBar.setVisibility(View.INVISIBLE);
                lst_data.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {




            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId()==R.id.menu_ekle){
            kullanici_ekle();
        }
        else if(item.getItemId()==R.id.menu_guncelle){
            kulaniciGuncelle();
        }
        else if(item.getItemId()==R.id.menu_sil){
            kullaniciSil(seciliKullanici);
        }
        return true;
    }

    private void kullaniciSil(Kullanici seciliKullanici) {
        //silme kodlari
        listeVeri.child("kullanicilar").child(seciliKullanici.getUid()).removeValue();
        kontrolTemizle();
    }


    private void kulaniciGuncelle() {
        Kullanici kullanici=new Kullanici(seciliKullanici.getUid(),input_ad.getText().toString(),input_email.getText().toString());
        listeVeri.child("kullanicilar").child(kullanici.getUid()).child("ad").setValue(kullanici.getAd());
        listeVeri.child("kullanicilar").child(kullanici.getUid()).child("email").setValue(kullanici.getEmail());
        kontrolTemizle();

    }

    private void kullanici_ekle() {

    Kullanici kullanici=new Kullanici(UUID.randomUUID().toString(),input_ad.getText().toString(),input_email.getText().toString());

    //veri tabanina veri gonderme

listeVeri.child("kullanicilar").child(kullanici.getUid()).setValue(kullanici);
kontrolTemizle();



    }

    private void kontrolTemizle() {
input_ad.setText("");
input_email.setText("");



    }
}