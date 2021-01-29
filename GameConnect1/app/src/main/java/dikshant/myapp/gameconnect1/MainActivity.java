package dikshant.myapp.gameconnect1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // 0:Yellow 1:Red
    int player = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningStates = {
            {0,1,2} , {3,4,5}, {6,7,8} , // rows
            {0,3,6} , {1,4,7} , {2,5,8} , // col
            {0,4,8} , {2,4,6} // diagonals
    };
    public void gameClicker(View view){
        ImageView counter = (ImageView)view;
        int tapped = Integer.parseInt(counter.getTag().toString());

        if(gameState[tapped] != 2){
            return;
        }

        gameState[tapped] = player;
        counter.setTranslationY(-1100);
        if(player == 0){
            //yellow will play
            counter.setImageResource(R.drawable.yellow);
            player = 1;
        }else{
            //red will play
            counter.setImageResource(R.drawable.red);
            player = 0;
        }
        counter.animate().translationYBy(1100).rotation(3600).setDuration(300);

        String winner = "Won";
        for(int[] winningState : winningStates){
            if(gameState[winningState[0]] == gameState[winningState[1]] && gameState[winningState[1]] == gameState[winningState[2]] && gameState[winningState[0]] != 2){
                if(player == 1){
                    winner = "Yellow " + winner;
                }else{
                    winner = "Red " + winner;
                }
                MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.win);
                mediaPlayer.start();


                TextView winnerEditText = (TextView) findViewById(R.id.winnerTextView);
                winnerEditText.setText(winner);
                winnerEditText.setVisibility(View.VISIBLE);

                Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                playAgainButton.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    public void playAgain(View view){
        player = 0;
        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }
        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid);
        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ImageView cell = (ImageView) gridLayout.getChildAt(i);
            cell.setImageDrawable(null);
        }
        TextView winnerEditText = (TextView) findViewById(R.id.winnerTextView);
        winnerEditText.setVisibility(View.INVISIBLE);

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}