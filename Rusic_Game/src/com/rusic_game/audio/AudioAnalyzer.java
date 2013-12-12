package com.rusic_game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.analysis.KissFFT;
import com.badlogic.gdx.audio.io.Mpg123Decoder;
import com.badlogic.gdx.files.FileHandle;

public class AudioAnalyzer {
		FileHandle externalFile;
        KissFFT fft;
        short[] samples;
        float[] spectrum;
        Mpg123Decoder decoder;
        AudioDevice device;
        public boolean playing = false;
        public boolean running = true;
        public boolean isAndroid;
        
        public AudioAnalyzer(String file, boolean isAndroid) {
        		this.isAndroid = isAndroid;
                fft = new KissFFT(2048);
                samples = new short[2048];
                spectrum = new float[2048];
                if(isAndroid == true) externalFile = Gdx.files.external(file);
                else{
                	externalFile = Gdx.files.external("tmp/audio-analyze.mp3");
                    Gdx.files.internal(file).copyTo(externalFile);
                }
                decoder = new Mpg123Decoder(externalFile);
                device = Gdx.audio.newAudioDevice(decoder.getRate(), decoder.getChannels() == 1 ? true : false);
        }
        
        private class Playback implements Runnable {
                @Override
                public void run() {
                        int readSamples = 0;
                        
                        // read until we reach the end of the file
                        while (playing && (readSamples = decoder.readSamples(samples, 0, samples.length)) > 0) {
                                // get audio spectrum
                                fft.spectrum(samples, spectrum);
                                // write the samples to the AudioDevice
                                device.writeSamples(samples, 0, readSamples);
                        }
                        running = false;
                }
        }
        
        public void play() {
                Thread playbackThread = new Thread(new Playback());
                playbackThread.setDaemon(true);
                playbackThread.start();
                playing = true;
        }
        
        public void getSpectrum(float[] spectrum) {
                System.arraycopy(this.spectrum, 0, spectrum, 0, this.spectrum.length);
        }
        
        public void togglePlay(){
        	if(playing == true) playing = false;
        	else play();
        }
        
        public void stop() {
                playing = false;
        }
        
        public void dispose() {
                playing = false;
                device.dispose();
                decoder.dispose();
                Gdx.files.external("tmp/audio-analyze.mp3").delete();
        }
}
