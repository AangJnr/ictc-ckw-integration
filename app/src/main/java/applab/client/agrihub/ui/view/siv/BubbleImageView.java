package applab.client.agrihub.ui.view.siv;

import android.content.Context;
import android.util.AttributeSet;

import applab.client.agrihub.ui.view.siv.shader.BubbleShader;
import applab.client.agrihub.ui.view.siv.shader.ShaderHelper;

public class BubbleImageView extends ShaderImageView {

    public BubbleImageView(Context context) {
        super(context);
    }

    public BubbleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BubbleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new BubbleShader();
    }
}
