
# Géolocalisation indoor par la technologie Li-Fi

Ce projet a été proposé par M. Ivan MADJAROV, Maître de conférences (HCE) en informatique à l'IUT R&T de Luminy. Il vise à développer une application permettant à l'utilisateur de se localiser dans le bâtiment de l'IUT et de pouvoir avoir une assistance pour localiser et connaître le chemin vers une salle.

## Fonctionnalités

-   Localisation de l'utilisateur sur un plan donné par le biais de balises LiFi ou de QR Code.
-   Itinéraire pour atteindre une salle spécifique
-   Exportation de l'emploi du temps via URL ICalendar pour connaître en temps réel le trajet à effectuer pour aller d'une salle à une autre en fonction de son emploi du temps.
-   Possibilité de changer le thème de l'application dans les paramètres.

## Technologies utilisées

-   Java pour le développement de l'application
-   Android Studio pour la création de l'application Android
-   QR Code pour l'importation des points dans la carte de l'application
-   Li-Fi pour la détection de la position de l'utilisateur dans le bâtiment
-   URL ICalendar pour l'exportation de l'emploi du temps

## UI Design

L'interface utilisateur est conçue pour être simple et intuitive. Elle comprend une barre de recherche pour sélectionner la salle désirée, une map pour guider l'utilisateur en fonction de la salle choisie et des boutons pour accéder à des fonctionnalités supplémentaires telles que le contact, le scan de QR Code, l'import de l'emploi du temps via ICalendar et les paramètres de l'application.

## Utilisateurs visé

Cette application est destinée aux nouveaux arrivants dans le bâtiment de l'IUT, tels que les étudiants de première année ou les visiteurs, ainsi qu'aux étudiants ne connaissant pas le bâtiment. Elle est exclusivement utilisable sur des appareils Android.

## Remarque

Le projet a été démarré sur un autre git mais, pour des raisons de qualité, nous avons décidé de repartir de zéro et de créer un nouveau git. Toutes les avancées actuelles sont documentées sur ce nouveau git. Veuillez consulter les commits pour connaître les modifications apportées.

## Comment builder le projet

Pour builder le projet, vous devez d'abord avoir Android Studio installé sur votre ordinateur. Ensuite, suivez les étapes suivantes :

1.  Ouvrez le projet dans Android Studio
2.  Assurez-vous que vous utilisez la dernière version de Gradle. Vous pouvez vérifier cela en allant dans "File" -> "Settings" -> "Gradle" et en vous assurant que la version sélectionnée est à jour.
3.  Connectez votre appareil Android ou émulateur à votre ordinateur ou configurez un émulateur dans Android Studio.
4.  Dans la barre d'outils d'Android Studio, cliquez sur "Build" -> "Build Bundle(s) / APK(s)" -> "Build APK(s)".
5.  Une fois la compilation terminée, l'APK générée se trouvera dans le répertoire "build/outputs/apk" de votre projet.
