-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 17 avr. 2024 à 16:29
-- Version du serveur : 8.0.36
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bddprojetjavalocationvoiture`
--

-- --------------------------------------------------------

--
-- Structure de la table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
CREATE TABLE IF NOT EXISTS `reservations` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `VoitureID` int NOT NULL,
  `LoueurID` int NOT NULL,
  `AgenceID` int NOT NULL,
  `DateDebut` date NOT NULL,
  `DateFin` date NOT NULL,
  `PrixPaye` double NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_reservations_voiture` (`VoitureID`),
  KEY `fk_reservations_loueur` (`LoueurID`),
  KEY `fk_reservations_agence` (`AgenceID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `reservations`
--

INSERT INTO `reservations` (`ID`, `VoitureID`, `LoueurID`, `AgenceID`, `DateDebut`, `DateFin`, `PrixPaye`) VALUES
(1, 1, 1, 1, '2024-04-17', '2024-04-20', 10);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `fk_reservations_agence` FOREIGN KEY (`AgenceID`) REFERENCES `agence` (`ID Agence`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reservations_loueur` FOREIGN KEY (`LoueurID`) REFERENCES `loueur` (`IDLoueur`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reservations_voiture` FOREIGN KEY (`VoitureID`) REFERENCES `voiture` (`ID Voiture`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
