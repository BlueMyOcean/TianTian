package com.jinjiang.computer.tianyi.voice;

import android.os.Bundle;

import com.jinjiang.computer.tianyi.utils.MyApplication;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;


/**
 * Created by Ben on 2016/4/25 0025.
 */
public class Speaker {
    public static SpeechSynthesizer ss = SpeechSynthesizer.createSynthesizer(MyApplication.getMyContext(),null);

    public static void setSs(SpeechSynthesizer ss) {
        Speaker.ss = ss;
    }

    public static SpeechSynthesizer getSs() {
        return ss;
    }

    public Speaker()
    {
        ss.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
        ss.setParameter(SpeechConstant.SPEED,"50");
        ss.setParameter(SpeechConstant.PITCH,"50");
        ss.setParameter(SpeechConstant.VOLUME,"100");
        ss.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        ss.setParameter(SpeechConstant.STREAM_TYPE, "3");
        //ss.setParameter(SpeechConstant.TTS_AUDIO_PATH,"../sdcard/")
    }

    public static int speak(String words)
    {
        return ss.startSpeaking(words,listener);
    }

    public static SynthesizerListener listener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {//暂停


        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

}
