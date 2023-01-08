<?php 
error_reporting(0);
date_default_timezone_set('Europe/Paris');
setlocale(LC_TIME, "fr_FR");

header('Content-Type: application/json; charset=utf-8');


$bdd = new PDO('mysql:host=localhost;dbname=m21204871;port=3306', "m21204871", "bAQ3aCrSEH1brur");


$req = $bdd->prepare("SELECT * FROM `Point` WHERE 1");
$req->execute();
$req = $req->fetchAll(PDO::FETCH_ASSOC);


echo json_encode($req);


?>