<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : dico.xsl
    Created on : 17 octobre 2023, 16:35
    Author     : khalili
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:dico="http://myGame/tux">
    
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    
    <xsl:template match="/">
        <html>
            <head>
                <title>dictonnaire</title>
            </head>
            <body>
                
                <!-- Affichage d'un titre -->
                
                <h1> Les mots du dictionnaire </h1>
                
                <!-- ici une liste ordonnée qui va contenir les mots à afficher -->
                
                <ol>
                    <!-- ici appelle au template qui cible tous les noeuds "mot" 
                    tout en triant les mots par ordre alphabétique
                -->
                
                <xsl:apply-templates select="//dico:mot">
                    <xsl:sort select="text()" order="ascending"/>
                </xsl:apply-templates>
                
                </ol>
                
                
            </body>
        </html>
    </xsl:template>
    
    <!-- ici défintion des règles appliquées sur un noeud mot :
        - une nouvelle ligne est rajoutée dans la liste
        - on récupère le contenu du noeud mot en question
        - on affiche ensuite ce contenu dans la ligne de la liste qu'on vient de rajouter
    -->
    
    <xsl:template match="dico:mot">
        <li> <xsl:value-of select="text()"/> </li>
    </xsl:template>

</xsl:stylesheet>
