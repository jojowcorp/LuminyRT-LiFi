-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : sam. 07 jan. 2023 à 23:06
-- Version du serveur : 8.0.27
-- Version de PHP : 8.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `minesr_Lq5JFfwR`
--
CREATE DATABASE IF NOT EXISTS `minesr_Lq5JFfwR` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `minesr_Lq5JFfwR`;

-- --------------------------------------------------------

--
-- Structure de la table `Point`
--

CREATE TABLE `Point` (
  `id` int NOT NULL,
  `Name` varchar(64) DEFAULT NULL,
  `connexion` varchar(64) DEFAULT NULL,
  `xPos` smallint NOT NULL DEFAULT '0',
  `yPos` smallint NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `Point`
--

INSERT INTO `Point` (`id`, `Name`, `connexion`, `xPos`, `yPos`) VALUES
(1, 'Sortie', '30', 626, 332),
(2, 'Alexandrie', '31', 593, 237),
(3, 'Cayenne', '32', 880, 221),
(4, 'Foyer', '33', 475, 153),
(5, 'Patras', '35', 344, 118),
(6, 'Haifa', '35', 320, 118),
(7, 'Corinthe', '36', 267, 117),
(8, 'Hambourg', '37', 215, 153),
(9, 'St Petersbourg', '37', 189, 198),
(10, 'Cadix', '34', 295, 197),
(11, 'St Sebastien', '34', 320, 197),
(12, 'New Orleans', '54', 371, 597),
(13, 'Miami', '53', 314, 595),
(14, 'New York', '52', 215, 596),
(15, 'Quebec', '51', 188, 636),
(16, 'Vancouver', '51', 263, 680),
(17, 'Los Angeles', '51', 290, 680),
(18, 'Acapulco', '41', 545, 681),
(19, 'Panama', '41', 573, 680),
(20, 'Port au Prince', '49', 760, 715),
(21, 'Rio', '49', 783, 715),
(22, 'Vaparaiso', '46', 929, 668),
(23, 'Jamaïque', '45', 965, 605),
(24, 'Shetland', '45', 959, 594),
(25, 'Bureau 2', '44', 931, 593),
(26, 'Bureau', '43', 777, 593),
(27, 'Anchorage', '42', 700, 614),
(28, 'La Havane', '42', 676, 595),
(29, 'Les Fidji', '42', 643, 593),
(30, NULL, '1,33,31,38', 557, 328),
(31, NULL, '2,30,32', 613, 268),
(32, NULL, '3,31', 809, 258),
(33, NULL, '34,4,30', 500, 195),
(34, NULL, '10,11,37,35', 351, 173),
(35, NULL, '34,36,5,6', 347, 130),
(36, NULL, '7,35', 270, 130),
(37, NULL, '34,9,8', 220, 175),
(38, NULL, '30,39', 490, 533),
(39, NULL, '38,40,42', 490, 606),
(40, NULL, '50,39,41', 490, 657),
(41, NULL, '40,18,19,47', 560, 668),
(42, NULL, '39,29,28,27,43', 673, 604),
(43, NULL, '42,44,26', 777, 603),
(44, NULL, '43,25,45,46', 900, 607),
(45, NULL, '44,24,23', 948, 608),
(46, NULL, '44,22', 906, 666),
(47, NULL, '41,48', 708, 666),
(48, NULL, '47,49', 709, 702),
(49, NULL, '20,21,48', 769, 710),
(50, NULL, '40,51,54', 390, 666),
(51, NULL, '15,16,17,50', 276, 666),
(52, NULL, '14,53', 225, 610),
(53, NULL, '52,13,54', 311, 610),
(54, NULL, '50,12,53', 390, 610);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Point`
--
ALTER TABLE `Point`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `Point`
--
ALTER TABLE `Point`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
