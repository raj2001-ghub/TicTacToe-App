package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int turn=0,cnt=9;
    private int startX=0,startY=0,endX=0,endY=0;
    private boolean winStatus=false;  // this winStatus helps us to know whether any one has won or not
    private TextView px,po;
    private View cutStroke00h,cutStroke00v,cutStroke01,cutStroke02,cutStroke10,cutStroke20,cutStroke11;
    private int pxid,poid;
    private ImageButton[][]gameArray =new ImageButton[3][3];
    private AppCompatButton rbutton;
    private int[][]matrix=new int[3][3];
    private int[][]idArray= new int [3][3];
    private ScaleAnimation sc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbutton=findViewById(R.id.resetButton);
        rbutton.setOnClickListener(this);
        pxid=R.id.player_x;
        poid=R.id.player_o;
        cutStroke00h=findViewById(R.id.cut_stroke00h);
        cutStroke00v=findViewById(R.id.cut_stroke00v);
        cutStroke01=findViewById(R.id.cut_stroke01);
        cutStroke02=findViewById(R.id.cut_stroke02);
        cutStroke10=findViewById(R.id.cut_stroke10);
        cutStroke20=findViewById(R.id.cut_stroke20);
        cutStroke11=findViewById(R.id.cut_stroke11);
        cutStroke00h.setVisibility(View.INVISIBLE);  // we set the visibility to invisible initially
        cutStroke00v.setVisibility(View.INVISIBLE);
        cutStroke01.setVisibility(View.INVISIBLE);
        cutStroke02.setVisibility(View.INVISIBLE);
        cutStroke10.setVisibility(View.INVISIBLE);
        cutStroke20.setVisibility(View.INVISIBLE);
        cutStroke11.setVisibility(View.INVISIBLE);
        px=findViewById(R.id.player_x);
        po=findViewById(R.id.player_o);
        Toast.makeText(this,"Player X start the game",Toast.LENGTH_SHORT).show();
        turnoff(po);
        turnon(px);
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                matrix[i][j]=-1;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                String id="button"+i+j;
                int gameID = getResources().getIdentifier(id, "id", getPackageName());
                idArray[i][j]=gameID;
                gameArray[i][j] = findViewById(gameID);
                gameArray[i][j].setOnClickListener(this);  // this is basically an instance of the class it is in
            }                                              // so we have inherited the interface View.OnClickListener which contains the default method declaration
        }                                                  // public void onClick(View v)
    }
    public boolean isButton(int id)
    {
        if(id==R.id.button00||id==R.id.button01||id==R.id.button02||id==R.id.button10||id==R.id.button11||id==R.id.button12||id==R.id.button20||id==R.id.button21||id==R.id.button22)
            return true;
        return false;
    }

    public Pair<Integer,Integer> findId(int id)
    {
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(idArray[i][j]==id)
                {
                    return new Pair<Integer,Integer>(i,j);
                }
        return new Pair<Integer,Integer>(-1,-1);
    }
    public boolean animationEffects(View v,float fromX,float toX,float fromY,float toY )
    {
        v.setVisibility(View.VISIBLE);
        sc=new ScaleAnimation(fromX,toX,fromY,toY);
        sc.setDuration(1000);
        sc.setFillAfter(true);
        v.startAnimation(sc);
        return true;
    }
    public boolean animationEffectsCross(View v,float degrees,float scaleX)
    {
        v.setRotation(degrees);
        v.setVisibility(View.VISIBLE);
        sc=new ScaleAnimation(1f,8f,1f,8f); /// here we create a dummy instance of ScaleAnimation class
        v.setScaleX(scaleX);
        return true;
    }
    public boolean isWin()
    {
        if(matrix[0][0]==matrix[0][1]&&matrix[0][1]==matrix[0][2]&&matrix[0][1]!=-1)
            return  animationEffects(cutStroke00h,1f,8f,1f,1f);
        else if(matrix[1][0]==matrix[1][1]&&matrix[1][1]==matrix[1][2]&&matrix[1][0]!=-1)
            return animationEffects(cutStroke10,1f,8f,1f,1f);
        else if(matrix[2][0]==matrix[2][1]&&matrix[2][1]==matrix[2][2]&&matrix[2][0]!=-1)
            return animationEffects(cutStroke20,1f,8f,1f,1f);
        else if(matrix[0][0]==matrix[1][0]&&matrix[1][0]==matrix[2][0]&&matrix[0][0]!=-1)
            return animationEffects(cutStroke00v,1f,1f,1f,10f);
        else if(matrix[0][1]==matrix[1][1]&&matrix[1][1]==matrix[2][1]&&matrix[0][1]!=-1)
            return animationEffects(cutStroke01,1f,1f,1f,10f);
        else if(matrix[0][2]==matrix[1][2]&&matrix[1][2]==matrix[2][2]&&matrix[0][2]!=-1)
            return animationEffects(cutStroke02,1f,8f,1f,1f);
        else if(matrix[0][0]==matrix[1][1]&&matrix[1][1]==matrix[2][2]&&matrix[0][0]!=-1)
            return animationEffectsCross(cutStroke11,48,10f);
        else if(matrix[0][2]==matrix[1][1]&&matrix[1][1]==matrix[2][0]&&matrix[0][2]!=-1)
            return animationEffectsCross(cutStroke11,-48,10f);

        return false;
    }
    public void resetGame()
    {
        turn=0;
        cnt=9;
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
            {
                matrix[i][j]=-1;
                gameArray[i][j].setImageResource(android.R.color.transparent);
                gameArray[i][j].setEnabled(true);
            }
        Toast.makeText(this,"Player X start the game",Toast.LENGTH_SHORT).show();
        turnoff(po);
        turnon(px);
        /// here we return the cutStroke to initial condition
        cutStroke00h.setVisibility(View.INVISIBLE);
        cutStroke00v.setVisibility(View.INVISIBLE);
        cutStroke10.setVisibility(View.INVISIBLE);
        cutStroke20.setVisibility(View.INVISIBLE);
        cutStroke01.setVisibility(View.INVISIBLE);
        cutStroke02.setVisibility(View.INVISIBLE);
        cutStroke11.setVisibility(View.INVISIBLE);
        cutStroke11.setRotation(0);
        cutStroke11.setScaleX(1f);
        sc.setFillAfter(false); //// very important line
        /// if we remove this the animation effect will persist on the screen even when we make the image invisible
    }
    public void winX()
    {
        CharSequence text = "Player X has won !!!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }
    public void winO()
    {
        CharSequence text = "Player O has won !!!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }
    public void drawGame()
    {
        CharSequence text = "There's a tie !!!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
        resetGame();
    }
    public void turnoff(TextView v)
    {
        v.setTextColor(Color.parseColor("#171717"));
        if(v.getId()==pxid)
            v.setShadowLayer(6 ,0,0,Color.parseColor("#780001"));
        else if(v.getId()==poid)
            v.setShadowLayer(6 ,0,0,Color.parseColor("#0c4f9e"));
        v.setTextSize(25);
    }
    public void turnon(TextView v)
    {
        if(v.getId()==pxid)
        {
            v.setShadowLayer(20 ,0,0,Color.parseColor("#c30606"));
            v.setTextColor(Color.parseColor("#e30808"));
        }
        else if(v.getId()==poid)
        {
            v.setShadowLayer(25 ,0,0,Color.parseColor("#3C0AF3"));
            v.setTextColor(Color.parseColor("#45BEED"));
        }
        v.setTextSize(30);
    }
    public void disableButtons()
    {
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                gameArray[i][j].setEnabled(false);
    }
    @Override
    public void onClick(View v)
    {
        if(isButton(v.getId()))
        {
            cnt--;
            int indexi,indexj=-1;
            Pair<Integer,Integer>index=findId(v.getId());
            indexi=index.first;
            indexj=index.second;
            ImageButton img=findViewById(v.getId());
            if(turn%2==0)      // this is the turn of player X
            {                  // put 1 in matrix array for X
                matrix[indexi][indexj]=1;
                img.setImageResource(R.drawable.cross3);
                turnoff(px);
                turnon(po);
                ///check the win condition for player x
                if(isWin())
                {
                    winX();
                    disableButtons();
                    return;
                }
            }
            else            // this is turn for player O
            {               // put 0 in matrix array for O
                matrix[indexi][indexj]=0;
                img.setImageResource(R.drawable.osymbol3);
                turnoff(po);
                turnon(px);
                /// check the win condition for player o
                if(isWin())
                {
                    winO();
                    disableButtons();
                    return;
                }
            }
            img.setEnabled(false); // it will make that particular button unclicakble
            // it should be reset to true when we reset the game once again
            // or when the game is finished and turn should be set to 0
            // then we should make all the buttons clickable
            turn++;
            ////check draw condition
            if(cnt==0)
            {
                drawGame();
                return;
            }
        }
        else if(v.getId()==R.id.resetButton)
            resetGame();
    }
}
//    THIS IS FOR SCALE ANIMATION
//    ScaleAnimation sc=new ScaleAnimation(1f,1f,1f,2f);
//            sc.setDuration(1000);
//                    cutStroke.startAnimation(sc);


//    THIS IS FOR ROTATE ANIMATION
//    RotateAnimation rot=new RotateAnimation(0,45,RotateAnimation.RELATIVE_TO_SELF,.5f,RotateAnimation.RELATIVE_TO_SELF,.5f);
//            rot.setDuration(1000);
//                    cutStroke.startAnimation(rot);
//                    cutStroke.setVisibility(View.INVISIBLE);