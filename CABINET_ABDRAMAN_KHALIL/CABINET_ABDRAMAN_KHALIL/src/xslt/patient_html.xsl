<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : fiche_patient.xsl
    Created on : 12 novembre 2023, 19:53
    Author     : Khalil
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output indent="yes" method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        
        <html>
            <head>
                
                <!-- Liaison feuille HTML-CSS -->
                <link rel="stylesheet" type="text/css" href="../css/patient_NOM_PATIENT.css"/>
                
                <title> fiche_patient.html </title>
            
            </head>
            
            <body>
                
                <!-- Les renseignements du patient : 
                    - nom
                    - prénom
                    - sexe
                    - date de naissance
                    - numéro de securité sociale
                    - adresse postale
                -->
                
                <!-- Titre Etat Civil -->
                <h1>
                    <xsl:text> Etat Civil </xsl:text> 
                </h1>
                
                <!-- création d'un element div qui contient les renseignements pour l'application du style CSS -->
                <xsl:element name="div">
                    
                    <!-- Attribut 'class' de l'element div -->
                    <xsl:attribute name="class">renseignements</xsl:attribute>
                    
                    <!-- nom du patient -->
                    <xsl:text> Nom : </xsl:text>
                    <xsl:value-of select="patient/nom/text()"/> <br/>

                    <!-- prénom du patient -->
                    <xsl:text> Prénom : </xsl:text>
                    <xsl:value-of select="patient/prénom/text()"/> <br/>

                    <!-- sexe du patient -->
                    <xsl:text> Sexe : </xsl:text>
                    <xsl:value-of select="patient/sexe/text()"/> <br/>

                    <!-- date de naissance du patient -->
                    <xsl:text> Date de naissance : </xsl:text>
                    <xsl:value-of select="patient/naissance/text()"/> <br/>

                    <!-- numéro de securité sociale du patient -->
                    <xsl:text> Numéro de securité sociale : </xsl:text>
                    <xsl:value-of select="patient/numéroSS/text()"/> <br/>

                    <!-- adresse postale -->
                    <xsl:text> Adresse : </xsl:text>

                    <!-- selection du numéro de la rue -->
                    <xsl:value-of select="patient/adresse/numéro/text()"/>

                    <xsl:text> </xsl:text> <!-- Rajout d'une espace -->

                    <!-- selection du nom de la rue -->
                    <xsl:value-of select="patient/adresse/rue/text()"/>

                    <xsl:text>, </xsl:text> <!-- Rajout d'une virgule -->

                    <!-- selection du code postal -->
                    <xsl:value-of select="patient/adresse/codePostal/text()"/>

                    <xsl:text>, </xsl:text>

                    <!-- selection du nom de la ville -->
                    <xsl:value-of select="patient/adresse/ville/text()"/>

                    <!-- rajout des infos sur l'étage s'il existe -->
                    <xsl:if test="string-length(patient/adresse/étage) > 0">
                        <xsl:text>, étage </xsl:text>
                        <!-- selection du numéro de l'étage -->
                        <xsl:value-of select="patient/adresse/étage/text()"/>
                    </xsl:if>
                    
                </xsl:element>
                
                
                <!-- Création d'un tableau listant 
                    - Les visites du patient
                    - leur libellé
                    - nom de l'intervenant
                -->
                <table>
                    
                    <!-- En-tête du tableau -->
                    <tr class="en-tete">
                        <td>Date de la visite</td>
                        <td>Libellé</td>
                        <td>Intervenant</td>
                    </tr>
                    
                    <!-- Pour chaque visite on veut afficher les libellés et nom de l'intervenant
                        On appelle donc un template qui traitera chaque noeud visite
                    -->
                    <xsl:apply-templates select="patient/visite"/>
                    
                </table>
            </body>
        </html>
    </xsl:template>
    
    <!-- ici, pour une visite on ajoute une nouvelle ligne dans le tableau contenant
        - date de la visite
        - libellé des actes
        - nom et prénom de l'intervenant
    -->
    <xsl:template match="visite">
        <tr>
            <!-- date de la visite -->
            <td> <xsl:value-of select="@date"/> </td>
            
            
            <!-- on veut afficher le libellé des actes et vue qu'un patient peut avoir plusieurs actes 
                on applique un template sur tous les noeuds 'acte'
            -->
            <td>
                <!-- Libellés des actes sous forme de liste -->
                <ul> <xsl:apply-templates select="acte"/> </ul>
            </td>
            
            
            <!-- Nom et prénom de l'intervenant -->
            <td>
                <xsl:value-of select="intervenant/prénom"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="intervenant/nom"/>
            </td>
            
        </tr>
    </xsl:template>
    
    <!-- ici Affichage des libellés des actes sous forme de liste -->
    <xsl:template match="acte">
        <li> <xsl:value-of select="text()"/> </li>
    </xsl:template>

</xsl:stylesheet>
