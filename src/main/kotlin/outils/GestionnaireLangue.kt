package outils

import java.util.Locale
import java.util.ResourceBundle


/* Gemini Class pour changer et recuperer la langue */
object GestionnaireLangue {
    // Par défaut, on charge le français
    private var bundle: ResourceBundle = ResourceBundle.getBundle("langues.langues", Locale.FRENCH)
    fun changerLangue(langue: String) {
        val locale = when (langue) {
            "English" -> Locale.ENGLISH
            else -> Locale.FRENCH // Français par défaut
        }

        bundle = ResourceBundle.getBundle("langues.langues", locale)    }
        // Fonction pour récupérer le texte traduit
        fun texte(cle: String): String {
            return try {
                bundle.getString(cle)
            } catch (e: Exception) {
                "!$cle!" // Affiche la clé avec des points d'exclamation si on a oublié de la traduire
            }
        }
    }
