package com.limelight.binding.input.virtual_keyboard;

import android.graphics.Color;
import android.view.KeyEvent;

import com.limelight.binding.input.KeyboardTranslator;

import java.util.ArrayList;
import java.util.List;

public class VirtualKeyboardLayout {

    public static final KeyboardTranslator KT = new KeyboardTranslator();

    public static final Key SKIP = new Key("", new IKeyAction[0]);

    private static final Key BACK = new Key(new ShortCut(KeyEvent.KEYCODE_BUTTON_B, "B",
            Color.RED), "️️️️⬅", new In[]{new In((short) KeyboardTranslator.VK_BACK_SPACE)});

    private static final Key DEF =  new Key(new ShortCut(KeyEvent.KEYCODE_BUTTON_L1, "L1",
            Color.MAGENTA), "⬇️", new IKeyAction[]{new SwitchDeck("def")});

    private static final Key DEF_CAPS =  new Key(new ShortCut(KeyEvent.KEYCODE_BUTTON_L1, "L1",
            Color.MAGENTA), "⬆", new IKeyAction[]{new SwitchDeck("def_caps")});

    private static final Key ALT =  new Key(new ShortCut(KeyEvent.KEYCODE_BUTTON_R1, "R1",
            Color.MAGENTA), "²", new IKeyAction[]{new SwitchDeck("alt")});

    private static final Key ALT_CAPS =  new Key(new ShortCut(KeyEvent.KEYCODE_BUTTON_R1, "R1",
            Color.MAGENTA), "²", new IKeyAction[]{new SwitchDeck("alt_caps")});

    private static final Key ENTER = new Key(new ShortCut(KeyEvent.KEYCODE_BUTTON_Y, "Y",
            Color.YELLOW), "↩", new In[]{new In(KT.translate(KeyEvent.KEYCODE_ENTER, 0))});

    private static final Key SPACE = new Key(new ShortCut(KeyEvent.KEYCODE_BUTTON_X, "X",
            Color.BLUE), " ", new In[]{new In((short)KeyboardTranslator.VK_SPACE)});

    // TODO: This layout was done by a german and works at least for german and english ;-).
    //       It would be good to clean this up and support language specific ones maybe also
    //       loading of custom ones (JSON file?!).

    private static final Deck[] LATIN_DECKS = new Deck[]{
            Deck.create("def",
                    "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", DEF_CAPS,
                    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", BACK,
                    "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", ALT,
                    "u", "v", "w", "x", "y", "z", ",", "-", ".", SPACE, ENTER
            ),
            Deck.create("def_caps",
                    "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", DEF,
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", BACK,
                    "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", ALT_CAPS,
                    "U", "V", "W", "X", "Y", "Z", ";", "_", ":", SPACE, ENTER
            ),
            Deck.create("alt",
                    "ä", "â", "ê", "ç", "ö", "ô", "ü", "û", "ß", "@", DEF,
                    "à", "á", "è", "é", "ò", "ó", "ù", "ú", "°", "'", BACK,
                    "?", "!", "&", "$", "€", "%", "+", "~", "*", "=", ALT_CAPS,
                    "(", ")", "[", "]", "{", "}", "<", ">", "/", "\\", ENTER
            ),
            Deck.create("alt_caps",
                    "Ä", "Â", "Ê", "Ç", "Ö", "Ô", "Ü", "Û", "ẞ", "@", DEF_CAPS,
                    "À", "Á", "È", "É", "Ò", "Ó", "Ù", "Ú", "^", "\"", BACK,
                    "¿", "¡", "#", "µ", "|", SKIP, SKIP, SKIP, SKIP, SKIP, ALT,
                    SKIP, SKIP, SKIP, SKIP, SKIP, SKIP, SKIP, SKIP, SKIP, SKIP, ENTER
            )
    };

    public static final VirtualKeyboardLayout EN =
            new VirtualKeyboardLayout("English", 11, 4, LATIN_DECKS);
    public static final VirtualKeyboardLayout DE =
            new VirtualKeyboardLayout("German", 11, 4, LATIN_DECKS);
    public static final VirtualKeyboardLayout FR = new VirtualKeyboardLayout("French",
            11, 4, LATIN_DECKS);

    public static final VirtualKeyboardLayout[] LAYOUTS = new VirtualKeyboardLayout[]{EN,
            DE, FR};

    public final String name;
    final int columns;
    final int rows;

    final Deck[] decks;

    VirtualKeyboardLayout(String name, int columns, int rows, Deck[] decks) {
        this.name = name;
        this.columns = columns;
        this.rows = rows;
        this.decks = decks;
    }

    public interface IKeyAction {
        void doIt(VirtualKeyboard keyboard);
    }

    public static class In implements IKeyAction {
        final short[] keys;
        final String text;

        In(final short[] keys) {
            this.keys = keys;
            this.text = null;
        }

        In(final int key) {
            this.keys = new short[]{(short) key};
            this.text = null;
        }

        In(final String text) {
            this.keys = null;
            this.text = text;
        }

        @Override
        public void doIt(VirtualKeyboard keyboard) {
            if (this.text != null) {
                keyboard.game.getConn().sendUtf8Text(text);
            }
            if (this.keys != null) {
                keyboard.game.sendKeys(keys);
            }
        }
    }

    public static class SwitchDeck implements IKeyAction {

        String id;

        SwitchDeck(String id) {
            this.id = id;
        }

        @Override
        public void doIt(VirtualKeyboard keyboard) {
            Deck next = null;
            for (Deck deck : keyboard.keyboardLayout.decks) {
                if (deck.id.equals(id)) {
                    next = deck;
                    break;
                }
            }
            if (next == null) {
                return;
            }
            int idx = 0;
            for (VirtualKeyboard.KeyField keyField : keyboard.keys) {
                keyField.key = next.keys[idx++];
                keyField.invalidate();
            }
        }
    }

    public static class ShortCut {
        final int code;
        final String icon;
        final int color;

        ShortCut(int code, String icon, int color) {
            this.code = code;
            this.icon = "(" + icon + ")";
            this.color = color;
        }
    }

    public static class Key {
        final ShortCut shortcut;

        final String text;
        final IKeyAction[] actions;
        final Key[] children;

        Key(ShortCut shortcut, String text, IKeyAction[] actions, Key[] children) {
            this.shortcut = shortcut;
            this.text = text;
            this.actions = actions;
            this.children = children;
        }


        Key(String text, IKeyAction[] actions, Key[] children) {
            this.shortcut = null;
            this.text = text;
            this.actions = actions;
            this.children = children;
        }

        Key(ShortCut shortcut, String text, IKeyAction[] actions) {
            this.shortcut = shortcut;
            this.text = text;
            this.actions = actions;
            this.children = null;
        }

        Key(String text, IKeyAction[] actions) {
            this.shortcut = null;
            this.text = text;
            this.actions = actions;
            this.children = null;
        }
    }



    public static class Deck {

        final String id;
        final Key[] keys;

        Deck(String id, Key[] keys) {
            this.id = id;
            this.keys = keys;
        }

        static Deck create(String id, Object ... keys) {
            List<Key> list = new ArrayList<>();
            for (Object key : keys) {
                if (key instanceof Key) {
                    list.add((Key)key);
                }
                if (key instanceof String) {
                    list.add(new Key((String)key, new In[]{new In((String)key)}));
                }
            }

            return new Deck(id, list.toArray(new Key[list.size()]));
        }
    }
}
