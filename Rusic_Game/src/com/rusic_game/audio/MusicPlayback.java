package com.rusic_game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


public class MusicPlayback {
	private Music selected_music;
	
	public float getPlaybackTime(){
		return selected_music.getPosition();
	}
	public boolean checkMusicIsPlaying(){
		return selected_music.isPlaying();
	}
	public void loadMusic(){
		selected_music = Gdx.audio.newMusic(Gdx.files.internal("data/myfile.mp3"));
	}
	public void startMusic(){
		selected_music.setVolume(1.0f);     // sets the volume, max is 1.0f
		selected_music.setLooping(true);   // repeats song until endMusic() is called
		selected_music.play();             // resumes the playback
	}
	public void pauseMusic(){
		selected_music.pause();         // pauses playback
	}
	public void endMusic(){
		selected_music.stop();
		selected_music.dispose();
	}
}
