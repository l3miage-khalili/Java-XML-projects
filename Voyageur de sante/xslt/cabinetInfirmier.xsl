<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : cabinetInfirmier.xsl
    Created on : 7 novembre 2023, 16:51
    Author     : Khalil
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:cab="http://www.ujf-grenoble.fr/l3miage/medical" 
                xmlns:act="http://www.ujf-grenoble.fr/l3miage/actes" version="1.0">
    
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    
    <!-- variable contenant les noeuds ngap du document actes.xml -->
    <xsl:variable name="actes" select="document('actes.xml', /)/act:ngap"/>
    
    <!-- Paramètre global servant à affichier une page dédiée à une infirmière en particulier se basant sur l'id de l'infirmière -->
    <xsl:param name="destinedId" select="001"/>
    
    <!-- Variable visitesDuJour : contient les patients d'une infimière -->
    <xsl:variable name="visitesDuJour" select="//cab:visite[@intervenant=$destinedId]"/>
        
    <xsl:template match="/">        
        <html>
            <head>
                <title>cabinetInfirmier.xsl</title>
            </head>
            <body>
                
                <!-- Affichage de la phrase d'accueil --> 
                Bonjour <xsl:value-of select="cab:cabinet/cab:infirmiers/cab:infirmier[@id=$destinedId]/cab:prénom/text()"/>
                <br/>
                Aujourd'hui, Vous avez <xsl:value-of select="count($visitesDuJour)"/> patient(s)
                
                <!-- A la suite de la phrase d'accueil, on souhaite lister pour chaque patient à visiter (et dans l'ordre de visite), 
                    son nom, 
                    son adresse correctement mise en forme et 
                    la liste des soins à effectuer 
                -->
                
                <ol>
                    <xsl:apply-templates select="$visitesDuJour/.."/>
                </ol>
                 
                
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="cab:patient">
        
        <!-- ici, définition d'une variable actePatient qui contient le id -->
        <xsl:variable name="actePatient" select="cab:visite/cab:acte/@id"/>
        
        <li> 
            <ul>
                <li> nom : <xsl:value-of select="cab:nom/text()"/> </li>
                <li> 
                    adresse : 
                    <xsl:value-of select="cab:adresse/cab:numéro/text()"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="cab:adresse/cab:rue/text()"/>
                    <xsl:text>, </xsl:text>
                    <xsl:value-of select="cab:adresse/cab:codePostal/text()"/>
                    <xsl:text>, </xsl:text>
                    <xsl:value-of select="cab:adresse/cab:ville/text()"/>
                    <xsl:text>, </xsl:text>
                    <xsl:text> étage </xsl:text> <xsl:value-of select="cab:adresse/cab:étage/text()"/>
                </li>
                <li> 
                    Soins à effectuer : 
                    <ul>
                        <xsl:apply-templates select="$actes//act:acte[@id=$actePatient]"/>
                    </ul>
                </li>
            </ul>
        </li>
    </xsl:template>
    
    <xsl:template match="act:acte">
        <li> <xsl:value-of select="text()"/> </li>
    </xsl:template>
    
</xsl:stylesheet>
