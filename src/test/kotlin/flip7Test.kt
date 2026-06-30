import io.mockk.Runs
import iut.info1.flip7.Flip7
import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteStop
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.verify
import iut.info1.flip7.cartes.CarteSpeciale
import iut.info1.flip7.cartes.OutilsCarte
import iut.info1.flip7.etats.EtatJoueur
import iut.info1.flip7.etats.EtatPartie
import iut.info1.flip7.exceptions.CarteInvalideException
import iut.info1.flip7.exceptions.ListeJoueursInvalideException
import iut.info1.flip7.exceptions.NombreJoueursInvalideException
import iut.info1.flip7.exceptions.ScoreFinPartieInvalideException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.fail
import iut.info1.flip7.exceptions.EtatPartieInvalideException
import iut.info1.flip7.exceptions.PiocheInvalideException

/**
 * Classe de tests unitaires pour la classe [Flip7].
 * * Permet de vérifier les comportements de base de la classe "Flip7" ainsi que different scénario de jeu :
 * @author équipe 1-2
 */

class flip7Test {

    private fun constructeur(): MutableList<Carte> {

        /**
         * Création de la pioche avec l'insertions de toutes les cartes 79 cartes numéro, 6 cartes bonus et 9 cartes spéciales
         */
        val listeCartes = mutableListOf<Carte>()

        /* utilisation de l'ia pour dire il y a 1 carte -> 0, 1 cartes -> 1 etc... puis ajout des cartes dans la liste*/
        val valeursNumeriques = mutableListOf<Int>()
        valeursNumeriques.add(0)
        for (valeur in 1..12) {
            repeat(valeur) {
                valeursNumeriques.add(valeur)
            }
        }

        for (valeurSpecifique in valeursNumeriques) {
            val carteNumMock = mockk<CarteNum>(relaxed = true)
            every { carteNumMock.estCarteNum() } returns true
            every { carteNumMock.valeur } returns valeurSpecifique
            listeCartes.add(carteNumMock)
        }

        /*fin de l'ia*/

        val valeursBonusPlus = listOf(2, 4, 6, 8, 10)
        for (i in 0 until 5) {
            val carteBonusPlusMock = mockk<CarteBonusPlus>()
            every { carteBonusPlusMock.estCarteBonusPlus() } returns true
            every { carteBonusPlusMock.estCarteBonusMultiplie() } returns false
            every { carteBonusPlusMock.valeur } returns valeursBonusPlus[i]
            listeCartes.add(carteBonusPlusMock)
        }

        val carteBonusMultiplieMock = mockk<CarteBonusMultiplie>()
        every { carteBonusMultiplieMock.estCarteBonusPlus() } returns false
        every { carteBonusMultiplieMock.estCarteBonusMultiplie() } returns true
        every { carteBonusMultiplieMock.valeur } returns 2
        listeCartes.add(carteBonusMultiplieMock)

        for (i in 0 until 3) {
            val c = mockk<Carte2ndeChance>()
            every { c.estCarte2ndeChance() } returns true
            every { c.valeur } returns 0
            listeCartes.add(c)
        }
        for (i in 0 until 3) {
            val c = mockk<Carte3aLaSuite>()
            every { c.estCarte3aLaSuite() } returns true
            every { c.valeur } returns 0
            listeCartes.add(c)
        }
        for (i in 0 until 3) {
            val c = mockk<CarteStop>()
            every { c.estCarteStop() } returns true
            every { c.valeur } returns 0
            listeCartes.add(c)
        }

        return listeCartes
    }

    /**
     * Vérification du constructeur avec 2 joueurs
     */
    @Test
    fun CT1_constructeurValideDeuxJoueurs() {
        val nbJoueurs = 2
        val joueur1 = mockk<IJoueur>()
        val joueur2 = mockk<IJoueur>()
        val listeJoueurs = listOf(joueur1, joueur2)
        val scoreFinPartie = 200
        val listeCartes = constructeur()

        val jeuFlip7 = Flip7(
            nbJoueurs = nbJoueurs,
            joueurs = listeJoueurs,
            cartes = listeCartes,
            debug = false,
            scoreFinPartie = scoreFinPartie
        )

        assertEquals(nbJoueurs, jeuFlip7.nbJoueurs)
        assertEquals(listeJoueurs, jeuFlip7.joueurs)
        assertEquals(scoreFinPartie, jeuFlip7.scoreFinPartie)
        assertEquals(false, jeuFlip7.debug)
    }

    /**
     * Vérification de la levée d'exception avec un nombre insufissant de joueur
     */
    @Test
    fun CT2_depassementPasAssezDeJoueur(){
        val nbJoueurs = 1
        val joueur1 = mockk<IJoueur>()
        val listeJoueurs = listOf(joueur1)
        val scoreFinPartie= 200
        val listeCartes = constructeur()

        assertThrows<NombreJoueursInvalideException> {
            Flip7(
                nbJoueurs = nbJoueurs,
                joueurs = listeJoueurs,
                cartes = listeCartes,
                debug = false,
                scoreFinPartie = scoreFinPartie
            )
        }
    }

    /**
     * Vérification de la levée d'exception avec trop de joueurs
     */
    @Test
    fun CT3_depassementTropDeJoueur(){
        val nbJoueurs = 5
        val listeJoueurs = mutableListOf<IJoueur>()
        for (i in 0 until 5) {
            val joueurMock = mockk<IJoueur>()
            listeJoueurs.add(joueurMock)
        }
        val scoreFinPartie = 200
        val listeCartes = constructeur()

        assertThrows<NombreJoueursInvalideException> {
            Flip7(
                nbJoueurs = nbJoueurs,
                joueurs = listeJoueurs,
                cartes = listeCartes,
                debug = false,
                scoreFinPartie = scoreFinPartie
            )
        }
    }

    /**
     * Vérification du debug a True
     */
    @Test
    fun CT4_debugTrue() {
        val nbJoueurs = 2
        val joueur1 = mockk<IJoueur>()
        val joueur2 = mockk<IJoueur>()
        val listeJoueurs = listOf(joueur1, joueur2)
        val scoreFinPartie = 200
        val listeCartes = constructeur()

        val jeuFlip7 = Flip7(
            nbJoueurs = nbJoueurs,
            joueurs = listeJoueurs,
            cartes = listeCartes,
            debug = true,
            scoreFinPartie = scoreFinPartie
        )

        assertEquals(true, jeuFlip7.debug)
    }

    /**
     * Vérification de la levée d'exception avec un score négatif
     */
    @Test
    fun CT5_scoreFinalNegatif() {
        val nbJoueurs = 2
        val joueur1 = mockk<IJoueur>()
        val joueur2 = mockk<IJoueur>()
        val listeJoueurs = listOf(joueur1, joueur2)
        val scoreFinPartie = -100
        val listeCartes = constructeur()

        assertThrows<ScoreFinPartieInvalideException> {
            Flip7(
                nbJoueurs = nbJoueurs,
                joueurs = listeJoueurs,
                cartes = listeCartes,
                debug = false,
                scoreFinPartie = scoreFinPartie
            )
        }
    }

    /**
     * Vérification de la levée d'exception avec un nombre de joueurs supérieux au nombre de Ijoueur
     */
    @Test
    fun CT6_listeJoueurDifferentNbJoueur() {
        val nbJoueurs = 4
        val joueur1 = mockk<IJoueur>()
        val joueur2 = mockk<IJoueur>()
        val listeJoueurs = listOf(joueur1, joueur2)
        val scoreFinPartie = 200
        val listeCartes = constructeur()

        assertThrows<ListeJoueursInvalideException> {
            Flip7(
                nbJoueurs = nbJoueurs,
                joueurs = listeJoueurs,
                cartes = listeCartes,
                debug = false,
                scoreFinPartie = scoreFinPartie
            )
        }
    }

    /**
     * Vérification de la levée d'exception avec un nombre de Ijouer supéérieux au nombre de joueur
     */
    @Test
    fun CT7_listeJoueurDifferentNbJoueur2() {
        val nbJoueurs = 2
        val liste = mutableListOf<IJoueur>()
        for ( i in 0 until 3){
            var joeur = mockk<IJoueur>()
            liste.add(joeur)
        }
        val scoreFinPartie = 200
        val listeCartes = constructeur()

        assertThrows<ListeJoueursInvalideException> {
            Flip7(
                nbJoueurs = nbJoueurs,
                joueurs = liste,
                cartes = listeCartes,
                debug = false,
                scoreFinPartie = scoreFinPartie
            )
        }
    }

    /**
     * Vérification du joueur qui pioche toute les cartes
     */
    @Test
    fun CT8_testJoueurCourantPiocheToutesLesCartes(){
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs
        val liste = mutableListOf<IJoueur>()
        for ( i in 0 until 3){
            var joeur = mockk<IJoueur>()
            liste.add(joeur)
        }
        val pioche = mutableListOf<Carte>(
            CarteNum(2), CarteBonusPlus(8), CarteBonusMultiplie(), Carte2ndeChance(), CarteStop(), Carte3aLaSuite()
        )
        val jeu = Flip7(3,liste,pioche,true,200)

        var cartePiochee = jeu.joueurCourantPiocheUneCarte()
        assertTrue(cartePiochee is CarteNum)

        cartePiochee = jeu.joueurCourantPiocheUneCarte()
        assertTrue(cartePiochee is CarteBonusPlus)

        cartePiochee = jeu.joueurCourantPiocheUneCarte()
        assertTrue(cartePiochee is CarteBonusMultiplie)

        cartePiochee = jeu.joueurCourantPiocheUneCarte()
        assertTrue(cartePiochee is Carte2ndeChance)

        cartePiochee = jeu.joueurCourantPiocheUneCarte()
        assertTrue(cartePiochee is CarteStop)
        jeu.joueurCourantCibleStop(cartePiochee, 1)

        cartePiochee = jeu.joueurCourantPiocheUneCarte()
        assertTrue(cartePiochee is Carte3aLaSuite)
    }

    /**
     * Vérification de la levée d'exception un joeur qui pioche une carte sur une pioche vide
     */
    @Test
    fun CT9_PiocheUneCartePiocheVide() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        var joueur1 = mockk<IJoueur>()
        var joueur2 = mockk<IJoueur>()
        var joueur3 = mockk<IJoueur>()

        var liste = listOf<IJoueur>(joueur1, joueur2, joueur3)
        val pioche = mutableListOf<Carte>()

        val jeu = Flip7(3, liste, pioche, false, 200)

        try {
            jeu.joueurCourantPiocheUneCarte()
        } catch (e: PiocheInvalideException) {

        }
    }

    /**
     * Début des scénario de jeu, expliqué dans le rapport.md, disponible
     */

    @Test
    fun CT10_testPiocheSupprimeCarte() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(3) { mockk<IJoueur>() }

        val carte1 = CarteNum(2)
        val carte2 = CarteNum(5)

        val pioche = mutableListOf<Carte>(carte1, carte2)

        val jeu = Flip7(3, joueurs, pioche, false, 200)

        val tailleAvant = jeu.taillePioche
        val cartePiochee = jeu.joueurCourantPiocheUneCarte()
        val tailleApres = jeu.taillePioche
        assertEquals(tailleAvant - 1, tailleApres)
    }

    @Test
    fun CT11_joueurCourantCibleStop(){
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        var joeur1 = mockk<IJoueur>()
        var joeur2 = mockk<IJoueur>()
        var joeur3 = mockk<IJoueur>()
        var liste = listOf<IJoueur>(joeur1,joeur2,joeur3)
        val pioche = mutableListOf<Carte>(
            CarteStop()
        )
        val jeu = Flip7(3, liste, pioche, false, 200)
        val carte = jeu.joueurCourantPiocheUneCarte()
        assertTrue(carte is CarteStop)
        jeu.joueurCourantCibleStop(carte, 1)
        assertEquals(EtatJoueur.STOP, jeu.etatJoueur[1])
    }

    @Test
    fun CT12_test3aLaSuiteFaitPerdre() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(3) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>(Carte3aLaSuite(), CarteNum(8), CarteNum(8), CarteNum(6))

        val jeu = Flip7(3, joueurs, pioche, true, 200)

        val cartePiochee = jeu.joueurCourantPiocheUneCarte()

        jeu.joueurCourantCible3aLaSuite(cartePiochee, 1)

        assertEquals(EtatJoueur.PERDU, jeu.etatJoueur[1])
    }

    @Test
    fun CT13_testJoueurDitStop() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(3) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>()

        val jeu = Flip7(3, joueurs, pioche, false, 200)

        jeu.joueurCourantDitStop()
        assertEquals(EtatJoueur.STOP, jeu.etatJoueur[0])
    }

    @Test
    fun CT14_testJoueurPerd() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(2) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>( CarteNum(8), CarteNum(7), CarteNum(8))

        val jeu = Flip7(2, joueurs, pioche, true, 200)

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()

        assertEquals(EtatJoueur.PERDU, jeu.etatJoueur[0])
    }

    @Test
    fun CT15_testJoueurJoueEncore() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(2) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>(Carte2ndeChance(), CarteNum(7), CarteNum(8), CarteNum(8), CarteNum(8))

        val jeu = Flip7(2, joueurs, pioche, true, 200)

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()

        assertEquals(EtatJoueur.JOUE_ENCORE, jeu.etatJoueur[0])
        assertFalse(jeu.main[0]!!.contains(Carte2ndeChance()))
        assertTrue(jeu.main[0]!!.contains(CarteNum(8)))
        print(jeu.main)
    }

    @Test
    fun CT16_scoreMancheNecessiteEtatMancheTerminee() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(2) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>(Carte2ndeChance(), CarteNum(7), CarteNum(8), CarteNum(8), CarteNum(8))

        val jeu = Flip7(2, joueurs, pioche, true, 200)
        assertThrows<EtatPartieInvalideException> { jeu.scoreManche() }
    }

    @Test
    fun CT17_scoreMancheJoueurMarque0() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(2) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>( CarteNum(5), CarteNum(0), CarteNum(5))

        val jeu = Flip7(2, joueurs, pioche, true, 200)

        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        val scores = jeu.scoreManche()
        assertEquals(0, scores[1])
    }

    @Test
    fun CT18_scoreMancheJoueurMarque5() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(2) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>( CarteNum(5), CarteNum(5), CarteNum(5))

        val jeu = Flip7(2, joueurs, pioche, true, 200)

        jeu.joueurCourantPiocheUneCarte() // joueur 0 pioche 5
        jeu.joueurCourantPiocheUneCarte() // joueur 1 pioche 0
        jeu.joueurCourantDitStop() // joueur 0 stop -> Perdu
        jeu.joueurCourantDitStop()        // joueur 1 dit stop → MANCHE_TERMINEE

        val scores = jeu.scoreManche()
        assertEquals(5, scores[1])
    }

    @Test
    fun CT19_scoreMancheJoueurPerduMarque5() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(2) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>( CarteNum(5), CarteNum(5), CarteNum(5))

        val jeu = Flip7(2, joueurs, pioche, true, 200)

        jeu.joueurCourantPiocheUneCarte() // joueur 0 pioche 5
        jeu.joueurCourantPiocheUneCarte() // joueur 1 pioche 0
        jeu.joueurCourantPiocheUneCarte() // joueur 0 pioche 5 → PERDU
        jeu.joueurCourantDitStop()        // joueur 1 dit stop → MANCHE_TERMINEE

        val scores = jeu.scoreManche()
        assertEquals(5, scores[1])
    }

    @Test
    fun CT20_testEtatPartie() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(2) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>(CarteStop(), Carte3aLaSuite(), CarteNum(8), CarteNum(7), CarteNum(6))

        val jeu = Flip7(2, joueurs, pioche, true, 200)

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)

        val carteStop = jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, jeu.etatPartie)

        jeu.joueurCourantCibleStop(carteStop, 1)
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)

        val carte3aLaSuite = jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, jeu.etatPartie)

        jeu.joueurCourantCible3aLaSuite(carte3aLaSuite, 0)
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)

        jeu.joueurCourantDitStop()
        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)

        jeu.scoreManche()
        assertEquals(EtatPartie.NOUVELLE_MANCHE, jeu.etatPartie)
    }

    @Test
    fun CT21_testEtatPartieTerminee() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(2) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>( CarteNum(12), CarteNum(5), CarteNum(11), CarteNum(6), CarteNum(10), CarteNum(7),
            CarteNum(9), CarteNum(2), CarteNum(8), CarteNum(1))

        val jeu = Flip7(2, joueurs, pioche, true, 50)

        jeu.joueurCourantPiocheUneCarte() //12
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte() //11
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte() //10
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte() //9
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte() //8
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop() //fin
        jeu.joueurCourantDitStop()
        jeu.scoreManche()
        assertEquals(EtatPartie.PARTIE_TERMINEE, jeu.etatPartie)
    }

    @Test
    fun CT22_scoreMancheScoreCumuleApresDeuxManches() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(2) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>( CarteNum(12))

        val jeu = Flip7(2, joueurs, pioche, true, 50)

        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()
        jeu.scoreManche()
        val scoreApresM1 = jeu.score[0]!!

        jeu.nouvelleManche()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()
        jeu.scoreManche()
        val scoreApresM2 = jeu.score[0]!!

        assertTrue(scoreApresM2 >= scoreApresM1)
    }

    @Test
    fun CT23_scoreManchePrendEnCompteBonusPlus() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(2) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>( CarteNum(5), CarteBonusPlus(10))

        val jeu = Flip7(2, joueurs, pioche, true, 50)

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()
        jeu.scoreManche()
        assertEquals(15, jeu.score[0])
    }

    @Test
    fun CT24_scoreManchePrendEnCompteBonusMultiplie() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(2) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>( CarteNum(1), CarteBonusMultiplie())

        val jeu = Flip7(2, joueurs, pioche, true, 50)

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()
        jeu.scoreManche()
        assertEquals(2, jeu.score[0])
    }

    @Test
    fun CT25_ExceptionEtatPartie() {
        mockkConstructor(OutilsCarte()::class)
        every { anyConstructed<OutilsCarte>().verifiePiocheInitiale(any()) } just Runs

        val joueurs = List(2) { mockk<IJoueur>() }
        val pioche = mutableListOf<Carte>( CarteStop(), Carte3aLaSuite(), CarteNum(5))

        val jeu = Flip7(2, joueurs, pioche, true, 50)

        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        try {
            jeu.joueurCourantDitStop()
        } catch (e: EtatPartieInvalideException) {

        }

        try {
            jeu.joueurCourantPiocheUneCarte()
        } catch (e: EtatPartieInvalideException) {

        }

        try {
            jeu.joueurCourantCibleStop(pioche[0], 1)
        } catch (e: EtatPartieInvalideException) {

        }

        try {
            jeu.joueurCourantCible3aLaSuite(pioche[1], 1)
        } catch (e: EtatPartieInvalideException) {

        }
    }
}