package com.example.fefteen;

import java.util.Random;

import com.example.fefteen.R.drawable;

import android.app.Activity;
//import android.app.Fragment.SavedState;
import android.os.Bundle;
//import android.os.storage.OnObbStateChangeListener;
//import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  {
	LinearLayout lin1,lin2,lin3,lin4;
	Button b[][]= new Button[4][4];
	Button b1;
	TextView v;
    ImageView img;
	int pole[][]=new int[4][4];//массив поля
	int save[] = new int [16]; //массив для сохранения данных при повороте
	int nextRow = 0;		   //переменная для обозначения Layout
	int nomber = 1;			   //переменная для надписей кнопок
	int step = 0;			   //количество ходов
	static final String state = "STATE_LOAD";
    static final String state_array = "STATE_ARRAY";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        setContentView(R.layout.activity_main);
        lin1 = (LinearLayout)findViewById(R.id.lay1);
        lin2 = (LinearLayout)findViewById(R.id.lay2);
        lin3 = (LinearLayout)findViewById(R.id.lay3);
        lin4 = (LinearLayout)findViewById(R.id.lay4);       
        b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(new mAdapter());     
        v = (TextView)findViewById(R.id.textView1);
        img = (ImageView)findViewById(R.id.imageView1);     
        for (int i=0;i<4;i++){
        	for(int j = 0;j<4;j++){
        		b[i][j] = new Button(this);
        		b[i][j].setText(""+nomber);
        		b[i][j].setOnClickListener(new mAdapter(i,j));
        		b[i][j].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0.33f));  	
        		if (nextRow==0)
        			lin1.addView(b[i][j]);
        		if (nextRow==1)
            		lin2.addView(b[i][j]);
        		if (nextRow==2)
            		lin3.addView(b[i][j]);
        		if (nextRow==3)
            		lin4.addView(b[i][j]);   
        		pole[i][j]=nomber;
        		b[i][j].setId(nomber);
        		nomber++;
        	}
        	nextRow++;
        }
        b[3][3].setText("");         
        if (savedInstanceState==null){
        	Mixer();
    	    newGame();
        }
        if (savedInstanceState!=null){
        	save = savedInstanceState.getIntArray(state_array);
        	step = savedInstanceState.getInt(state);   	
        	saveMixer();
        	newGame();       
        }     
        v.setText("Количество ходов: "+step);   
    }
  
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	outState.putInt(state, step);
    	outState.putIntArray(state_array, save);
    	super.onSaveInstanceState(outState);   
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){ 	 	
    	super.onRestoreInstanceState(savedInstanceState);
    	step = savedInstanceState.getInt(state);
    	save = savedInstanceState.getIntArray(state_array);
    }
      
    class mAdapter implements OnClickListener{
    	int posi,posj;
    	mAdapter(int posI,int posJ){
    	   this.posi=posI;
    	   this.posj=posJ;
    	}
    	mAdapter(){
    	}
		@Override
		public void onClick(View arg0) {
			if (arg0.getId()==R.id.button1){
				step = 0;
				Mixer();
				newGame();
			}
			else{
				if((posj != 0) && (pole[posi][posj-1] == 16)){
					pole[posi][posj-1] = pole[posi][posj];
					pole[posi][posj]=16;
					b[posi][posj].setText("");
					b[posi][posj-1].setText(""+pole[posi][posj-1]);
					step++;
					v.setText("Количество ходов: "+step);
				} 
			else{
				if((posj != 3) && (pole[posi][posj+1] == 16)){
					pole[posi][posj+1] = pole[posi][posj];
					pole[posi][posj]=16;
					b[posi][posj].setText("");
					b[posi][posj+1].setText(""+pole[posi][posj+1]);
					step++;
					v.setText("Количество ходов: "+step);
			    } 
			else{		  
				if((posi != 0) && (pole[posi-1][posj] == 16)){
				    pole[posi-1][posj] = pole[posi][posj];
				    pole[posi][posj]=16;
				    b[posi][posj].setText("");
				    b[posi-1][posj].setText(""+pole[posi-1][posj]);
				    step++;
					v.setText("Количество ходов: "+step);
				} 
			else{
				if((posi != 3) && (pole[posi+1][posj] == 16)){
				    pole[posi+1][posj] = pole[posi][posj];
					pole[posi][posj]=16;
					b[posi][posj].setText("");
					b[posi+1][posj].setText(""+pole[posi+1][posj]);
					step++;
					v.setText("Количество ходов: "+step);
				} 		  
			}
			}
		    } 
		    int k=1; 
			for (int i=0;i<4;i++){
				for (int j=0;j<4;j++){
					if (pole[i][j]==k)k++;
					else 
						break;
				 }
			}	
			
			
			int kk = 0;
			for (int i=0;i<4;i++){
				for(int j = 0;j<4;j++){
	    	         save[kk] = pole[i][j]; kk++;
	    	    } 
	    	}
			if (k==17){
				for (int i=0;i<4;i++)
					for (int j=0;j<4;j++)
						b[i][j].setEnabled(false);
				img.setImageResource(drawable.fuck);
				Toast t= Toast.makeText(getApplicationContext(), "Цель достигнута!", Toast.LENGTH_LONG);
				t.setGravity(Gravity.CENTER, 0, 0); 	
				t.show();
		    }						  
			}					
		}   	
    }
    
    
    
    public void Mixer(){
    	nomber=1;
    	for (int i=0;i<4;i++){
    		for(int j = 0;j<4;j++){
    			pole[i][j]=nomber;
    			nomber++;
    		}
    	}
    	int x1,y1;
    	int x2,y2;
    	int d;
    	(new Random()).nextInt();
    	x1=3;y1=3;
    	for (int i=0;i<150;i++){
    		do{
    			x2=x1;y2=y1;
    			d = (int)(Math.random()*4);
    			switch (d){
    				case 0:{x2--;break;}
    				case 1:{x2++;break;}
    				case 2:{y2--;break;}
    				case 3:{y2++;break;}
    			}
    		}while ((x2<0)||(x2>=4)||(y2<0)||(y2>=4));
    	pole[y1][x1]=pole[y2][x2];
    	pole[y2][x2]=16;
    	x1=x2;
    	y1=y2;
    	}
    	int k=0;
    	for (int i=0;i<4;i++){
    		for(int j = 0;j<4;j++){
    			save[k] = pole[i][j]; k++;
    	       } 
    	}
    }

    public void saveMixer(){
    	int k=0;
    	for (int i=0;i<4;i++){
    	       for(int j = 0;j<4;j++){
    	    	   pole[i][j]=save[k];k++;
    	       }
    	}  	
    }

    public void newGame(){
    	for(int i = 0;i<4;i++)
    		for(int j = 0;j<4;j++){
    			if(pole[i][j]!=16)b[i][j].setText(""+pole[i][j]);
    			else 
    				b[i][j].setText("");
    		}
    	v.setText("");
    	for (int i=0;i<4;i++)
    		for (int j=0;j<4;j++)
    			b[i][j].setEnabled(true);
    	img.setImageResource(drawable.troll);
    }
    
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(0, 0, 0, "Как играть?");
		menu.add(0, 1, 0, "О приложении");
		menu.add(0, 2, 0, "Выход"); 	
    	return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
	   		case 0:
	   			Toast.makeText(this, "Cоберите числа в порядке возрастания от 1 до 15.", Toast.LENGTH_LONG).show();
	   			break;
	   		case 1:
	   			Toast.makeText(this, "Игра fefTeen v 1.5\nРазработка и поддержка: Киселев Владимир\nВсе права защищены, 04.01.2015", Toast.LENGTH_LONG).show();
	   			break;
	   		case 2:
	   			finish();
	   			break;
	    }
	    return true;
   }
          
}
