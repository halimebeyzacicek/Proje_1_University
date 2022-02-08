package com.example.readexcelfile;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class MainActivity extends AppCompatActivity {

    private TextView txtvalue;    //textView'e isim vermiş olduk.
    private EditText txtname;

    public void init(){
        txtvalue = (TextView) findViewById(R.id.textView);  //textView'e ulaşmaya çalışıyoruz.
        txtname = (EditText) findViewById(R.id.editText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();     //init fonksiyonunu çağırıyoruz.

    }
    public void order(View v){//order fonksiyonu butona basıldığı an çalışacak.(main.xml'de ayarladık bunu)
        try{
            String number = txtname.getText().toString();   //EditText okuma/yazma
            txtvalue.setText(number);
            int number_int = Integer.parseInt(number);

            AssetManager am=getAssets();                                 //Excel dosyasını okuma
            InputStream is=am.open("ogr-fizik-1-notları.xls");
            Workbook wb=Workbook.getWorkbook(is);
            Sheet s=wb.getSheet(0);
            int row=s.getRows();
            int col=s.getColumns();

            String ogr_no="";
            String ogr_ad_soyad;
            String ogr_ders;
            String ogr_vize="";
            String ogr_final="";
            int aranan_satir=0;

            for (int i=1;i<row;i++){//satır          //Girilen numaranın hangi satırda olduğunu bulma.
                int j=0;//sutun
                Cell bulma1=s.getCell(j,i);
                ogr_no=bulma1.getContents();
                int aranan_no_int = Integer.parseInt(ogr_no);
                if(aranan_no_int == number_int){                  //Eğer bulunduysa aranan satırı i'yi aranan_satira eşitle.
                    aranan_satir=i;
                }
            }

            Cell ad=s.getCell(1,aranan_satir);  //Aranan öğrencini adını alma.(1.sutün)
            ogr_ad_soyad=ad.getContents();

            Cell soyad=s.getCell(2,aranan_satir);  //Aranan öğrencini soyadını alma.(2.sutün)
            ogr_ad_soyad+="\n"+soyad.getContents();

            Cell ders=s.getCell(3,aranan_satir);  //Aranan öğrencini ders adını alma.(3.sutün)
            ogr_ders=ders.getContents();

            Cell vize=s.getCell(4,aranan_satir);     //Aranan öğrencini vizesini alma.(4.sutün)
            ogr_vize=vize.getContents();
            int vize1 = Integer.parseInt(ogr_vize);

            Cell finall=s.getCell(5,aranan_satir);      //Aranan öğrencini finalini alma.(5.sutün)
            ogr_final=finall.getContents();
            int final1 = Integer.parseInt(ogr_final);

            float sonuc= (float) ((vize1*0.4)+(final1*0.6));  //vize %40 + final %60


            display2(ogr_ad_soyad);   //textView'de yadırma.
            display3(sonuc);
            display4(ogr_ders);
        }
        catch(Exception e)
        {
            System.out.println("Hata"+ e);
        }

    }

    public void display2(String value){   //TextView2'ye yazma fonksiyonu.
        TextView y=(TextView)findViewById(R.id.textView2);
        y.setText("Adı ve Soyadı:\n"+value);
    }

    public void display3(float value){
        TextView y=(TextView)findViewById(R.id.textView3);
        y.setText( "Ortalama="+value);
    }
    public void display4(String value){
        TextView y=(TextView)findViewById(R.id.textView6);
        y.setText( "Ders="+value);
    }
}
