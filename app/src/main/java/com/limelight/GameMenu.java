package com.limelight;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import com.limelight.binding.input.GameInputDevice;
import com.limelight.binding.input.KeyboardTranslator;
import com.limelight.nvstream.NvConnection;
import com.limelight.nvstream.input.KeyboardPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide options for ongoing Game Stream.
 * <p>
 * Shown on back action in game activity.
 */
public class GameMenu {

    private static final long TEST_GAME_FOCUS_DELAY = 10;

    public static class MenuOption {
        private final String label;
        private final boolean withGameFocus;
        private final Runnable runnable;

        public MenuOption(String label, boolean withGameFocus, Runnable runnable) {
            this.label = label;
            this.withGameFocus = withGameFocus;
            this.runnable = runnable;
        }

        public MenuOption(String label, Runnable runnable) {
            this(label, false, runnable);
        }
    }

    private final Game game;
    private final NvConnection conn;
    private final GameInputDevice device;

    public GameMenu(Game game, NvConnection conn, GameInputDevice device) {
        this.game = game;
        this.conn = conn;
        this.device = device;

        showMenu();
    }

    private String getString(int id) {
        return game.getResources().getString(id);
    }

    private void openKeyboard() {
        new Handler().postDelayed(() -> {

            game.toggleKeyboard();

        }, 500);
    }

    private void runWithGameFocus(Runnable runnable) {
        // Ensure that the Game activity is still active (not finished)
        if (game.isFinishing()) {
            return;
        }
        // Check if the game window has focus again, if not try again after delay
        if (!game.hasWindowFocus()) {
            new Handler().postDelayed(() -> runWithGameFocus(runnable), TEST_GAME_FOCUS_DELAY);
            return;
        }
        // Game Activity has focus, run runnable
        runnable.run();
    }

    private void run(MenuOption option) {
        if (option.runnable == null) {
            return;
        }

        if (option.withGameFocus) {
            runWithGameFocus(option.runnable);
        } else {
            option.runnable.run();
        }
    }

    private void showMenuDialog(String title, MenuOption[] options) {
        AlertDialog.Builder builder = new AlertDialog.Builder(game);
        builder.setTitle(title);

        final ArrayAdapter<String> actions =
                new ArrayAdapter<String>(game, android.R.layout.simple_list_item_1);

        for (MenuOption option : options) {
            actions.add(option.label);
        }

        builder.setAdapter(actions, (dialog, which) -> {
            String label = actions.getItem(which);
            for (MenuOption option : options) {
                if (!label.equals(option.label)) {
                    continue;
                }

                run(option);
                break;
            }
        });

        builder.show();
    }

    private void showSpecialKeysMenu() {
        showMenuDialog(getString(R.string.game_menu_send_keys), new MenuOption[]{
                new MenuOption(getString(R.string.game_menu_send_keys_esc),
                        () -> game.sendKeys(new short[]{KeyboardTranslator.VK_ESCAPE})),
                new MenuOption(getString(R.string.game_menu_send_keys_f11),
                        () -> game.sendKeys(new short[]{KeyboardTranslator.VK_F11})),
                new MenuOption(getString(R.string.game_menu_send_keys_ctrl_v),
                        () -> game.sendKeys(new short[]{KeyboardTranslator.VK_LCONTROL, KeyboardTranslator.VK_V})),
                new MenuOption(getString(R.string.game_menu_send_keys_win),
                        () -> game.sendKeys(new short[]{KeyboardTranslator.VK_LWIN})),
                new MenuOption(getString(R.string.game_menu_send_keys_win_d),
                        () -> game.sendKeys(new short[]{KeyboardTranslator.VK_LWIN, KeyboardTranslator.VK_D})),
                new MenuOption(getString(R.string.game_menu_send_keys_win_g),
                        () -> game.sendKeys(new short[]{KeyboardTranslator.VK_LWIN, KeyboardTranslator.VK_G})),
                new MenuOption(getString(R.string.game_menu_send_keys_shift_tab),
                        () -> game.sendKeys(new short[]{KeyboardTranslator.VK_LSHIFT, KeyboardTranslator.VK_TAB})),
                new MenuOption(getString(R.string.game_menu_cancel), null),
        });
    }

    private void showMenu() {
        List<MenuOption> options = new ArrayList<>();

        options.add(new MenuOption(getString(R.string.game_menu_toggle_keyboard), true,
                () -> openKeyboard()));

        if (device != null) {
            options.addAll(device.getGameMenuOptions());
        }

        options.add(new MenuOption(getString(R.string.game_menu_send_keys), () -> showSpecialKeysMenu()));
        options.add(new MenuOption(getString(R.string.game_menu_disconnect), () -> game.disconnect()));
        options.add(new MenuOption(getString(R.string.game_menu_cancel), null));

        showMenuDialog("Game Menu", options.toArray(new MenuOption[options.size()]));
    }
}
