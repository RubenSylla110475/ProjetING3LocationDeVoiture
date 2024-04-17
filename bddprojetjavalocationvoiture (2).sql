-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 17 avr. 2024 à 16:30
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
-- Structure de la table `agence`
--

DROP TABLE IF EXISTS `agence`;
CREATE TABLE IF NOT EXISTS `agence` (
  `Nom` text NOT NULL,
  `Adresse` text NOT NULL,
  `Email` text NOT NULL,
  `Tel` text NOT NULL,
  `ID Agence` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID Agence`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `agence`
--

INSERT INTO `agence` (`Nom`, `Adresse`, `Email`, `Tel`, `ID Agence`) VALUES
('Paris', '57 Rue de Vaugirard', 'agence1@edu.ece.fr', '0788888888', 1),
('Marseille', '16 rue du Coq', 'AgenceMarseille@edu.ece.fr', '0756879341', 2),
('Nice', '2 Avenue Baquis', 'AgenceNice@edu.ece.fr', '0783456921', 3),
('Lyon', '230 Rue André Philip', 'AgenceLyon@edu.ece.fr', '0684506831', 4);

-- --------------------------------------------------------

--
-- Structure de la table `loueur`
--

DROP TABLE IF EXISTS `loueur`;
CREATE TABLE IF NOT EXISTS `loueur` (
  `Nom` text NOT NULL,
  `Prenom` text NOT NULL,
  `Email` text NOT NULL,
  `Tel` text NOT NULL,
  `Type` int NOT NULL COMMENT '1/Admin 2/NouveauClients 3/Adhérents',
  `MotdePasse` text NOT NULL,
  `IDLoueur` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IDLoueur`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `loueur`
--

INSERT INTO `loueur` (`Nom`, `Prenom`, `Email`, `Tel`, `Type`, `MotdePasse`, `IDLoueur`) VALUES
('Sylla', 'Ruben', 'ruben.sylla@edu.ece.fr', '0781740946', 1, 'ruben2003', 1),
('Admin1', 'PAdmin1', 'admin@edu.ece.fr', '1111111111', 2, 'admin', 10);

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

-- --------------------------------------------------------

--
-- Structure de la table `voiture`
--

DROP TABLE IF EXISTS `voiture`;
CREATE TABLE IF NOT EXISTS `voiture` (
  `Modele` varchar(50) NOT NULL,
  `Marque` varchar(50) NOT NULL,
  `Immatriculation` varchar(50) NOT NULL,
  `Kilometrage` double NOT NULL,
  `Couleur` varchar(50) NOT NULL,
  `Louée` tinyint(1) NOT NULL DEFAULT '0',
  `IDLoueur` int DEFAULT NULL,
  `Localisation` int NOT NULL,
  `Date début de Location` date DEFAULT NULL,
  `Date fin de Location` date DEFAULT NULL,
  `Prix/Jour` double NOT NULL,
  `Image` varchar(500) NOT NULL,
  `ID Voiture` int NOT NULL AUTO_INCREMENT,
  `Classe` text NOT NULL,
  PRIMARY KEY (`ID Voiture`),
  KEY `IDLoueur` (`IDLoueur`),
  KEY `Localisation` (`Localisation`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `voiture`
--

INSERT INTO `voiture` (`Modele`, `Marque`, `Immatriculation`, `Kilometrage`, `Couleur`, `Louée`, `IDLoueur`, `Localisation`, `Date début de Location`, `Date fin de Location`, `Prix/Jour`, `Image`, `ID Voiture`, `Classe`) VALUES
('Clio 4', 'Renault', 'KM-777-PS', 100, 'Blanc', 0, NULL, 3, NULL, NULL, 70, 'Clio4.jpg', 1, 'Compacte'),
('C3', 'Citroen', 'RN-114-SA', 4560, 'Blanc', 0, NULL, 2, NULL, NULL, 80, 'CitroenC3.jpg', 2, 'Compacte'),
('Panda', 'Fiat', 'LC-698-RT', 30545, 'Blanc', 0, NULL, 4, NULL, NULL, 45, 'FiatPanda.jpg', 3, 'Mini'),
('IX', 'BMW', 'BM-678-TR', 1000, 'Blanc', 0, NULL, 3, NULL, NULL, 150, 'BMWIX.jpg', 4, 'Electrique'),
('Serie 3', 'BMW', 'JK-678-PM', 5607, 'Blanc', 0, NULL, 1, NULL, NULL, 96, 'BMW3.jpg', 5, 'Berline'),
('Série 1', 'BMW', 'TY-532-KH', 40605, 'Blanc', 0, NULL, 1, NULL, NULL, 45, 'BMW1.png', 6, 'Compacte'),
('308e', 'Peugeot', 'AB-444-AB', 560, 'Vert foncé', 0, NULL, 2, NULL, NULL, 560, 'Peugeot308Elec.jpg', 7, 'Electrique'),
('Evoque', 'Range Rover', 'TY-784-BV', 6708, 'Blanc', 0, NULL, 1, NULL, NULL, 87, 'RangeRover.png', 8, '4x4'),
('208', 'Peugeot', 'TY-678-FD', 50847, 'Jaune', 0, NULL, 1, NULL, NULL, 35, 'Peugeot208.jpg', 9, 'Mini'),
('Modele S', 'Tesla', 'TY-798-FS', 769, 'Blanc', 0, NULL, 1, NULL, NULL, 78, 'Tesla.jpg', 10, 'Electrique');

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

--
-- Contraintes pour la table `voiture`
--
ALTER TABLE `voiture`
  ADD CONSTRAINT `voiture_ibfk_1` FOREIGN KEY (`IDLoueur`) REFERENCES `loueur` (`IDLoueur`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `voiture_ibfk_2` FOREIGN KEY (`Localisation`) REFERENCES `agence` (`ID Agence`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
