<?xml version="1.0" encoding="UTF-8"?>

<!-- A completer : doit contenir le score de la partie -->

<!--
    Document   : pofil.xsl
    Created on : 17 octobre 2023, 17:33
    Author     : khalili
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:pro="http://myGame/tux">
    
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>Mes parties</title>
            </head>
            <body>
                
                <!-- ici on veut afficher le nom du joueur en titre de la page -->
                
               <h2> <i> <xsl:value-of select="pro:profil/pro:nom/text()"/> </i> </h2>
               
               <!-- ici affichage de l'avatar et définition d'une varaiable
                    contenant le nom de l'avatar
               -->
               
               <xsl:variable name="nom_avatar" select="pro:profil/pro:avatar/text()"/>
               
               <img src="$nom_avatar"/>
               
               <h1> Parties jouées </h1>
               
               <!-- ici création d'un tableau contenant les informations sur les parties jouées
                    notamment les dates, les mots trouvés ainsi que le niveau de chaque mot
               -->
               
               <table border="1">
                   <tr>
                       <td> Date </td>
                       <td> Mot </td>
                       <td> Niveau </td>
                       <td> Score </td>
                   </tr>
                   
                   <!-- A chaque fois qu'on a une partie, on a une nouvelle ligne de données 
                        qui se rajoute dans le tableau et qui contient les données sur
                        la date, le mot trouvé et le niveau du mot trouvé, on va donc
                        appeler le template qui va traiter chaque noeud partie
                   -->
                   
                   <xsl:apply-templates select="//pro:partie"/>
                   
               </table>
               
            </body>
        </html>
    </xsl:template>
    
    <!-- A chaque fois qu'on a une partie, on a une nouvelle ligne de données 
        qui se rajoute dans le tableau et qui contient les données sur
        la date, le mot trouvé et le niveau du mot trouvé, on va donc
        appliquer UN template qui traite CHACUNE de ses parties
    -->
    
    <xsl:template match="pro:partie">
        <tr>
            <td><xsl:value-of select="@date"/></td>
            <td><xsl:value-of select="pro:mot/text()"/></td>
            <td><xsl:value-of select="pro:mot/@niveau"/></td>
            <td></td> <!-- A completer : doit contenir le score de la partie -->
        </tr>
    </xsl:template>

</xsl:stylesheet>
