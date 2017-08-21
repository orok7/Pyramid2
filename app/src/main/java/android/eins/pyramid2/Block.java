package android.eins.pyramid2;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

class Block {
    private ImageView block;
    private Block lowerBlock;

    private final int src = R.drawable.block;
    private final int selected = R.drawable.block_selected;

    Block(Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams, int blockId) {
            block = new ImageView(context);
            setBlockToDefaulf();
            block.setScaleType(ImageView.ScaleType.FIT_XY);
            block.setId(blockId);
            parent.addView(block, layoutParams);
        }

    int getId() {
            return block.getId();
        }

    ImageView getBlock() {
            return block;
        }

    public Block getLowerBlock() {
        return lowerBlock;
    }

    public void setLowerBlock(Block lowerBlock) {
        this.lowerBlock = lowerBlock;
    }

    public void setBlockAsSelected() {
        block.setImageResource(selected);
    }

    public void setBlockToDefaulf() {
        block.setImageResource(src);
    }

    @Override
    public String toString() {
        return "Block{" +
                "block=" + String.valueOf((block!=null)?block.getWidth():"null") +
                ", lowerBlock=" + String.valueOf( (lowerBlock!=null)?lowerBlock.getBlock().getWidth():"null") +
                '}';
    }
}
