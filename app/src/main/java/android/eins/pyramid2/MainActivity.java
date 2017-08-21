package android.eins.pyramid2;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

//TODO Create menu
//TODO Add animations
//TODO Add text viewing
//TODO Create comments
//TODO Screen size must be add into PlayingField class

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

//    Layout components
    ConstraintLayout clMainLayout;
    Block[] blocks;
    ImageView[] arrows;
    PlayingField playingField;
    Block selectedBlock;


//    Private fields
    private int level;
    private boolean isTaken;

//    Private constants
    private final int ID_OF_BIGGEST_BLOCK = 1010;
    private final int WIDTH_OF_BIGGEST_BLOCK = 260;
    private final int HEIGHT_OF_BLOCKS = 30;
    private final int BLOCK_BOTTOM_MARGIN = 8;
    private final double MAKE_SMALLER = 0.72;
    private final int ARROWS_ANIMATION_IN = R.anim.alpha_on;
    private final int ARROWS_ANIMATION_OUT = R.anim.alpha_off;
    private final int BLOCK_ANIMATION_IN = R.anim.alpha_on;
    private final int BLOCK_ANIMATION_OUT = R.anim.alpha_on;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Remove notification bar
        this.getWindow().
                setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        clMainLayout = (ConstraintLayout) findViewById(R.id.clMainLayout);

        arrows = new ImageView[3];
        arrows[0] = (ImageView) findViewById(R.id.ivArrow1);
        arrows[1] = (ImageView) findViewById(R.id.ivArrow2);
        arrows[2] = (ImageView) findViewById(R.id.ivArrow3);

        arrowsShow(false);

        clMainLayout.setOnTouchListener(this);

        level = 1;
        isTaken = false;
        starting();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int SC_W = SC_W();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
/////////////////////////////////  LEFT  ///////////////////////////////////////
            if (event.getX() >= 0 && event.getX() <= SC_W/3 ) {
                if (isTaken) {
                    return putBlock(PlayingField.Position.LEFT);
                } else {
                    return takeBlock(PlayingField.Position.LEFT);
                }
            } else
/////////////////////////////////  CENTER  ///////////////////////////////////////
            if (event.getX() >= SC_W/3 && event.getX() <= 2*SC_W/3) {
                if (isTaken) {
                    return putBlock(PlayingField.Position.CENTER);
                } else {
                    return takeBlock(PlayingField.Position.CENTER);
                }
            } else
/////////////////////////////////  RIGHT  ///////////////////////////////////////
            if (event.getX() >= 2*SC_W/3 && event.getX() <= SC_W) {
                if (isTaken) {
                    return putBlock(PlayingField.Position.RIGHT);
                } else {
                    return takeBlock(PlayingField.Position.RIGHT);
                }
            }
        }
        return true;
    }

    boolean putBlock(PlayingField.Position position) {
        Block putTo = playingField.getTop(position);
        if (putTo != null) {
            if (selectedBlock.getId() > putTo.getId())
                return false;

            if (selectedBlock.equals(putTo)) {
                playingField.incrementNumBlocks(position);
                playingField.setTop(selectedBlock, position);
                clearSelected();
                return true;
            }
        }
        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) selectedBlock.getBlock().getLayoutParams();
        layoutParams.bottomToTop = (putTo != null)  ? putTo.getId() : ConstraintLayout.NO_ID;
        layoutParams.bottomToBottom = (putTo == null)  ? clMainLayout.getId() : ConstraintLayout.NO_ID;
        switch (position) {
            case LEFT:
                layoutParams.leftToLeft = clMainLayout.getId();
                layoutParams.leftToRight = ConstraintLayout.NO_ID;
                layoutParams.rightToLeft = R.id.tvLeftMidle;
                layoutParams.rightToRight = ConstraintLayout.NO_ID;
                break;
            case CENTER:
                layoutParams.leftToLeft = clMainLayout.getId();
                layoutParams.leftToRight = ConstraintLayout.NO_ID;
                layoutParams.rightToLeft = ConstraintLayout.NO_ID;
                layoutParams.rightToRight = clMainLayout.getId();
                break;
            case RIGHT:
                layoutParams.leftToLeft = ConstraintLayout.NO_ID;
                layoutParams.leftToRight = R.id.tvRightMidle;
                layoutParams.rightToLeft = ConstraintLayout.NO_ID;
                layoutParams.rightToRight = clMainLayout.getId();
                break;
        }

        selectedBlock.getBlock().setLayoutParams(layoutParams);
        playingField.incrementNumBlocks(position);
        playingField.setTop(selectedBlock, position);
        selectedBlock.setLowerBlock(putTo);
        clearSelected();

        isTaken = false;
        checkingLevelComplite(position);
        return true;
    }

    private void clearSelected() {
        Animation anim = AnimationUtils.loadAnimation(this, BLOCK_ANIMATION_OUT);
        selectedBlock.getBlock().setAnimation(anim);
        selectedBlock.setBlockToDefaulf();
        selectedBlock = null;
        arrowsShow(false);
    }

    boolean takeBlock(PlayingField.Position position) {
        Block taken = playingField.getTop(position);
        selectedBlock = taken;
        if (selectedBlock == null) return false;
        Animation anim = AnimationUtils.loadAnimation(this, BLOCK_ANIMATION_IN);
        selectedBlock.getBlock().setAnimation(anim);
        selectedBlock.setBlockAsSelected();
        arrowsShow(true);
        isTaken = true;
        playingField.decrementNumBlocks(position);
        playingField.setTop(taken.getLowerBlock(), position);
        return true;
    }

    private int SC_W(){ return clMainLayout.getWidth();}

    private void starting() {

        int blocksCount = level + 2;
        blocks = new Block[blocksCount];
        playingField = new PlayingField();

        int currentWidth = WIDTH_OF_BIGGEST_BLOCK;

        for (int i=0; i < blocksCount; i++) {
            ConstraintLayout.LayoutParams layoutParams =
                    new ConstraintLayout.LayoutParams(currentWidth, HEIGHT_OF_BLOCKS);

            layoutParams.bottomToTop = (i > 0)  ? blocks[i-1].getId() : ConstraintLayout.NO_ID;
            layoutParams.bottomToBottom = (i == 0)  ? clMainLayout.getId() : ConstraintLayout.NO_ID;

            layoutParams.leftToLeft = clMainLayout.getId();
            layoutParams.rightToRight = clMainLayout.getId();
            layoutParams.bottomMargin = BLOCK_BOTTOM_MARGIN;

            blocks[i] = new Block(this, clMainLayout, layoutParams, ID_OF_BIGGEST_BLOCK - i);
            blocks[i].setLowerBlock( (i>0) ? blocks[i-1] : null );

            currentWidth = (int)(currentWidth * MAKE_SMALLER);
        }

        playingField.setTop(blocks[blocks.length-1], PlayingField.Position.CENTER);
        playingField.setNumBlocks(blocks.length, PlayingField.Position.CENTER);

    }

    private void arrowsShow (boolean mustShow) {
        int show = (mustShow) ? View.VISIBLE : View.INVISIBLE;
        Animation anim = (mustShow) ? AnimationUtils.loadAnimation(this, ARROWS_ANIMATION_IN)
                : AnimationUtils.loadAnimation(this, ARROWS_ANIMATION_OUT);
        for(int i=0; i<3; i++) {
            arrows[i].setAnimation(anim);
            arrows[i].setVisibility(show);
        }
    }

    private void checkingLevelComplite(PlayingField.Position position){
        if (position == PlayingField.Position.CENTER) return;
        if (playingField.getNumBlocks(position) == blocks.length) {
            Toast.makeText(this, "YOU WIN!!!", Toast.LENGTH_LONG).show();
            level = (level == 5) ? 1 : level+1;
            for (Block block: blocks)
                clMainLayout.removeView(block.getBlock());
            starting();
        }
    }
}
