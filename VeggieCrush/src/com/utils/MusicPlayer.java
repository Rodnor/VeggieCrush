package com.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer {
	public static Clip loopPanelGame = null;
	public static Clip loopPanelCraft = null;
	public static Clip cutSound = null;
	private static int compteur1 = 0;
	private static int compteur2 = 0;
	public static boolean gameMusicIsMute = false;
	public static boolean craftMusicIsMute = false;

	public MusicPlayer() {
	}

	public static void playGameMusic() {
		if (compteur1 == 0) {
			try {
				loopPanelGame = null;
				try {
					loopPanelGame = AudioSystem.getClip();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
				try {
					loopPanelGame.open(AudioSystem.getAudioInputStream(new File("sounds/game.wav")));
					loopPanelGame.loop(Clip.LOOP_CONTINUOUSLY);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			compteur1++;
		}
	}

	public static void playCraftMusic() {
		if (compteur2 == 0) {
			try {
				loopPanelCraft = null;
				try {
					loopPanelCraft = AudioSystem.getClip();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
				try {
					loopPanelCraft.open(AudioSystem.getAudioInputStream(new File("sounds/craft.wav")));
					loopPanelCraft.loop(Clip.LOOP_CONTINUOUSLY);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			compteur2++;
		}
	}

	public static void playSound() {

		try {
			cutSound = null;
			try {
				cutSound = AudioSystem.getClip();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
			try {
				cutSound.open(AudioSystem.getAudioInputStream(new File("sounds/cut.wav")));
				cutSound.start();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void stopGameMusic() {
		if (compteur1 == 1) {
			compteur1--;
		}
		loopPanelGame.stop();
	}

	public static void stopCraftMusic() {
		if (compteur2 == 1) {
			compteur2--;
		}
		loopPanelCraft.stop();
	}

}
