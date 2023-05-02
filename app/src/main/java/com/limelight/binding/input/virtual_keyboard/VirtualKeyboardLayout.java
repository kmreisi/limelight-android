package com.limelight.binding.input.virtual_keyboard;

import android.graphics.Color;
import android.view.KeyEvent;

import com.limelight.binding.input.KeyboardTranslator;

public class VirtualKeyboardLayout {

    public static final KeyboardTranslator KT = new KeyboardTranslator();

    public static final VirtualKeyboardLayout ENGLISH =
            new VirtualKeyboardLayout(
                    new Deck[]{
                            new Deck("default",
                                    new Key[]{
                                            new Key("1", new In[]{new In("1")}),
                                            new Key("2", new In[]{new In("2")}),
                                            new Key("3", new In[]{new In("3")}),
                                            new Key("4", new In[]{new In("4")}),
                                            new Key("5", new In[]{new In("5")}),
                                            new Key("6", new In[]{new In("6")}),
                                            new Key("7", new In[]{new In("7")}),
                                            new Key("8", new In[]{new In("8")}),
                                            new Key("9", new In[]{new In("9")}),
                                            new Key("0", new In[]{new In("0")}),
                                            new Key(new ShortCut(KeyEvent.KEYCODE_BUTTON_B, "B", Color.RED), "️️️️⬅", new In[]{new In((short) KeyboardTranslator.VK_BACK_SPACE)}),
                                            new Key("a", new In[]{new In("a")}, new Key[]{new Key("ä", new In[]{new In("ä")}),
                                                    new Key("á", new In[]{new In("á")}),
                                                    new Key("à", new In[]{new In("à")}),
                                                    new Key("â", new In[]{new In("â")})

                                            }),
                                            new Key("b", new In[]{new In("b")}),
                                            new Key("c", new In[]{new In("c")}),
                                            new Key("d", new In[]{new In("d")}),
                                            new Key("e", new In[]{new In("e")}, new Key[]{new Key("é", new In[]{new In("é")}),
                                                    new Key("è", new In[]{new In("è")}),
                                                    new Key("ê", new In[]{new In("ê")})}),
                                            new Key("f", new In[]{new In("f")}),
                                            new Key("g", new In[]{new In("g")}),
                                            new Key("h", new In[]{new In("h")}),
                                            new Key("i", new In[]{new In("i")}),
                                            new Key("j", new In[]{new In("j")}),
                                            new Key("@", new In[]{new In("@")}),
                                            new Key("k", new In[]{new In("k")}),
                                            new Key("l", new In[]{new In("l")}),
                                            new Key("m", new In[]{new In("m")}),
                                            new Key("n", new In[]{new In("n")}),
                                            new Key("o", new In[]{new In("o")}, new Key[]{new Key("ö", new In[]{new In("ö")}),
                                                    new Key("ó", new In[]{new In("ó")}),
                                                    new Key("ò", new In[]{new In("ò")}),
                                                    new Key("ô", new In[]{new In("ô")})}),
                                            new Key("p", new In[]{new In("p")}),
                                            new Key("q", new In[]{new In("q")}),
                                            new Key("r", new In[]{new In("r")}),
                                            new Key("s", new In[]{new In("s")}, new Key[]{new Key("ß", new In[]{new In("ß")}),}),
                                            new Key("t", new In[]{new In("t")}),
                                            new Key(new ShortCut(KeyEvent.KEYCODE_BUTTON_X, "X",
                                                    Color.BLUE), "⬆",
                                                    new IKeyAction[]{new SwitchDeck("caps")}),
                                            new Key("u", new In[]{new In("u")}, new Key[]{new Key("ü", new In[]{new In("ü")}),
                                                    new Key("ú", new In[]{new In("ú")}),
                                                    new Key("ù", new In[]{new In("ù")}),
                                                    new Key("û", new In[]{new In("û")})}),
                                            new Key("v", new In[]{new In("v")}),
                                            new Key("w", new In[]{new In("w")}),
                                            new Key("x", new In[]{new In("x")}),
                                            new Key("y", new In[]{new In("y")}),
                                            new Key("z", new In[]{new In("z")}),
                                            new Key(",", new In[]{new In(",")}),
                                            new Key("-", new In[]{new In("-")}),
                                            new Key(".", new In[]{new In(".")}),
                                            new Key(" ", new In[]{new In(" ")}),
                                            new Key(new ShortCut(KeyEvent.KEYCODE_BUTTON_Y, "Y", Color.YELLOW), "↩", new In[]{new In(KT.translate(KeyEvent.KEYCODE_ENTER, 0))})}),
                            new Deck("caps",
                                    new Key[]{
                                            new Key("1", new In[]{new In("1")}),
                                            new Key("2", new In[]{new In("2")}),
                                            new Key("3", new In[]{new In("3")}),
                                            new Key("4", new In[]{new In("4")}),
                                            new Key("5", new In[]{new In("5")}),
                                            new Key("6", new In[]{new In("6")}),
                                            new Key("7", new In[]{new In("7")}),
                                            new Key("8", new In[]{new In("8")}),
                                            new Key("9", new In[]{new In("9")}),
                                            new Key("0", new In[]{new In("0")}),
                                            new Key(new ShortCut(KeyEvent.KEYCODE_BUTTON_B, "B", Color.RED), "️️️️⬅", new In[]{new In((short) KeyboardTranslator.VK_BACK_SPACE)}),
                                            new Key("A", new In[]{new In("A")}, new Key[]{new Key("Ä", new In[]{new In("Ä")}),
                                                    new Key("Á", new In[]{new In("Á")}),
                                                    new Key("À", new In[]{new In("À")})}),
                                            new Key("B", new In[]{new In("B")}),
                                            new Key("C", new In[]{new In("C")}),
                                            new Key("D", new In[]{new In("D")}),
                                            new Key("E", new In[]{new In("E")}, new Key[]{new Key("É", new In[]{new In("É")}),
                                                    new Key("È", new In[]{new In("È")}),
                                                    new Key("Ê", new In[]{new In("Ê")})}),
                                            new Key("F", new In[]{new In("F")}),
                                            new Key("G", new In[]{new In("G")}),
                                            new Key("H", new In[]{new In("H")}),
                                            new Key("I", new In[]{new In("I")}),
                                            new Key("J", new In[]{new In("J")}),
                                            new Key("@", new In[]{new In("@")}),
                                            new Key("K", new In[]{new In("K")}),
                                            new Key("L", new In[]{new In("L")}),
                                            new Key("M", new In[]{new In("M")}),
                                            new Key("N", new In[]{new In("N")}),
                                            new Key("O", new In[]{new In("O")}, new Key[]{new Key("Ö", new In[]{new In("ö")}),
                                                    new Key("Ó", new In[]{new In("Ó")}),
                                                    new Key("Ò", new In[]{new In("Ò")}),
                                                    new Key("Ô", new In[]{new In("Ô")})}),
                                            new Key("P", new In[]{new In("P")}),
                                            new Key("Q", new In[]{new In("Q")}),
                                            new Key("R", new In[]{new In("R")}),
                                            new Key("S", new In[]{new In("S")}),
                                            new Key("T", new In[]{new In("T")}),
                                            new Key(new ShortCut(KeyEvent.KEYCODE_BUTTON_X, "X", Color.BLUE), "⬆", new IKeyAction[]{new SwitchDeck("default")}),
                                            new Key("U", new In[]{new In("U")}, new Key[]{new Key("Ü", new In[]{new In("Ü")}),
                                                    new Key("Ú", new In[]{new In("Ú")}),
                                                    new Key("Ù", new In[]{new In("Ù")}),
                                                    new Key("Û", new In[]{new In("Û")})}),
                                            new Key("V", new In[]{new In("V")}),
                                            new Key("W", new In[]{new In("W")}),
                                            new Key("X", new In[]{new In("X")}),
                                            new Key("Y", new In[]{new In("Y")}),
                                            new Key("Z", new In[]{new In("Z")}),
                                            new Key(",", new In[]{new In(",")}),
                                            new Key("-", new In[]{new In("-")}),
                                            new Key(".", new In[]{new In(".")}),
                                            new Key(" ", new In[]{new In(" ")}),
                                            new Key(new ShortCut(KeyEvent.KEYCODE_BUTTON_Y, "Y", Color.YELLOW), "↩", new In[]{new In(KT.translate(KeyEvent.KEYCODE_ENTER, 0))})})

                    });

    final Deck[] decks;

    VirtualKeyboardLayout(Deck[] decks) {
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

        /*
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "⬅️",
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "@",
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "⬆️",
        "u", "v", "w", "x", "y", "z", ",", "-", ".", " ", "↩️"
     */

    public static class Deck {

        final String id;
        final Key[] keys;

        Deck(String id, Key[] keys) {
            this.id = id;
            this.keys = keys;
        }
    }
}
