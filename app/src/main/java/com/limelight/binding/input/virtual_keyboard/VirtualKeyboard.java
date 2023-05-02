package com.limelight.binding.input.virtual_keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.limelight.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class VirtualKeyboard {

    /*package*/ final List<KeyField> keys = new ArrayList<>();
    /*package*/ final Game game;
    private final FrameLayout layout;
    /*package*/ VirtualKeyboardLayout keyboardLayout = VirtualKeyboardLayout.LAYOUTS[0];
    private int deck = 0;
    private int moveDirection;
    private Timer moveTimer;


    public VirtualKeyboard(FrameLayout layout, final Game game) {

        this.layout = layout;
        this.game = game;

        DisplayMetrics screen = game.getResources().getDisplayMetrics();

        // The keyboard has border of on col and one row on each side
        int key_width = screen.widthPixels / (keyboardLayout.columns + 2);
        int key_height = (screen.heightPixels / 2) / (keyboardLayout.rows + 2);

        int yOffset = (screen.heightPixels / 2);

        int pos = 0;
        for (int row = 1; row <= keyboardLayout.rows; ++row) {
            for (int col = 1; col <= keyboardLayout.columns; ++col) {
                VirtualKeyboardLayout.Key key = keyboardLayout.decks[deck].keys[pos];
                int x = col * key_width;
                int y = yOffset + row * key_height;

                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(key_width, key_height);
                layoutParams.setMargins(x, y, 0, 0);

                KeyField field = new KeyField(game, key, col, row);
                keys.add(field);
                layout.addView(field, layoutParams);
                ++pos;
            }
        }
    }

    public void show() {
        for (KeyField key : keys)
            key.setVisibility(View.VISIBLE);

        layout.requestLayout();
    }

    private KeyField findCurrent() {
        for (KeyField key : keys) {
            if (key.focus) {
                return key;
            }
        }

        return null;
    }

    private Point findNext(Point current, int direction) {

        int col = current.x;
        int row = current.y;

        if ((direction & Direction.LEFT.code) != 0) {
            --col;
        }
        if ((direction & Direction.RIGHT.code) != 0) {
            ++col;
        }
        if ((direction & Direction.UP.code) != 0) {
            ++row;
        }
        if ((direction & Direction.DOWN.code) != 0) {
            --row;
        }

        if (col > 11) {
            col = 1;
        }

        if (col < 1) {
            col = 11;
        }

        if (row > 4) {
            row = 1;
        }

        if (row < 1) {
            row = 4;
        }

        return new Point(col, row);

    }

    private KeyField findField(Point pos) {
        for (KeyField k : keys) {
            if (k.pos.equals(pos)) {
                return k;
            }
        }

        return null;
    }

    private void move(int direction) {

        KeyField current = findCurrent();
        KeyField next = current;

        while(next != null) {
            Point nextPos = findNext(next.pos, direction);
            next = findField(nextPos);
            if (next.key != VirtualKeyboardLayout.SKIP) {
                break;
            }
        }

        if (next == null) {
            next = keys.get(0);
        }

        if (current != null) {
            current.focus = false;
            current.invalidate();
        }

        next.focus = true;
        next.invalidate();
    }

    public List<KeyField> getKeys() {
        return keys;
    }

    public boolean handleKeyDown(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BUTTON_A) {
            KeyField field = findCurrent();
            if (field != null) {
                field.click();
            }
        }

        for (KeyField field : keys) {
            if (field.key.shortcut != null && field.key.shortcut.code == event.getKeyCode()) {
                field.click();
                break;
            }
        }

        return true;
    }

    private void moveWithDebounce(int direction) {

        if (direction == 0) {
            if (moveTimer != null) {
                moveTimer.cancel();
                moveTimer = null;
            }
            return;
        }

        moveDirection = direction;

        if (moveTimer == null) {
            moveTimer = new Timer();
            moveTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    move(moveDirection);
                }
            }, 0, 250);
        }
    }

    public boolean handleMotionEvent(View view, MotionEvent event) {
        int sources = event.getSource();

        if (!(((sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD) || ((sources & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK))) {
            return false;
        }

        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;

        if ((actionCode == MotionEvent.ACTION_UP) || actionCode == MotionEvent.ACTION_CANCEL) {
            moveWithDebounce(0);
            return true;
        }


        if ((actionCode == MotionEvent.ACTION_DOWN) || actionCode == MotionEvent.ACTION_MOVE) {

            int direction = 0;

            float dead = 0.3f;
            float x = event.getX() + event.getAxisValue(MotionEvent.AXIS_HAT_X);
            float y = event.getY() + event.getAxisValue(MotionEvent.AXIS_HAT_Y);

            if (x < -dead) {
                direction |= Direction.LEFT.code;
            }
            if (x > +dead) {
                direction |= Direction.RIGHT.code;
            }
            if (y > +dead) {
                direction |= Direction.UP.code;
            }
            if (y < -dead) {
                direction |= Direction.DOWN.code;
            }

            moveWithDebounce(direction);
        }

        return true;
    }


    private enum Direction {
        NONE(0b0000), UP(0b0001), RIGHT(0b0010), DOWN(0b0100), LEFT(0b1000);


        private final int code;

        Direction(int code) {
            this.code = code;
        }
    }

    class KeyField extends View {

        private final Point pos;
        private final Paint paint;
        /*package*/ VirtualKeyboardLayout.Key key;
        private boolean clicked = false;
        private boolean focus = false;

        public KeyField(Context context, VirtualKeyboardLayout.Key key, int col, int row) {
            super(context);
            this.key = key;
            this.pos = new Point(col, row);

            paint = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            paint.setShadowLayer(0, 0, 0, Color.GRAY);
            paint.setColor(clicked ? Color.RED : (focus ? Color.BLUE : Color.GRAY));
            paint.setStyle(Paint.Style.FILL);

            paint.setAlpha(0xFF / 2);
            canvas.drawPaint(paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(40);

            paint.setTextAlign(Paint.Align.CENTER);

            if (!key.text.isEmpty()) {
                canvas.drawText(key.text, (canvas.getWidth() / 2), (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)), paint);

                canvas.drawLine(0, 0, getWidth(), 0, paint);
                canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), paint);
                canvas.drawLine(getWidth(), getHeight(), 0, getHeight(), paint);
                canvas.drawLine(0, getHeight(), 0, 0, paint);

                VirtualKeyboardLayout.ShortCut shortcut = key.shortcut;
                if (shortcut != null) {
                    paint.setShadowLayer(20, 0, 0, Color.BLACK);
                    paint.setColor(shortcut.color);
                    paint.setTextSize(30);
                    canvas.drawText(shortcut.icon, canvas.getWidth() - 30, (int) (canvas.getHeight() - 30), paint);
                }
            }
        }

        void run(VirtualKeyboardLayout.IKeyAction[] actions) {
            if (actions == null || actions.length == 0) {
                return;
            }

            VirtualKeyboardLayout.IKeyAction action = actions[0];
            action.doIt(VirtualKeyboard.this);

            if (actions.length > 1) {
                new Handler().postDelayed((() -> {
                    run(Arrays.copyOfRange(actions, 1, actions.length));
                }), 10);
            }
        }

        public void click() {
            clicked = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    clicked = false;
                    invalidate();
                }
            }, 25);
            invalidate();

            run(key.actions);
        }

    }

}
