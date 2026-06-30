package outils

import javafx.beans.property.SimpleDoubleProperty
import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.FloatControl
import kotlin.math.log10

// Fichier créé grâce à une requête gemini pour faire le son de fond , on avait jamais fait ça en cours
object Musique {
    private var clip: Clip? = null

    val volumeProperty = SimpleDoubleProperty(1.0)

    init {
        // Écouteur : dès que la propriété change (via le bind), on change le volume du son
        volumeProperty.addListener { _, _, newValue ->
            applyVolume(newValue.toFloat())
        }
    }

    fun playBackgroundMusic(fileName: String) {

            // Re-recherche correcte de la ressource avec getResourceAsStream et ton formatage de chemin
            val inputStream = javaClass.getResourceAsStream("/son/${fileName}.wav")
                ?: throw IllegalArgumentException("Fichier audio introuvable : ${fileName}.wav")

            val bufferedIn = BufferedInputStream(inputStream)
            val audioStream = AudioSystem.getAudioInputStream(bufferedIn)

            clip = AudioSystem.getClip()
            clip?.open(audioStream)
            clip?.loop(Clip.LOOP_CONTINUOUSLY)
            clip?.start()

            // Applique le volume de la propriété au démarrage
            applyVolume(volumeProperty.get().toFloat())

    }

    fun stopMusic() {
        if (clip != null && clip!!.isRunning) {
            clip?.stop()
            clip?.close()
        }
    }

    private fun applyVolume(volume: Float) {
            val currentClip = clip
            if (currentClip != null && currentClip.isOpen) {
                val gainControl = currentClip.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
                val vol = volume.coerceIn(0.0f, 1.0f)
                val dB = if (vol == 0.0f) {
                    gainControl.minimum
                } else {
                    (log10(vol.toDouble()) * 20.0).toFloat().coerceIn(gainControl.minimum, gainControl.maximum)
                }
                gainControl.value = dB
            }
    }
}