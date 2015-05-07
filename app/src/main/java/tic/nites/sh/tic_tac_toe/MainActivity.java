package tic.nites.sh.tic_tac_toe;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    //Whose turn is it? false = X and true = O.
    private boolean tacsTurn = false;
    private char board[][] = new char[3][3];

    public void setupOnClickListeners() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);

        for(int y = 0; y < T.getChildCount(); y++){
            if(T.getChildAt(y) instanceof TableRow){
                TableRow R = (TableRow) T.getChildAt(y);
                for(int x = 0; x < R.getChildCount(); x ++){
                    View V = R.getChildAt(x);
                    V.setOnClickListener(new PlayOnClick(x,y));

                }
            }
        }

    }

    public void newGame(View view){
        tacsTurn = false;
        board = new char[3][3];
        resetButtons();

    }


    private void resetButtons(){
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof Button) {
                        Button B = (Button) R.getChildAt(x);
                        B.setText("");
                        B.setEnabled(true);
                    }
                }
            }
        }
        TextView t = (TextView) findViewById(R.id.titleText);
        t.setText(R.string.title);
    }

    private boolean checkWinner(char[][] board, int size, char player) {
        // check each column
        for (int x = 0; x < size; x++) {
            int total = 0;
            for (int y = 0; y < size; y++) {
                if (board[x][y] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // check each row
        for (int y = 0; y < size; y++) {
            int total = 0;
            for (int x = 0; x < size; x++) {
                if (board[x][y] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // forward diag
        int total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == y && board[x][y] == player) {
                    total++;
                }
            }
        }
        if (total >= size) {
            return true; // they win
        }

        // backward diag
        total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x + y == size - 1 && board[x][y] == player) {
                    total++;
                }
            }
        }
        if (total >= size) {
            return true; // they win
        }

        return false; // nobody won
    }

    private void disableButtons() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof Button) {
                        Button B = (Button) R.getChildAt(x);
                        B.setEnabled(false);
                    }
                }
            }
        }
    }

    /**
     * Method that returns true when someone has won and false when nobody has.<br />
     * It also display the winner on screen.
     *
     * @return
     */
    private boolean checkWin() {

        char winner = '\0';
        if (checkWinner(board, 3, 'X')) {
            winner = 'X';
        } else if (checkWinner(board, 3, 'O')) {
            winner = 'O';
        }

        if (winner == '\0') {
            return false; // nobody won
        } else {
            // display winner
            TextView T = (TextView) findViewById(R.id.titleText);
            T.setText(winner + " wins");
            return true;
        }
    }

    private class PlayOnClick implements View.OnClickListener {

        private int x = 0;
        private int y = 0;

        public PlayOnClick(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public void onClick(View view) {
            if(view instanceof Button){
                Button B = (Button) view;
                board[x][y] = (tacsTurn ? 'O' : 'X');
                B.setText(tacsTurn ? "O" : "X");
                B.setEnabled(false);
                tacsTurn = !tacsTurn;

                // check if anyone has won
                if (checkWin()) {
                    disableButtons();
                }

            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupOnClickListeners();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
