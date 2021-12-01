package edu.mssu.cis385.ballapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.mssu.cis385.ballapp.model.Player;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PlayerClassTest {
    @Test
    public void player_StringConcatenate() {
        Player player = new Player("Drake"+" "+"Josh");
        assertEquals(player.getText(), "Drake Josh");
    }

    @Test
    public void player_InitAndGetName() {
        Player player = new Player("Drake Bell");
        assertEquals(player.getText(), "Drake Bell");
    }

    @Test
    public void player_InitSetAndGetName() {
        Player player = new Player("Drake Bell");
        player.setText("Josh Peck");
        assertEquals(player.getText(), "Josh Peck");
    }
}